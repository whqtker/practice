package org.ll.apiresponse.dto;

public record ExampleResponse(
        String message
) {
    public static ExampleResponse from(String message) {
        return new ExampleResponse(message);
    }
}
