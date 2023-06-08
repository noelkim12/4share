package com.srpost.va.policy;

import static com.srpost.salmon.bean.error.SmError.ERR_BSNS_UPDATE;
import static com.srpost.salmon.bean.success.SmSuccess.SCS_BSNS_UPDATE;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.srpost.salmon.bean.SmSearchBean;
import com.srpost.salmon.spring.mvc.SmBaseController;

/**
 * POLICY Controller
 * 
 * @author NOEL
 * @version 0.1
 */
@Controller
@RequestMapping("/va/policy")
public class PolicyController extends SmBaseController {

    @Resource
    private IPolicyService service;
    
    @GetMapping({"/index.do", "/condition/index.do"})
    public void index(ModelMap model, HttpServletRequest request) {
        
    }
    
    @GetMapping("/p_category.do")
    public void categoryPop(ModelMap model, HttpServletRequest request) {
        
    }
    
    @GetMapping("/p_policy.do")
    public void policyPop(ModelMap model, HttpServletRequest request) {
        
    }

    @GetMapping("/condition/p_condition.do")
    public void conditionPop(ModelMap model, HttpServletRequest request) {

        model.addAttribute("kindList", service.vocKindList());
    }
    
    @GetMapping("/condition/p_vocPop.do")
    public void vocPop(ModelMap model, HttpServletRequest request) {

    }
    
    @PostMapping("/j_jsonData.do")
    @ResponseBody
    public List<Map<String, Object>> jsonData(ModelMap model, HttpServletRequest request, 
            @RequestParam(required=true, value="param") String param ) {
        
        return service.jsonData(param);
    }
    
    @PostMapping("/t_updateCategoryAction.do")
    @ResponseBody
    public ResponseEntity<?> t_updateCategoryAction(HttpServletRequest request,
            @RequestParam(required=true, value="param") String param) {
        
        int affected = service.updateCategoryAction(param);
        if (affected == 1) {
            return success(SCS_BSNS_UPDATE, affected);
        }
        return error(ERR_BSNS_UPDATE);
    }
    
    @PostMapping("/t_updatePolicyAction.do")
    @ResponseBody
    public ResponseEntity<?> t_updatePolicyAction(HttpServletRequest request,
            @RequestParam(required=true, value="param") String param) {
        
        int affected = service.updatePolicyAction(param);
        if (affected == 1) {
            return success(SCS_BSNS_UPDATE, affected);
        }
        return error(ERR_BSNS_UPDATE);
    }
    
    @PostMapping("/t_insertCategoryAction.do")
    @ResponseBody
    public ResponseEntity<?> insertCategoryAction(HttpServletRequest request,
            @RequestParam(required=true, value="param") String param) {
        
        int affected = service.insertCategoryAction(param);
        if (affected == 1) {
            return success(SCS_BSNS_UPDATE, affected);
        }
        return error(ERR_BSNS_UPDATE);
    }
    
    @PostMapping("/t_insertPolicyAction.do")
    @ResponseBody
    public ResponseEntity<?> insertPolicyAction(HttpServletRequest request,
            @RequestParam(required=true, value="param") String param) {
        
        int affected = service.insertPolicyAction(param);
        if (affected == 1) {
            return success(SCS_BSNS_UPDATE, affected);
        }
        return error(ERR_BSNS_UPDATE);
    }

    @GetMapping("/condition/j_selectPolicyCondition.do")
    @ResponseBody
    public Map<String, Object> selectPolicyCondtion(HttpServletRequest request,
            @RequestParam(required=true, value="param") String param) {
        
        return service.selectPolicyConidtion(param);
    }
    
    @PostMapping("/condition/t_crudPolicyConditionAction.do")
    @ResponseBody
    public ResponseEntity<?> crudPolicyConditionAction(HttpServletRequest request,
            @RequestParam(required=true, value="param") String param) {
        
        int affected = service.crudPolicyConditionAction(param);
        if (affected == 1) {
            return success(SCS_BSNS_UPDATE, affected);
        }
        return error(ERR_BSNS_UPDATE);
    }
    
    @PostMapping("/a_vocList.do")
    public void vocList(SmSearchBean bean, ModelMap model, HttpServletRequest request) {
        
    }

    @PostMapping("/j_list.do")
    @ResponseBody
    public Map<String, Object> list(SmSearchBean bean, HttpServletRequest request, ModelMap model) {
        return service.vocList(bean);
    }
    
    @PostMapping("/condition/j_listByCondition.do")
    @ResponseBody
    public Map<String, Object> listByCondition(SmSearchBean bean, HttpServletRequest request, ModelMap model) {
        return service.vocListByCondition(bean);
    }
    
    /**
     * 이슈 엑셀다운
     */
    @PostMapping("/x_excelAction.do")
    public ModelAndView xlsList(SmSearchBean bean) {

        return downloadExcel(service.listExcel(bean), bean);
    }
    
    @PostMapping("/condition/t_togglePolicyBlindAction.do")
    @ResponseBody
    public ResponseEntity<?> togglePolicyBlindAction(HttpServletRequest request,
            @RequestParam(required=true, value="civilSrvNum") String civilSrvNum) {
        
        int affected = service.togglePolicyBlindAction(civilSrvNum);
        if (affected == 1) {
            return success(SCS_BSNS_UPDATE, affected);
        }
        return error(ERR_BSNS_UPDATE);
    }
}
