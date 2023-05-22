package com.srpost.cm.bo.si.destLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * 내부단 개인정보 파기 엑셀변환 row handler
 *
 * @author  allen
 * @date    2022-09-19
 * @since   1.0
 */
public class DestLogExcelRowHandler implements ResultHandler {

    private List<Map<String, Object>> list;

    public DestLogExcelRowHandler() {
        
        list = new ArrayList<Map<String, Object>>();
    }
    
    @Override
    public void handleResult(ResultContext context) {
        
        DestLogBean dataBean = (DestLogBean)context.getResultObject();

        Map<String, Object> dataMap = new HashMap<String, Object>();

        dataMap.put( "destId", dataBean.getDestId() );
        dataMap.put( "destNm", dataBean.getDestNm() ); 
        
        dataMap.put( "targetId", dataBean.getTargetId() );
        dataMap.put( "targetNm", dataBean.getTargetNm() );
        dataMap.put( "termInfo", dataBean.getTermInfo() );
        
        dataMap.put( "mthdNm", dataBean.getMthdNm() );
        dataMap.put( "destDt", dataBean.getDestDt() );
        
        list.add(dataMap);
    }
    
    public List<Map<String, Object>> getList() {
        
        return list;
    }
}