package com.musinsa.searchdemo.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class ElasticSearchService extends RestHighLevelClient {

    RestHighLevelClient client=  null;

    public ElasticSearchService(RestClientBuilder restClientBuilder) {
        super(restClientBuilder);
        client = this;
    }
    /**
     * 추천 단어 검색
     * @param searchWord
     * @return
     * @throws IOException
     */
    public List<String> getSuggestedWords(String searchWord) throws IOException {
        List<String> response = new ArrayList<>();

        BoolQueryBuilder query = boolQuery().should(prefixQuery("word.keyword",searchWord));
        query.should(prefixQuery("word",searchWord));
        query.should(prefixQuery("word.txt",searchWord));
        query.minimumShouldMatch(1);

        SearchResponse res = client.search(new SearchRequest().indices("")
                        .searchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .source(new SearchSourceBuilder()
                                .size(100)
                                .query(query))
                , RequestOptions.DEFAULT);
        for(SearchHit hit : res.getHits().getHits()){
            String word = (String)hit.getSourceAsMap().get("word");
            response.add(word);
        }
        return response;
    }
}