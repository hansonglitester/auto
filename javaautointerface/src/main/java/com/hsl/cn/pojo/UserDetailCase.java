package com.hsl.cn.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserDetailCase {
    @Id
    private Integer id;
    private Integer userId;
    private String testTitle;
    private String except;
    private String actual;
    /*是否执行测试 0 执行 其他不执行*/
    private Integer active;
    /*测试结果*/
    private String testResult;

}
