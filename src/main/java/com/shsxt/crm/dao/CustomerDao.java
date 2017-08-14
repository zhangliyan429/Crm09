package com.shsxt.crm.dao;

import com.shsxt.crm.vo.CustomerVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomerDao {
    @Select("select id,name FROM t_customer where is_valid=1")
    List<CustomerVO> findAll();
}
