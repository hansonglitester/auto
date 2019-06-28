package com.hsl.cn.respority.test;

import com.hsl.cn.pojo.test.SaveUserCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveUserCaseDao extends JpaRepository<SaveUserCase,Integer> {
}
