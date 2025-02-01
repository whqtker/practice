package com.practice.elkstack.domain.post.postDoc.controller;

import com.practice.elkstack.domain.post.postDoc.document.PostDoc;
import com.practice.elkstack.domain.post.postDoc.service.PostDocService;
import com.practice.elkstack.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/postDocs")
@RequiredArgsConstructor
public class ApiV1PostDocController {
    private final PostDocService postDocService;

    @PostMapping("/write")
    public RsData<PostDoc> write(
            @RequestBody @Valid PostDocWriteRequest request
    ){
        PostDoc postDoc = postDocService.write(request.title, request.content);
        return new RsData<>("201", "글 작성 성공", postDoc);
    }

    record PostDocWriteRequest(
            @NotBlank String title,
            @NotBlank String content
    ) {}
}
