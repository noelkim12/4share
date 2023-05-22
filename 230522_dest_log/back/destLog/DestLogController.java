package com.srpost.cm.bo.si.destLog;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.srpost.cm.bo.base.mgr.IMgrService;

import com.srpost.salmon.web.mvc.controller.BaseController;

/**
 * 내부단 개인정보 파기이력 컨트롤러
 *
 * @author  
 * @date    2022-09-19
 * @since   1.0
 */
@Controller
@RequestMapping(value="/bo/si/destLog")
public class DestLogController extends BaseController {

    @Resource
    IDestLogService service;
    @Resource
    IMgrService mgrService;
    
    /**
     * 개인정보 파기 이력 메인
     */
    @RequestMapping(value="NR_index.do", method=RequestMethod.GET)
    public void index() {
    }
    
    /**
     * 개인정보 파기 이력 목록
     */
    @RequestMapping(value="JR_list.do", method=RequestMethod.GET)
    public ModelAndView list(DestLogBean bean, HttpServletRequest request, ModelMap model) {
        
        return responseJson(model, service.list(bean));
    }
    
    /**
     * 엑셀 변환 팝업창
     */
    @RequestMapping(value="PR_excelForm.do", method=RequestMethod.GET)
    public void excelForm() {
    }
    
    /**
     * 엑셀 변환 액션
     */
    @RequestMapping(value="XR_excelAction.do", method=RequestMethod.POST)
    public ModelAndView excelAction(DestLogBean bean, HttpServletRequest request, ModelMap model) {

        List<Map<String, Object>> dataList = service.listExcel(bean);

        return responseExcel(model, dataList, bean);
    }
    
}
