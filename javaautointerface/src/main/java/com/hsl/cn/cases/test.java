package com.hsl.cn.cases;

import com.hsl.cn.config.TestConfig;
import com.hsl.cn.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.testng.annotations.Test;

public class test {
    @Test
    public void test(){

        //发请求
//        Header[] headers={
//                new BasicHeader("Accept","application/x-ms-application, image/jpeg, application/xaml+xml, image/gif, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\n"),
//                new BasicHeader("Referer"," https://www.qhlead.com"),
//                new BasicHeader("Accept-Language",": zh-CN"),
//                new BasicHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)\n"),
//                new BasicHeader("UA-CPU","AMD64"),
//                new BasicHeader("Accept-Encoding","gzip, deflate"),
//                new BasicHeader("Host","www.qhlead.com"),
//                new BasicHeader("Connection","Keep-Alive"),
//                new BasicHeader("Cache-Control","no-cache"),
//                new BasicHeader("Cookie", "SERVERID=21403f46096f6ab343bed97e63ba9a1a|1560933884|1560933282;__RequestVerificationToken=5wD0bKGBjECGGoKdiIZETEHX3tvtkukeKOUtQpT_I_6ojoSf7c6Q8qo3yNIeq10IIA8QYHoMP4Fly11GhxZA6tXr6F9VpC-AJIbUXSMyAgY1;qhlead_s=6445e6d8-54e9-4b5e-ab07-2a488ed6b74a")
//        };
        String url= "https://www.qhlead.com/Passport/Login?Length=8";
        String param="Mobile=15102011472&Password=zj123456&RememberMe=false&__RequestVerificationToken=6fihR6SSXbCYADTCceVXdwsZvrzVpd9M9p0lbii_qfOeRyT7Jy-xkV_xJsSLAIa4yIwHw1W_Ay8jezwIycqgoBc5D7jKs2OiXbgwY15hQfA1";
        String result= HttpClientUtils.sendPostRequest(url,param);
        System.out.println(result);
    }
}
