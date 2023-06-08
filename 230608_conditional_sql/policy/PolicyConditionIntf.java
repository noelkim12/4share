package com.srpost.va.policy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.srpost.va.stat.pi.issue.StatIssueBean;

public interface PolicyConditionIntf {

    @SelectProvider(type=PolicyConditionProvider.class, method="selectVocSeqs")
    List<Integer> selectVocSeqs(Map<String, Object> paramMap);
    
    @SelectProvider(type=PolicyConditionProvider.class, method="selectVocList")
    List<StatIssueBean> selectVocList(Map<String, Object> paramMap);

    @SelectProvider(type=PolicyConditionProvider.class, method="selectVocListCount")
    Integer selectVocListCount(Map<String, Object> paramMap);
    
}
