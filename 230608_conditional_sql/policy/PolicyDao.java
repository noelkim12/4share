package com.srpost.va.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.srpost.bo.base.mgr.MgrBean;
import com.srpost.bo.base.mgr.MgrUtil;
import com.srpost.salmon.bean.SmSearchBean;
import com.srpost.salmon.lang.StringUtil;
import com.srpost.salmon.spring.mvc.SmBaseDao;
import com.srpost.va.env.user.EnvUserDao;
import com.srpost.va.stat.pi.issue.StatIssueBean;

/**
 * POLICY DAO
 * @author NOEL
 */
@Repository
public class PolicyDao extends SmBaseDao {

    @Resource
    EnvUserDao envUserDao;
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> jsonData(String param) {
        
        // 소속기관 (자치구 일때) 체크
        MgrBean mgrBean = MgrUtil.getBeanFromSession();
        Gson gs = new Gson();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap = gs.fromJson(param, parameterMap.getClass());
        
        if (StringUtil.isNotEmpty(mgrBean) ) {
            if (StringUtil.equals(mgrBean.getOrgUpperCd(), "V000050")) {
                parameterMap.put("isLocal", true);
                parameterMap.put("orgCd", mgrBean.getOrgCd());
                parameterMap.put("deptCd", mgrBean.getDeptCd());
            }
            else {
                parameterMap.put("isLocal", false);
                parameterMap.put("orgCd", "seoulSi");
                parameterMap.put("deptCd", "seoulSi");
            }
        }
         
        List<Map<String, Object>> dataList = selectList("_policy." + (String)parameterMap.get("dataType"), parameterMap);
        
        return dataList;
    }

    @SuppressWarnings(value="unchecked")
    public Integer updateCategoryAction(String param) {

        Gson gs = new Gson();
        MgrBean mgrBean = MgrUtil.getBeanFromSession();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap = gs.fromJson(param, parameterMap.getClass());
        
        parameterMap.put("MOD_USER_ID", mgrBean.getMgrId());
        parameterMap.put("MOD_USER_NM", mgrBean.getMgrNm());
        
        envUserDao.insertAction("정책 카테고리 수정");
        
        return update("_policy.updateCategoryAction", parameterMap);
    }

    @SuppressWarnings(value="unchecked")
    public Integer updatePolicyAction(String param) {

        Gson gs = new Gson();
        MgrBean mgrBean = MgrUtil.getBeanFromSession();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap = gs.fromJson(param, parameterMap.getClass());
        
        parameterMap.put("MOD_USER_ID", mgrBean.getMgrId());
        parameterMap.put("MOD_USER_NM", mgrBean.getMgrNm());

        envUserDao.insertAction("정책 수정");
        
        return update("_policy.updatePolicyAction", parameterMap);
    }

    @SuppressWarnings(value="unchecked")
    public Integer insertCategoryAction(String param) {

        Gson gs = new Gson();
        MgrBean mgrBean = MgrUtil.getBeanFromSession();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap = gs.fromJson(param, parameterMap.getClass());
        
        parameterMap.put("ORG_CD", StringUtil.equals(mgrBean.getOrgCd(), "V000010") ? "seoulSi" : mgrBean.getOrgCd());
        parameterMap.put("ORG_NM", StringUtil.equals(mgrBean.getOrgNm(), "V000010") ? "서울시" : mgrBean.getOrgNm());
        parameterMap.put("USER_ID", mgrBean.getMgrId());
        parameterMap.put("OWNER", mgrBean.getMgrNm());

        envUserDao.insertAction("정책 카테고리 등록");
        
        return update("_policy.insertCategoryAction", parameterMap);
    }

