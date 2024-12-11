package com.mtataje.discogs.integration.api.client;

import com.mtataje.discogs.integration.api.common.DiscogsExternalAPIProperties;
import com.mtataje.discogs.integration.api.exception.ConcurrentOperationFailureException;
import com.mtataje.discogs.integration.api.util.ConcurrentExecutable;
import com.mtataje.discogs.integration.api.util.validation.HttpResponseValidator;
import com.mtataje.discogs.integration.api.vo.DiscogsArtistVO;
import com.mtataje.discogs.integration.api.vo.DiscogsFullReleaseResponseVO;
import com.mtataje.discogs.integration.api.vo.DiscogsReleaseDetailsVO;
import com.mtataje.discogs.integration.api.vo.DiscogsReleaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DiscogsClient implements ConcurrentExecutable {

    private final RestTemplate restTemplate;
    private final DiscogsExternalAPIProperties discogsExternalAPIProperties;
    private final ExecutorService executorService;
    private final DiscogsExternalAPIProperties properties;

    public DiscogsArtistVO getBaseArtistInfo(Long id) {
        String url = String.format("%s%s%d", discogsExternalAPIProperties.getBaseApiUrl(), "artists/", id);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, String.format("Discogs token=%s", discogsExternalAPIProperties.getApiKey()));

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DiscogsArtistVO> rawResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, DiscogsArtistVO.class);

        HttpResponseValidator.validate(rawResponseEntity);

        return rawResponseEntity.getBody();
    }

    public List<DiscogsReleaseVO> getReleasesFromArtistReleaseUrl(String resourceUrl) {
        ResponseEntity<DiscogsFullReleaseResponseVO> rawResponseEntity = restTemplate.exchange(resourceUrl, HttpMethod.GET, null, DiscogsFullReleaseResponseVO.class);
        HttpResponseValidator.validate(rawResponseEntity);

        Optional<List<DiscogsReleaseVO>> releases = Optional.ofNullable(rawResponseEntity.getBody().getReleases());
        return releases.orElseGet(ArrayList::new);
    }

    public List<DiscogsReleaseVO> getDiscogsReleaseWithDetailsFromReleaseUrl(String resourceUrl) {
        List<DiscogsReleaseDetailsVO> releaseDetailsList = new ArrayList<>();

        List<DiscogsReleaseVO> releases = getReleasesFromArtistReleaseUrl(resourceUrl);
        List<Callable<DiscogsReleaseDetailsVO>> tasks = releases.stream()
                .map(release -> (Callable<DiscogsReleaseDetailsVO>) () -> getReleaseDetails(release.getResourceUrl()))
                .collect(Collectors.toList());

        try {
            List<Future<DiscogsReleaseDetailsVO>> futures = executorService.invokeAll(tasks);

            for (Future<DiscogsReleaseDetailsVO> future : futures) {
                try {
                    releaseDetailsList.add(future.get());
                } catch (ExecutionException e) {
                    throw new ConcurrentOperationFailureException(e);
                }
            }
        } catch (InterruptedException e) {
            throw new ConcurrentOperationFailureException(e);
        }

        executorService.shutdown();

        Map<Long, DiscogsReleaseDetailsVO> releaseDetailsMap = releaseDetailsList.stream()
                .collect(Collectors.toMap(DiscogsReleaseDetailsVO::getReleaseId, details -> details));

        for (DiscogsReleaseVO release : releases) {
            release.setDetails(releaseDetailsMap.get(release.getId()));
        }

        return releases;
    }

    private DiscogsReleaseDetailsVO getReleaseDetails(String resourceUrl) {
        return executeWithRetry(() -> {
            ResponseEntity<DiscogsReleaseDetailsVO> responseEntity =
                    restTemplate.exchange(resourceUrl, HttpMethod.GET, null, DiscogsReleaseDetailsVO.class);
            HttpResponseValidator.validate(responseEntity);
            return responseEntity.getBody();
        }, properties.getWaitDurationInMs(), properties.getMaxAttempts());
    }

}
