package com.practice.elkstack.domain.post.postDoc.repository;

import com.practice.elkstack.domain.post.postDoc.document.PostDoc;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDocRepository extends ElasticsearchRepository<PostDoc, String> {
}
