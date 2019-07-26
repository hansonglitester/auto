package com.hsl.cn.testscript;

import com.hsl.cn.appmodules.SaveUserAction;
import com.hsl.cn.config.TestConfig;
import com.hsl.cn.dataobject.pojo.dev.User;
import com.hsl.cn.dataobject.pojo.test.SaveUserCase;
import com.hsl.cn.dataobject.respority.dev.UserDao;
import com.hsl.cn.dataobject.respority.test.SaveUserCaseDao;
import com.hsl.cn.utils.DataObjectUtils;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestSaveUser extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(TestSaveUser.class);
    SaveUserCaseDao caseDao = TestConfig.applicationContext.getBean(SaveUserCaseDao.class);
    UserDao userDao = TestConfig.applicationContext.getBean(UserDao.class);
    String url = TestConfig.cofigMap.get("user.saveuser").toString();
    List <SaveUserCase> caseList=null;

    @DataProvider(name = "data")
    public Object[][] getData() {
        caseList = caseDao.findAll();
        logger.info(caseList.toString());
        //定义二位数字，返回测试数据
        Object[][] data = DataObjectUtils.getData(caseList);
        return data;
    }


    @Test(dataProvider = "data")
    public void testSaveUser(SaveUserCase saveUserCase) throws IOException {
        //组参数,发请求,获得响应
        HttpResponse response = SaveUserAction.execute(saveUserCase, url);

        String responseBody = EntityUtils.toString(response.getEntity());
        logger.info("实际响应body:" + responseBody);
        //断言
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);


        String rsp_code = JsonPath.read(saveUserCase.getExcept(), "$.rsp_code");

        if ("0000".equals(rsp_code)) {

            //期望的数据，从库中查找
            List <User> exeuser = userDao.findByMobile(saveUserCase.getMobile());
            logger.info("期望预期用户信息：" + exeuser);
            //提取实际响应的数据
            Integer actid= JsonPath.read(responseBody, "$.data.id");
            String actmobile=JsonPath.read(responseBody, "$.data.mobile");
            //断言实际返回的用户和库中返回的是否一致
            Assert.assertEquals(actid, exeuser.get(0).getId());
            Assert.assertEquals(actmobile, exeuser.get(0).getMobile());

        }else {

            Assert.assertEquals(responseBody, saveUserCase.getExcept());
        }

        logger.info(saveUserCase.getTestTitle()+" 测试执行成功");
    }

    //测试完成，删除开发库产生的测试数据,保证测试数据唯一性
    @AfterClass
    public void delete(){
        for (SaveUserCase userCase :caseList){
            userDao.deleteByMobile(userCase.getMobile());
        }
    }
}

