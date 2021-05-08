package com.sky.demo.api;

import com.google.gson.Gson;
import com.sky.demo.utils.LogUtils;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/22 下午2:14
 */
public class JsonToResponse implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {
//        LogUtils.i(request+"");
    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        LogUtils.i(result+"");
        return new Gson().fromJson(result,resultType);
    }
}
