package com.srpost.cm.bo.si.destLog;

import java.util.List;
import java.util.Map;

import com.srpost.cm.bo.base.user.UserBean;
import com.srpost.salmon.bean.BasePagerBean;

/**
 * 내부단 개인정보 파기 인터페이스
 *
 * @author  allen
 * @date    2022-09-19
 * @since   1.0
 */
public interface IDestLogService {

    BasePagerBean list(DestLogBean bean);
    
    List<Map<String, Object>> listExcel(DestLogBean bean);

    int insertAction(DestLogBean bean);

    List<UserBean> destLogScheduler();

    int destLogAction(DestLogBean bean);
}
