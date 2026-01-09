package org.ll.apiresponse.controller;

import lombok.RequiredArgsConstructor;
import org.ll.apiresponse.dto.ExampleResponse;
import org.ll.apiresponse.service.ExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/normal")
    public ResponseEntity<ExampleResponse> getNormalResponse() {
        return ResponseEntity.ok(exampleService.getNormalResponse());
    }

    @GetMapping("/error")
    public ResponseEntity<ExampleResponse> getErrorResponse() {
        return ResponseEntity.ok(exampleService.getErrorResponse());
    }
}
