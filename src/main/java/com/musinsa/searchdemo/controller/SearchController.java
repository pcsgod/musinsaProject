package com.musinsa.searchdemo.controller;


import com.musinsa.searchdemo.handler.IHandler;
import com.musinsa.searchdemo.handler.api.SuggestedWordHandler;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SuggestedWordHandler suggestedWordHandler;

    /**
     * 추천 단어 검색
     * @param servletResponse
     * @param searchWord
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/search/{searchWord}")
    @ResponseBody
    public Object searchApiHandler(HttpServletResponse servletResponse,@PathVariable String searchWord) throws IOException {
        try {
            logger.info("searchApiHandler.search - start searchWord :"+searchWord);
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("searchWord", searchWord);
            Object res = suggestedWordHandler.CallHandler(requestBody);
            logger.info("searchApiHandler.search - end searchWord:"+searchWord+" res :"+res);
            return res;
        }catch (Exception ex){
            servletResponse.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            logger.error("searchApiHandler.search - end searchWord:"+searchWord,ex);
            return "ERROR";
        }
    }

}
