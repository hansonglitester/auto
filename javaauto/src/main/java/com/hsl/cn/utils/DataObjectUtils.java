package com.hsl.cn.utils;

import java.util.List;

public class DataObjectUtils {
    public static  Object[][] getData(List list){
        //定义二位数字，返回测试数据
        Object[][] data=new Object[list.size()][];
        for(int i=0;i<list.size();i++){

            data[i]=new Object[]{list.get(i)};
        }
        return data;
    }
}
