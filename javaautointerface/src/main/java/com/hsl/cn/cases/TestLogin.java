package com.hsl.cn.cases;

import com.hsl.cn.config.TestConfig;
import com.hsl.cn.dao.LoginCaseDao;
import com.hsl.cn.pojo.LoginCase;
import com.hsl.cn.utils.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLogin extends BaseTest{
    public Logger logger= LoggerFactory.getLogger(TestLogin.class);
    public String loginUrl= TestConfig.getUrl("user.login");

    @Autowired
    LoginCaseDao loginCaseDao;

    @DataProvider(name = "data")
    public Object[][] getData(){
        List<LoginCase> loginCasesList=loginCaseDao.findAll();
        logger.info(loginCasesList.toString());

        //定义二位数字，返回测试数据
        Object[][] data=new Object[loginCasesList.size()][];
        for(int i=0;i<loginCasesList.size();i++){

            data[i]=new Object[]{loginCasesList.get(i)};
        }
        return data;
    }


    @Test(groups = "login",dataProvider = "data")
    public void  testLogin(LoginCase loginCase) throws IOException {
        logger.info(loginCase.toString());
        //组参数
        Map<Object,Object> params=new HashMap <>();
        params.put("name",loginCase.getName());
        params.put("pwd",loginCase.getPwd());

        //发请求
        String result =getResult(loginUrl,params);
        logger.info(result);

        //校验测试结果


    }


    public String getResult(String url,Map<Object,Object> params) throws IOException {

        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpPost httpPost=new HttpPost(loginUrl);

        //设置请求的参数,设置请求的实体
        List<NameValuePair> list=new ArrayList <>();
        for(Map.Entry<Object,Object> entry:params.entrySet()){
            NameValuePair nameValuePair=new BasicNameValuePair(entry.getKey().toString(),entry.getValue().toString());
            list.add(nameValuePair);
        }
        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(list,"utf-8");
        httpPost.setEntity(entity);
        logger.info("-----------getURI-"+httpPost.getURI().toString());

        //发送请求,获得响应实体
        HttpResponse response=httpclient.execute(httpPost);
        String result=EntityUtils.toString(response.getEntity(),"utf-8");

//        // //获得Cookie
         CookieStore cookieStore=new BasicCookieStore();
                // httpclient.getCookieStore();
//        List<Cookie> cookies= cookieStore.getCookies();
//        System.out.println(cookies.size());
//        for (Cookie cookie:cookies){
//           System.out.println(cookie.getName());
//        }

        cookieStore.addCookie(new BasicClientCookie("login","true"));
        cookieStore.addCookie(new BasicClientCookie("status","200"));

        //httpclient.setCookieStore(cookieStore);
        TestConfig.cookieStore=cookieStore;
        return result;
    }


}
