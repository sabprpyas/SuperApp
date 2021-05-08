package com.sky.demo.utils.http;

import com.sky.demo.common.Constants;

import org.xutils.http.RequestParams;

/**
 * @author sky QQ:1136096189
 * @Description: TODO HTTP基础请求类，待修改
 * @date 15/11/28 下午12:38
 */
public class HttpUtilsBase {
//    public volatile static HttpUtils httpUtils = new HttpUtils();
//    public final static HttpUtils httpUtils = new HttpUtils();
//
//    static {
//        httpUtils.configCurrentHttpCacheExpiry(0);
//    }
//
    public static RequestParams getRequestParams() {
        RequestParams params = new RequestParams(Constants.BASE_URL);
//        params.addQueryStringParameter("AppVersion", Constants.APP_VER);
        return params;
    }
//
//    public static String generatePath(String path, String action) {
//        return String.format("%s%s/%s", Constants.BASE_URL, path, action);
//    }
//
    public interface RequestHandler {
        void cancel();
    }
}
