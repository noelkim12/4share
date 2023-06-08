package com.srpost.va.policy;

import org.apache.ibatis.type.Alias;

import com.srpost.salmon.bean.SmBaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * POLICY BEAN
 * 
 * @author NOEL
 */
@Getter @Setter
@Alias("policyBean")
@SuppressWarnings("serial")
public class PolicyBean extends SmBaseBean {
    private String startDt;
    private String endDt;
    private String type;
    private String statName;
    private String deptCd;
    private String cateCd;
    private String cateNm;
    private String orgCd;
    private String orgNm;
    private String userId;
    private String owner;
    private String modUserId;
    private String modUserNm;
    private String isUse;
    private String isDel;
    private String modDt;
    private String cateOrd;
    private String sysCd;
    private String sysNm;
    private String cateClr;
}