    @SuppressWarnings(value="unchecked")
    public Integer insertPolicyAction(String param) {
        
        Gson gs = new Gson();
        MgrBean mgrBean = MgrUtil.getBeanFromSession();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap = gs.fromJson(param, parameterMap.getClass());

        parameterMap.put("ORG_CD", StringUtil.equals(mgrBean.getOrgCd(), "V000010") ? "seoulSi" : mgrBean.getOrgCd());
        parameterMap.put("ORG_NM", StringUtil.equals(mgrBean.getOrgNm(), "V000010") ? "서울시" : mgrBean.getOrgNm());
        parameterMap.put("USER_ID", mgrBean.getMgrId());
        parameterMap.put("OWNER", mgrBean.getMgrNm());

        envUserDao.insertAction("정책 등록");
        
        return update("_policy.insertPolicyAction", parameterMap);
    }

    public List<Integer> getVocSeqByCondition(Map<String, Object> parameterMap) {
        
        SqlSession sqlSession = this.getSqlSession();
        
        PolicyConditionIntf pci = sqlSession.getMapper(PolicyConditionIntf.class);

        List<Integer> seqList = pci.selectVocSeqs(parameterMap);
        
        return seqList;
    }
    
    public List<StatIssueBean> getVocListByCondition(Map<String, Object> parameterMap) {
        
        SqlSession sqlSession = this.getSqlSession();
        
        PolicyConditionIntf pci = sqlSession.getMapper(PolicyConditionIntf.class);
        
        List<StatIssueBean> vocList = pci.selectVocList(parameterMap);
        
        return vocList;
    }
    
    public Integer getVocListCountByCondition(Map<String, Object> parameterMap) {
        
        SqlSession sqlSession = this.getSqlSession();
        
        PolicyConditionIntf pci = sqlSession.getMapper(PolicyConditionIntf.class);
        
        Integer vocCount = pci.selectVocListCount(parameterMap);
        
        return vocCount;
    }
    
    
    
    public Map<String, Object> policyConditionMap(Map<String, Object> parameterMap) {
        
        return selectOne("_policy.selectPolicyCondition", parameterMap);
    }

    public List<StatIssueBean> vocList(SmSearchBean bean) {

        return selectList("_policy.vocList", bean);
    }

    public int vocListCount(SmSearchBean bean) {
        
        return selectOne("_policy.vocListCount", bean);
    }
    

    public List<Map<String, Object>> vocKindList() {

        return selectList("_policy.vocKindList");
    }

    @SuppressWarnings(value="unchecked")
    public Integer crudPolicyConditionAction(String param) {
        
        Gson gs = new Gson();
        Map<String, Object> parameterMap = new HashMap<String, Object>();

        parameterMap = gs.fromJson(param, parameterMap.getClass());
        
        Map<String, Object> policyConditionMap = selectOne("_policy.selectPolicyCondition", parameterMap);
        
        if ( StringUtil.isNotEmpty(policyConditionMap)) {
            envUserDao.insertAction("정책 검색조건 수정");
            return update("_policy.updatePolicyConditionAction", parameterMap);
        }

        envUserDao.insertAction("정책 검색조건 등록");
        return insert("_policy.insertPolicyConditionAction", parameterMap);
    }

    @SuppressWarnings(value="unchecked")
    public Map<String, Object> selectPolicyCondition(String param) {

        Gson gs = new Gson();
        Map<String, Object> parameterMap = new HashMap<String, Object>();

        parameterMap = gs.fromJson(param, parameterMap.getClass());
        
        return selectOne("_policy.selectPolicyCondition", parameterMap);
    }
    
    public Map<String, Object> selectPolicyCondition(Map<String, Object> parameterMap) {
        
        return selectOne("_policy.selectPolicyCondition", parameterMap);
    }

    public List<Map<String, Object>> listExcel(SmSearchBean bean) {

        envUserDao.insertAction("정책 민원 엑셀 다운로드");
        return selectListExcel("_policy.vocListAll", bean);
    }
    
    public Integer togglePolicyBlindAction(String  civilSrvNum) {

        envUserDao.insertAction("정책 비활성화");
        return update("_policy.togglePolicyBlindAction", civilSrvNum);
    }
}
