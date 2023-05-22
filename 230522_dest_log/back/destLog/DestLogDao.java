package com.srpost.cm.bo.si.destLog;

import static com.srpost.salmon.constant.StringPool.ONE;
import static com.srpost.salmon.constant.StringPool.ZERO;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.srpost.cm.bo.base.login.LoginBean;
import com.srpost.cm.bo.base.user.UserBean;
import com.srpost.salmon.bean.BasePagerBean;
import com.srpost.salmon.lang.StringUtil;
import com.srpost.salmon.spi.egov.ISalmonSeqGenerator;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;


/**
 * 내부단 개인정보 파기 DAO
 *
 * @author  allen
 * @date    2022-09-19
 * @since   1.0
 */
@Repository
public class DestLogDao extends EgovAbstractMapper {

    @Resource(name = "destLogSeqGenerator")
    ISalmonSeqGenerator seqGenerator;
    
    public BasePagerBean list(DestLogBean bean) {
        
        Map<String, Object> parameterMap = DestLogUtil.getParameterMap(bean);
        
        List<DestLogBean> dataList = selectList("_destLog.list", parameterMap);
        int totalCount = (Integer)selectOne("_destLog.listCount", parameterMap);

        return new BasePagerBean(dataList, totalCount, bean);
    }
    
    public List<Map<String, Object>> listExcel(DestLogBean bean) {
        
        Map<String, Object> parameterMap = DestLogUtil.getParameterMap(bean);
        
        DestLogExcelRowHandler rowHandler = new DestLogExcelRowHandler();
        
        if ( StringUtil.equals(bean.getXlsScope(), LoginBean.SCOPE_TOTAL)) {
            getSqlSession().select("_destLog.listExcel", parameterMap, rowHandler);
        }
        else {
            getSqlSession().select("_destLog.list", parameterMap, rowHandler);
        }
        
        return rowHandler.getList();
    }

    public int insertAction(DestLogBean bean) {
        
        DestLogUtil.setNotNullValue(bean);
        
        bean.setDestSeq(seqGenerator.getNextLong());
        
        int affected = insert("_destLog.insert", bean);
        
        return affected;

    }

    public List<UserBean> destLogScheduler() {
        return selectList("_user.listMasking");
    }

    public int destLogAction(DestLogBean bean) {
        // 스케쥴러인지 수동인지 판별
        LoginBean loginBean = bean.getLoginBean();
        if (loginBean == null)
            bean.setMthdNm("자동삭제");
        else 
            bean.setMthdNm("수동삭제");
        
        int affected = update("_user.dest", bean); // 개인정보 파기
        
        if(affected < ONE) {
            return ZERO;
        }
        
        bean.setDestSeq(seqGenerator.getNextLong()); // 다음 시퀀스
        
        return insert("_destLog.insert", bean); // 파기 로그 추가
    }
}
