package com.shsxt.crm.service;

import com.shsxt.crm.dao.CustomerDao;
import com.shsxt.crm.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    /**
     * 查询所有客户
     */
    @Autowired
    private CustomerDao customerDao;

    public List<CustomerVO> findAll(){
        List<CustomerVO> customes=customerDao.findAll();
        return customes;
    }
}
