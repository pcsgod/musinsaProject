package com.musinsa.searchdemo.handler.api;

import com.musinsa.searchdemo.handler.IHandler;
import com.musinsa.searchdemo.service.ElasticSearchService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SuggestedWordHandler implements IHandler {

    @Autowired
    ElasticSearchService elasticSearchService;

    @Override
    public Object CallHandler(Map<String, Object> requestBody) throws IOException {
        String searchWord = (String)requestBody.get("searchWord");
        return  elasticSearchService.getSuggestedWords(searchWord);
    }
}
