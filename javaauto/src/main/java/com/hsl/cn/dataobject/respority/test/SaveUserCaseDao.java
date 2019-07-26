package com.hsl.cn.dataobject.respority.test;

import com.hsl.cn.dataobject.pojo.test.SaveUserCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveUserCaseDao extends JpaRepository<SaveUserCase,Integer> {

}
