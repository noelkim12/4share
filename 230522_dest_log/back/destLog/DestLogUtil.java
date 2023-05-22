package com.srpost.cm.bo.si.destLog;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.srpost.cm.bo.base.login.LoginBean;
import com.srpost.cm.bo.base.mgr.MgrUtil;
import com.srpost.salmon.lang.WebUtil;

/**
 * 내부단 개인정보 파기 Util
 *
 * @author  allen
 * @date    2022-09-19
 * @since   1.0
 */
public final class DestLogUtil {
    
    public static Map<String, Object> getParameterMap(DestLogBean bean) {
        
        Map<String, Object> parameterMap = bean.createPagerMap();
        
        return parameterMap;
    }
    
 public static void setNotNullValue(DestLogBean bean) {
        
        HttpServletRequest request = WebUtil.getCurrentRequest();
        LoginBean loginBean = MgrUtil.getSession(request);
        bean.setDestId(loginBean.getMgrId());
    }
}
