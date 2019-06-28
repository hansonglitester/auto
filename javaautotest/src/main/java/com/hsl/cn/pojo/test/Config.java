package com.hsl.cn.pojo.test;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Config {
    @Id
    private Integer id;
    private String interfacename;
    private String url;
}
