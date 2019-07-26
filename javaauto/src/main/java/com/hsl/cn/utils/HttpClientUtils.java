package com.hsl.cn.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpClientUtils {

    public static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 发送get，发送的参数是键值类型的key=value&key=value
     * @param reqURL
     * @param params
     * @return
     */
    public static HttpResponse  sendGetRequest  (Header[] headers,String reqURL, Map<String, Object> params) throws IOException {


        reqURL = reqURL + "?" + getReqParam(params);
        logger.info("reqURL:{}",reqURL,params);
        String responseContent = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(reqURL);

        if (headers!=null||headers.length>0){
            httpGet.setHeaders(headers);
        }

        HttpResponse response = httpClient.execute(httpGet);
        return  response;
    }

    public static HttpResponse  sendGetRequest  (String reqURL) throws IOException {
       return sendGetRequest(null,reqURL,null);
    }
    public static HttpResponse  sendGetRequest  (String reqURL,Map<String,Object> params) throws IOException {
        return sendGetRequest(null,reqURL,params);
    }



    /**
     * 发送HTTP_POST请求,发送的参数是键值类型的key=value&key=value
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @return 远程主机响应正文
     */
    public static HttpResponse sendPostRequest(String reqURL, Map <String, Object> params) throws IOException {
        return sendPostRequest(new Header[]{},reqURL,params);
    }


    /**
     * 发送HTTP_POST请求,发送的参数是键值类型的key=value&key=value
     *
     * @param reqURL        请求地址
     * @param params        请求参数 map存储
     * @return 远程主机响应正文
     */
    public static  HttpResponse sendPostRequest(Header[] headers,String reqURL, Map <String, Object> params) throws IOException {
        logger.info("reqURL:{}",reqURL,params);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(reqURL);

        if (headers!=null||headers.length>0){
            httpPost.setHeaders(headers);
        }



        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列

        for (Map.Entry<String, Object> entry : params.entrySet()) {

            BasicNameValuePair bnvs=null;
            if(entry.getValue()!=null) {
                bnvs = new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString());
            }else{
                bnvs=new BasicNameValuePair(entry.getKey().toString(),"");
            }
            formParams.add(bnvs);
        }

        httpPost.setEntity(new UrlEncodedFormEntity(formParams,"UTF-8"));

        logger.info("reqParam:{}",EntityUtils.toString(httpPost.getEntity(),"utf-8"));
        HttpResponse response = httpClient.execute(httpPost);
        logger.info(response.toString());
        return  response;
    }


    /**
     * @param reqURL
     * @param params key=value
     * @return
     */

    public static  HttpResponse sendPostRequest(Header[] headers,String reqURL, String params) throws IOException {
        logger.info("reqURL:{}",reqURL);
        HttpPost httpPost = new HttpPost(reqURL);

        if (headers!=null||headers.length>0){
            httpPost.setHeaders(headers);
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();

        StringEntity postEntity = new StringEntity(params, "UTF-8");
        httpPost.setEntity(postEntity);
        logger.info("reqParam:{}",EntityUtils.toString(httpPost.getEntity(),"utf-8"));

        HttpResponse response = httpClient.execute(httpPost);

        return response;
    }

    public static  HttpResponse sendPostRequest(String reqURL, String params) throws IOException {
        return sendPostRequest(new Header[]{},reqURL,params);
    }

    public static String getReqParam(Map<String, Object> params) {

        String str=null;
        try {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列

            for (Map.Entry<String, Object> entry : params.entrySet()) {

                BasicNameValuePair bnvs=null;
                if(entry.getValue()!=null) {
                    bnvs = new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString());
                }else{
                    bnvs=new BasicNameValuePair(entry.getKey().toString(),"");
                }
                formParams.add(bnvs);
            }

            str=EntityUtils.toString(new UrlEncodedFormEntity(formParams,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
