package com.practice.elkstack.domain.post.postDoc.service;

import com.practice.elkstack.domain.post.postDoc.document.PostDoc;
import com.practice.elkstack.domain.post.postDoc.repository.PostDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostDocService {
    private final PostDocRepository postDocRepository;

    public PostDoc write(String title, String content) {
        PostDoc postDoc = PostDoc.builder()
                .title(title)
                .content(content)
                .build();

        return postDocRepository.save(postDoc);
    }
}
