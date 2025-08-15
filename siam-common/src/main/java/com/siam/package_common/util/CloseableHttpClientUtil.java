package com.siam.package_common.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Base64;


/**
 * http请求类
 * <p>Title: HttpClicentUtil</p>
 * <p>Description: </p>
 * <p>Company: wp</p>
 */
public class CloseableHttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(CloseableHttpClientUtil.class);

    public static JSONObject sendPostBuffer(String url, String data_body) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建post方法连接实例，在post方法中传入待连接地址
        HttpPost httpPost = new HttpPost(url);

        logger.info("请求参数={},请求地址={}", data_body, url);
        StringEntity stringEntity = new StringEntity(data_body, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Accept", "application/json");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse httpResponse = null;
        JSONObject resultMap = new JSONObject();
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            logger.info("204处理成功，应答无内容,状态码为={}", httpResponse.getStatusLine().getStatusCode());
            //204处理成功，应答无内容
            if (httpResponse.getStatusLine().getStatusCode() == 200 ||
                    httpResponse.getStatusLine().getStatusCode() == 204) {
                String base64 = "";
                try (InputStream is = entity.getContent(); ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    base64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
                }
                logger.info("获取base64返回信息={}", base64);
                resultMap.put("success", true);
                resultMap.put("data", base64);
                resultMap.put("message", "成功");
            } else {
                JSONObject getResponseBody = JSONObject.parseObject(EntityUtils.toString(entity));
                logger.info("获取返回参数={}", getResponseBody);
                resultMap.put("success", false);
                resultMap.put("code", getResponseBody.getString("code"));
                resultMap.put("message", getResponseBody.getString("message"));
            }
            logger.info("请求参数={},请求地址={},返回数据={}", data_body, url, resultMap);
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            return resultMap;
        } catch (IOException e) {
            return resultMap;
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                return resultMap;
            }
        }
        return resultMap;
    }

    public static JSONObject sendPost(String url, String data_body) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建post方法连接实例，在post方法中传入待连接地址
        HttpPost httpPost = new HttpPost(url);

        logger.info("【前】=====请求参数={},请求地址={}", data_body, url);
        StringEntity stringEntity = new StringEntity(data_body, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("version", "1.0.0");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse httpResponse = null;
        JSONObject resultMap = new JSONObject();
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            logger.info("204处理成功，应答无内容,状态码为={}", httpResponse.getStatusLine().getStatusCode());
            //204处理成功，应答无内容
            if (httpResponse.getStatusLine().getStatusCode() == 200 ||
                    httpResponse.getStatusLine().getStatusCode() == 204) {
                JSONObject body = JSONObject.parseObject(EntityUtils.toString(entity));
                logger.info("获取返回信息={}", body);
                resultMap.put("success", true);
                resultMap.put("data", body);
                resultMap.put("message", "成功");
            } else {
                //JSONObject body = JSONObject.parseObject(EntityUtils.toString(entity));
                resultMap.put("success", false);
               // resultMap.put("data", body);
                resultMap.put("code", httpResponse.getStatusLine().getStatusCode());
                resultMap.put("message", "请求错误");
            }
            logger.info("【后】=====请求参数={},请求地址={},返回数据={}", data_body, url, resultMap);
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
            return resultMap;
        } catch (IOException e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
            return resultMap;
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                resultMap.put("success", false);
                resultMap.put("message", e.getMessage());
                return resultMap;
            }
        }
        return resultMap;
    }

    public static JSONObject sendGet(String url) throws URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URIBuilder uriBuilder = new URIBuilder(url);

        logger.info("请求地址={}", url);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse httpResponse = null;
        JSONObject resultMap = new JSONObject();
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            logger.info("204处理成功，应答无内容,状态码为={}", httpResponse.getStatusLine().getStatusCode());
            //204处理成功，应答无内容
            if (httpResponse.getStatusLine().getStatusCode() == 200 ||
                    httpResponse.getStatusLine().getStatusCode() == 204) {
                JSONObject body = JSONObject.parseObject(EntityUtils.toString(entity));
                logger.info("获取返回信息={}", body);
                resultMap.put("success", true);
                resultMap.put("data", body);
                resultMap.put("message", "成功");
            } else {
                JSONObject getResponseBody = JSONObject.parseObject(EntityUtils.toString(entity));
                logger.info("获取返回参数={}", getResponseBody);
                resultMap.put("success", false);
                resultMap.put("code", getResponseBody.getString("code"));
                resultMap.put("message", getResponseBody.getString("message"));
            }
            logger.info("请求地址={},返回数据={}", url, resultMap);
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            return resultMap;
        } catch (IOException e) {
            return resultMap;
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                return resultMap;
            }
        }
        return resultMap;
    }
}
