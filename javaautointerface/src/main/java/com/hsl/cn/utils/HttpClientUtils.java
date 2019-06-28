package com.hsl.cn.utils;

import java.io.IOException;
import java.util.*;

import com.hsl.cn.cases.TestSaveUser;
import com.hsl.cn.config.TestConfig;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hibernate.loader.collection.OneToManyJoinWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientUtils {
    public static int statusCode;

    public static Logger logger= LoggerFactory.getLogger(TestSaveUser.class);

    /**
     * 发送get，发送的参数是键值类型的key=value&key=value
     * @param reqURL
     * @param params
     * @return
     */
    public static String sendGetRequest(String reqURL, Map<String, String> params) {

        logger.info(params.toString());

        reqURL = reqURL + "?" + getReqParam(params);

        String responseContent = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(reqURL);
        httpGet.setHeader("Cookie", TestConfig.cookieValue);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            statusCode =response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity,"UTF-8");
                EntityUtils.consume(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseContent;
    }
    /**
     * 发送HTTP_POST请求,发送的参数是键值类型的key=value&key=value
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, Map <String, Object> params) {
        String responseContent= sendPostRequest(new Header[]{},reqURL,params);
        return responseContent;
    }


    /**
     * 发送HTTP_POST请求,发送的参数是键值类型的key=value&key=value
     *
     * @param reqURL        请求地址
     * @param params        请求参数 map存储
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(Header[] headers,String reqURL, Map <String, Object> params) {
        logger.info("reqparam:"+params);
        String responseContent = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(reqURL);

        if (headers!=null||headers.length>0){
            httpPost.setHeaders(headers);
        }
        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParams,"UTF-8"));

            HttpResponse response = httpClient.execute(httpPost);
            statusCode =response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity,"UTF-8");
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }



    /**
     * @param reqURL
     * @param params key=value
     * @return
     */
    public static String sendPostRequest(String reqURL, String params) {
       return  sendPostRequest(new Header[]{},reqURL,params);
    }


    public static String sendPostRequest(Header[] headers,String reqURL, String params) {
        logger.info("reqparam:"+params);

        HttpPost httpPost = new HttpPost(reqURL);
        if (headers!=null||headers.length>0){
            httpPost.setHeaders(headers);
        }
        httpPost.setHeaders(headers);
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            StringEntity postEntity = new StringEntity(params, "UTF-8");
            httpPost.setEntity(postEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpPost.abort();
        }
        return result;
    }


    public static String getReqParam(Map<String, String> params) {

        String str=null;
        try {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列

            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
             str=EntityUtils.toString(new UrlEncodedFormEntity(formParams,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
