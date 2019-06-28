package com.hsl.cn.pojo.test;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class SaveUserCase {
    @Id
    @GeneratedValue
    private Integer id;
    private String testTitle;
    private String name;
    private String pwd;
    private Integer age;
    private Integer sex;
    private Integer status;
    private String except;
    private String actual;
    /*是否执行测试 0 执行 其他不执行*/
    private Integer active;
    /*测试结果*/
    private String testResult;

}
