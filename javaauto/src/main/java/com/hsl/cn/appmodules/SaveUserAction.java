package com.hsl.cn.appmodules;

import com.google.gson.Gson;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.dataobject.pojo.test.SaveUserCase;
import com.hsl.cn.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveUserAction {
    public static HttpResponse execute(SaveUserCase saveUserCase,String url) throws IOException {

        //组参数
        Map<String,Object> params=new HashMap<>();
        Map <String, Object> map = new HashMap <>();
        map.put("mobile", saveUserCase.getMobile());
        map.put("age", saveUserCase.getAge());
        map.put("pwd", saveUserCase.getPwd());
        map.put("sex", saveUserCase.getSex());
        map.put("status", saveUserCase.getStatus());
        String param = new Gson().toJson(map);

        //发请求
        Header[] headers={
                new BasicHeader("Content-Type","application/json"),
                new BasicHeader("cookie", TestConfig.cookieValue)
        };

        HttpResponse response= HttpClientUtils.sendPostRequest(headers,url,param);
        return response;
    }

}
