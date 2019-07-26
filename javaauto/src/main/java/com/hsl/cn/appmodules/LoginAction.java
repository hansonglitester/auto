package com.hsl.cn.appmodules;

import com.hsl.cn.dataobject.pojo.test.LoginCase;
import com.hsl.cn.utils.HttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginAction {

    public static HttpResponse excute(LoginCase loginCase, String url) throws IOException {
        //组参数
        Map<String,Object> params=new HashMap<>();
        params.put("mobile",loginCase.getMobile());
        params.put("pwd",loginCase.getPwd());
        //发送请求，获得相应的实体
        HttpResponse response= HttpClientUtils.sendPostRequest(url,params);
        return response ;
    }


    public static String  getLoginCookie(String mobile,String pwd, String url) throws IOException {
        //组参数
        Map<String,Object> params=new HashMap<>();
        params.put("mobile",mobile);
        params.put("pwd",pwd);
        //发送请求，获得相应的实体
        DefaultHttpClient client=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);

        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            BasicNameValuePair bnvs = new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString());
            formParams.add(bnvs);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(formParams,"utf-8"));
        //
        client.execute(httpPost);
        CookieStore cookieStore= client.getCookieStore();
        List<Cookie> cookies=cookieStore.getCookies();
        cookies.toString();
        StringBuffer sb=new StringBuffer();

        for (Cookie cookie:cookies){
            sb.append(cookie.getName()).append("=").append(cookie.getValue());
        }

        return sb.toString();
    }


}
