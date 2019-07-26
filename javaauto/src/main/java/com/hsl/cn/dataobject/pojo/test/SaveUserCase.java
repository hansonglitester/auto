package com.hsl.cn.dataobject.pojo.test;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SaveUserCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String testTitle;
    private String mobile;
    private String pwd;
    private String age;
    private String sex;
    private String status;
    private String except;
    
}
