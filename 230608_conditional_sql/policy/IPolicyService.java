package com.srpost.va.policy;

import java.util.List;
import java.util.Map;

import com.srpost.salmon.bean.SmSearchBean;

/**
 * POLICY SERVICE
 * 
 * @author NOEL
 */
public interface IPolicyService {

    public List<Map<String, Object>> jsonData(String param);
    
    Integer updateCategoryAction(String param);
    
    Integer updatePolicyAction(String param);
    
    Integer insertCategoryAction(String param);
    
    Integer insertPolicyAction(String param);
    
    Integer crudPolicyConditionAction(String param);
    
    Integer togglePolicyBlindAction(String  civilSrvNum);

    Map<String, Object> vocList(SmSearchBean bean);
    
    Map<String, Object> vocListByCondition(SmSearchBean bean);

    public List<Integer> getVocSeqByCondition();

    List<Map<String, Object>> vocKindList();
    
    Map<String, Object> selectPolicyConidtion(String param);
    
    List<Map<String, Object>> listExcel(SmSearchBean bean);
    
}
