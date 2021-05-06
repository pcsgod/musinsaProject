package com.musinsa.searchdemo.handler;

import java.io.IOException;
import java.util.Map;

public interface IHandler {

    public Object CallHandler(Map<String,Object> requstBody) throws IOException;
}
