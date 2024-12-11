package com.mtataje.discogs.integration.api.util.validation;

import com.mtataje.discogs.integration.api.exception.InvalidHttpStatusCodeException;
import com.mtataje.discogs.integration.api.exception.NoResponseDataException;
import com.mtataje.discogs.integration.api.exception.ResponseWithoutBodyException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@UtilityClass
public class HttpResponseValidator {

    public void validate(ResponseEntity<?> response) {
        if (Objects.isNull(response)) {
            throw new NoResponseDataException();
        }
        HttpStatusCode httpStatusCode = response.getStatusCode();

        if (!httpStatusCode.is2xxSuccessful()) {
            throw new InvalidHttpStatusCodeException();
        }

        if (!response.hasBody()) {
            throw new ResponseWithoutBodyException();
        }
    }

}
