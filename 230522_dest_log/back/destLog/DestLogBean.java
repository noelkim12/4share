package com.srpost.cm.bo.si.destLog;

import org.apache.ibatis.type.Alias;

import com.srpost.cm.bo.base.login.LoginBean;
import com.srpost.salmon.bean.BaseListBean;

/**
 * 내부단 개인정보 파기 정보 Bean
 *
 * @author  allen
 * @date    2022-09-19
 * @since   1.0
 */
@Alias("destLogBean")
@SuppressWarnings("serial")
public class DestLogBean extends BaseListBean {
    
    /** 파기일련번호 */
    private Long destSeq;
    /** 파기자_ID */
    private String destId;
    /** 파기자_이름 */
    private String destNm;
    /** 파기대상_KEY */
    private String targetKey;
    /** 파기대상_ID */
    private String targetId;
    /** 파기대상_이름 */
    private String targetNm;
    /** 처리_방법 */
    private String mthdNm;
    /** 개인정보_이용기간 */
    private String termInfo;
    /** 파기_일시 */
    private String destDt;
    
    private LoginBean loginBean;

    public Long getDestSeq() {
        return destSeq;
    }

    public void setDestSeq(Long destSeq) {
        this.destSeq = destSeq;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getDestNm() {
        return destNm;
    }

    public void setDestNm(String destNm) {
        this.destNm = destNm;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetNm() {
        return targetNm;
    }

    public void setTargetNm(String targetNm) {
        this.targetNm = targetNm;
    }

    public String getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(String termInfo) {
        this.termInfo = termInfo;
    }

    public String getMthdNm() {
        return mthdNm;
    }

    public void setMthdNm(String mthdNm) {
        this.mthdNm = mthdNm;
    }

    public String getDestDt() {
        return destDt;
    }

    public void setDestDt(String destDt) {
        this.destDt = destDt;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    
}
