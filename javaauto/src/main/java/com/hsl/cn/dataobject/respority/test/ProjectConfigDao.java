package com.hsl.cn.dataobject.respority.test;

import com.hsl.cn.dataobject.pojo.test.ProjectConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectConfigDao extends JpaRepository<ProjectConfig,Integer> {
    public List<ProjectConfig> findByConfigkey(String key);
}
