package com.hsl.cn.respority.test;

import com.hsl.cn.pojo.test.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigDao extends JpaRepository<Config,Integer> {
}
