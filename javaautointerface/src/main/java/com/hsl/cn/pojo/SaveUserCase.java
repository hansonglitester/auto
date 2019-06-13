package com.hsl.cn.pojo;

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
    private String name;
    private String pwd;
    private Integer age;
    private Integer sex;
    private Integer status;
    private String except;
    private String actual;
    private Integer priority;
}
