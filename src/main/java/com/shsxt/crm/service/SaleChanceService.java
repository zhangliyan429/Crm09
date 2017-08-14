package com.shsxt.crm.service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dto.SaleChanceDto;
import com.shsxt.crm.dto.SaleChanceQuery;
import com.shsxt.crm.model.SaleChance;
import com.shsxt.crm.util.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService {
    @Autowired
    private SaleChanceDao saleChanceDao;
    private static Logger logger = LoggerFactory.getLogger(SaleChanceService.class);

    public Map<String, Object> selectForPage(SaleChanceQuery query) {
        //构建一个分页对象
        Integer page = query.getPage();
        if (page == null) {
            page = 1;
        }
        Integer pageSize = query.getRows();

        if (pageSize == null) {
            pageSize = 7;
        }
        String sort=query.getSort();
        if(StringUtils.isBlank(sort)){
            sort="id.desc";// 数据库字段.desc/asc
        }
        PageBounds pageBounds=new PageBounds(page,pageSize, Order.formString(sort));
        List<SaleChance> saleChances=saleChanceDao.selectForPage(query,pageBounds);
        PageList<SaleChance> result=(PageList<SaleChance>) saleChances;
        //返回分页结果
        Paginator paginator=result.getPaginator();
        Map<String,Object> map=new HashMap<>();
        map.put("paginator",paginator);
        map.put("rows",result);
        map.put("total",paginator.getTotalCount());

        return map;
    }

    /**
     * 添加
     * @param saleChanceDto
     * @param loginUserName
     */
    public void add(SaleChanceDto saleChanceDto, String loginUserName) {
        // 参数验证
        checkParams(saleChanceDto.getCustomerName(),saleChanceDto.getCustomerId(),saleChanceDto.getCgjl());

        // 判断分配状态  根据是否有指定人判断
        String assignMan=saleChanceDto.getAssignMan();
        int state=0;//未分配
        Date assignTime=null;
        if(StringUtils.isNoneBlank(assignMan)){
            state=1;//已分配
            assignTime=new Date();//重新设置分配时间
        }
        SaleChance saleChance=new SaleChance();
        BeanUtils.copyProperties(saleChanceDto,saleChance);//属性拷贝
        saleChance.setAssignMan(assignMan);
        saleChance.setAssignTime(assignTime);
        saleChance.setCreateMan(loginUserName);


//		saleChance.setCreateDate(new Date());
//		saleChance.setUpdateDate(new Date());
        // 保存
        int count=saleChanceDao.insert(saleChance);
        logger.debug("插入的记录数为:{},主键为:",count,saleChance.getId());

    }

    private void checkParams(String customerName,Integer customerId,Integer cgjl) {
        /*if(customerId == 0){
            throw new ParamException("请选择客户");
        }
        if(customerName == null && customerName.length()>0){
            throw new ParamException("请选择客户");
        }
        if(cgjl == 0){
            throw new ParamException("请输入成功几率");
        }*/
        AssertUtil.intIsNotEmpty(customerId,"请选择客户");
        AssertUtil.isNotEmpty(customerName,"请选择客户");
        AssertUtil.intIsNotEmpty(cgjl,"请输入成功几率");
    }

    /**
     * 更新
     * @param saleChance
     */
    public void update(SaleChance saleChance) {
        //基本验证
        Integer id=saleChance.getId();
        AssertUtil.intIsNotEmpty(id,"请选择记录进行更新");
        checkParams(saleChance.getCustomerName(),saleChance.getCustomerId(),saleChance.getCgjl());
        //判断分配状态  根据是否有指定人判断
        //选查询该记录是否分配过   先查询  在判定
        //checkState(saleChance);
        saleChance.setUpdateDate(new Date());
        SaleChance saleChance1FromDB = saleChanceDao.findById(id);
        AssertUtil.notNull(saleChance1FromDB,"该记录不存在,请重新选择");
        int state=saleChance1FromDB.getState();
        Date assignTime=null;
        String assignMan=saleChance1FromDB.getAssignMan();
        if(saleChance1FromDB.getState() == 0){//未分配
            if(StringUtils.isNoneBlank(saleChance.getAssignMan())){
                state=1;//已分配
                assignTime=new Date();
            }
        }else{//已分配
            if(!saleChance1FromDB.getAssignMan().equals(saleChance.getAssignMan())){// 页面传入的指派人和数据库中的指派人不相等
                if(StringUtils.isNoneBlank((saleChance.getAssignMan()))){// 客户端没有传值
                    state=0;//处于未分配状态
                    assignTime=null;
                }else{//客户端传值
                    assignMan=saleChance.getAssignMan();
                    assignTime=new Date();
                }
            }

        }
        saleChance.setAssignMan(assignMan);
        saleChance.setAssignTime(assignTime);
        saleChance.setState(state);
        //更新
        saleChanceDao.update(saleChance);

    }

    /**
     * 验证分配状态
     * @param saleChance
     */
   /* private void checkState(SaleChance saleChance) {
        SaleChance saleChance1FromDB = saleChanceDao.findById(saleChance.getId());
        AssertUtil.notNull(saleChance1FromDB,"该记录不存在,请重试!");
        int state=saleChance1FromDB.getState();
        Date assignTime=null;
        String assignMan=saleChance1FromDB.getAssignMan();
        if(saleChance1FromDB.getState() == 0){//未分配
            if(StringUtils.isNoneBlank(saleChance.getAssignMan())){
                state=1;//已分配
                assignTime=new Date();
            }
        }else{//已分配
            if(!saleChance1FromDB.getAssignMan().equals(saleChance.getAssignMan())){// 页面传入的指派人和数据库中的指派人不相等
                if(StringUtils.isNoneBlank(saleChance.getAssignMan())){// 客户端没有传值
                    state=0;//处于未分配状态
                    assignTime=null;
                }else{//客户端传值
                    assignMan=saleChance.getAssignMan();
                    assignTime=new Date();
                }
            }

        }
        saleChance.setAssignMan(assignMan);
        saleChance.setAssignTime(assignTime);
        saleChance.setState(state);
    }*/

    /**
     * 删除
     * @param ids
     */
    public void delete(String ids) {
        //参数验证
        AssertUtil.isNotEmpty(ids,"请选择记录进行删除");
        saleChanceDao.delete(ids);
    }
}
