package org.ll.apiresponse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String code,
        String message,
        int status,
        List<FieldError> errors
) {

    public record FieldError(String field, String message) {}

    public static ErrorResponse of(String code, String message, int status) {
        return new ErrorResponse(code, message, status, null);
    }
}
