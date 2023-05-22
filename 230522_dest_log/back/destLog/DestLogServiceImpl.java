package com.srpost.cm.bo.si.destLog;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.srpost.cm.bo.base.user.UserBean;
import com.srpost.salmon.bean.BasePagerBean;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 내부단 개인정보 파기 서비스 구현체
 *
 * @author  allen
 * @date    2022-09-19
 * @since   1.0
 */
@Service
public class DestLogServiceImpl extends EgovAbstractServiceImpl implements IDestLogService {

    @Resource
    DestLogDao dao;

    @Override
    public BasePagerBean list(DestLogBean bean) {

        return dao.list(bean);
    }
    
    @Override
    public List<Map<String, Object>> listExcel(DestLogBean bean) {
        
        return dao.listExcel(bean);
    }
    
    @Override
    public int insertAction(DestLogBean bean) {
        
        return dao.insertAction(bean);
    }

    @Override
    public List<UserBean> destLogScheduler() {
        return dao.destLogScheduler();
    }

    @Override
    public int destLogAction(DestLogBean bean) {
        return dao.destLogAction(bean);
    }

    
}
