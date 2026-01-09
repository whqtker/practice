package org.ll.apiresponse.service;

import lombok.RequiredArgsConstructor;
import org.ll.apiresponse.dto.ExampleResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExampleService {

    public ExampleResponse getNormalResponse() {
        return ExampleResponse.from("Hello World!");
    }

    public ExampleResponse getErrorResponse() {
        throw new IllegalArgumentException("올바르지 않은 요청입니다.");
    }
}
