package com.srpost.va.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.srpost.salmon.bean.SmSearchBean;
import com.srpost.salmon.lang.StringUtil;
import com.srpost.salmon.spring.mvc.SmBaseService;

/**
 * POLICY SERVICE IMPLEMENT
 * 
 * @author NOEL
 */
@Service
public class PolicyServiceImpl extends SmBaseService implements IPolicyService  {

    @Resource
    private PolicyDao dao;

    @Override
    public List<Map<String, Object>> jsonData(String param) {

        return dao.jsonData(param);
    }

    @Override
    public Integer updateCategoryAction(String param) {

        return dao.updateCategoryAction(param);
    }

    @Override
    public Integer updatePolicyAction(String param) {

        return dao.updatePolicyAction(param);
    }

    @Override
    public Integer insertCategoryAction(String param) {

        return dao.insertCategoryAction(param);
    }

    @Override
    public Integer insertPolicyAction(String param) {

        return dao.insertPolicyAction(param);
    }

    @Override
    @SuppressWarnings("serial")
    public Map<String, Object> vocList(SmSearchBean bean) {

        int cp = bean.getCp();
        int rp = bean.getRpp();

        if (StringUtil.isEmpty(cp)) {
            cp = 1;
        }
        if (StringUtil.isEmpty(rp)) {
            rp = 10;
        }

        bean.setStartNum(((cp - 1) * rp) + 1);
        bean.setEndNum(cp * rp);
        
        if ( StringUtil.isNotEmpty(bean.getSv10())) {
            Map<String, Object> paramMap = new HashMap<String, Object>() {{
                put("POLICY_SEQ", Integer.valueOf(bean.getSv10()));
            }};
            
            Map<String, Object> conditionMap = dao.selectPolicyCondition(paramMap);
            List<Integer> seqList = dao.getVocSeqByCondition(conditionMap);
            
            bean.setSeqList(seqList);
            bean.setSv04((String)conditionMap.get("BASE_DT_TYPE"));
            bean.setSv05((String)conditionMap.get("ST_DT"));
            bean.setSv06((String)conditionMap.get("KIND_CD"));
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", dao.vocList(bean));
        result.put("count", dao.vocListCount(bean));
        
        return result;
    }

    @Override
    @SuppressWarnings("serial")
    public Map<String, Object> vocListByCondition(SmSearchBean bean) {

        int cp = bean.getCp();
        int rp = bean.getRpp();

        if (StringUtil.isEmpty(cp)) {
            cp = 1;
        }
        if (StringUtil.isEmpty(rp)) {
            rp = 10;
        }

        bean.setStartNum(((cp - 1) * rp) + 1);
        bean.setEndNum(cp * rp);
        
        Map<String, Object> paramMap = new HashMap<String, Object>() {{
            put("POLICY_SEQ", Integer.valueOf(bean.getSv10()));
        }};
        
        Map<String, Object> conditionMap = new HashMap<String, Object>(); 
        
        conditionMap = dao.selectPolicyCondition(paramMap);
        if ( StringUtil.isEmpty(conditionMap) ) conditionMap = new HashMap<String, Object>();
         
        conditionMap.put("POLICY_SEQ", Integer.valueOf(bean.getSv10()));
        conditionMap.put("START_NUM", bean.getStartNum());
        conditionMap.put("END_NUM", bean.getEndNum());
        conditionMap.put("SEARCH_VAL", bean.getSv());
        conditionMap.put("SEARCH_KEY", bean.getSk());
        

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", dao.getVocListByCondition(conditionMap));
        result.put("count", dao.getVocListCountByCondition(conditionMap));

        return result;
    }

    @Override
    public List<Integer> getVocSeqByCondition() {
        
        return dao.getVocSeqByCondition(null);
    }
    
    @Override
    public List<Map<String, Object>> vocKindList() {

        return dao.vocKindList();
    }

    @Override
    public Integer crudPolicyConditionAction(String param) {

        return dao.crudPolicyConditionAction(param);
    }

    @Override
    public Map<String, Object> selectPolicyConidtion(String param) {

        return dao.selectPolicyCondition(param);
    }

    @Override
    public List<Map<String, Object>> listExcel(SmSearchBean bean) {

        return dao.listExcel(bean);
    }

    @Override
    public Integer togglePolicyBlindAction(String  civilSrvNum) {

        return dao.togglePolicyBlindAction(civilSrvNum);
    }
    
    
}
