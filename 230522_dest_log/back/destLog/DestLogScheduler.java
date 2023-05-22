package com.srpost.cm.bo.si.destLog;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srpost.cm.bo.base.user.UserBean;
import com.srpost.salmon.lang.StringUtil;
import com.srpost.salmon.scheduelr.BaseScheduler;
import com.srpost.salmon.spi.crypto.SalmonCrypto;

import static com.srpost.salmon.constant.StringPool.*;

/**
 * VOC 개인정보 자동파기 스케줄러 - 개인정보 동의기간이 지나면 자동적으로 파기
 * 
 * @author  allen
 * @date    2022-10-14
 * @since   1.0
 */
public class DestLogScheduler extends BaseScheduler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    IDestLogService destLogService;
    
    @Resource
    SalmonCrypto salmonCtypto;

	public void doSchedule() {

        //if ( isExcludeServer() ) return;
        
        logger.info("--------------------------------------------");
        logger.info("개인정보 자동파기 스케쥴러 시작");
        
        List<UserBean> userList = destLogService.destLogScheduler();
        
        int destCnt = ZERO; // 파기 성공 건수
	    
	    if (StringUtil.isEmpty(userList)) {
	        logger.info("대상 자동파기 건이 존재하지 않음, 개인정보 자동파기 스케쥴러 종료");
	    }
	    else {
    	    logger.info("대상 {}명의 자동파기 건, 파기 및 DB 작업 시작", userList.size());
    	    
    	    
            for(UserBean userBean : userList) {
                // 휴대폰 번호 복호화 후 마스킹 처리
                String orgMobile = salmonCtypto.decrypt(userBean.getUserEncMobile());
                String regex = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";
                String maskMobile = "";
                
                Matcher matcher = Pattern.compile(regex).matcher(orgMobile);
                
                if(matcher.find()) {
                    String target = matcher.group(2);
                    int length = target.length();
                    char[] c = new char[length];
                    Arrays.fill(c, '*');
                    
                    maskMobile = orgMobile.replace(target, String.valueOf(c));
                    userBean.setUserEncMobile(salmonCtypto.encrypt(maskMobile));
                }
                
                int maskFlag = update("_user.updateMasking", userBean);
                
                if(maskFlag < ONE) {
                    logger.error("유저 "+userBean.getUserKey()+"의 자동파기 로그 건, 파기 및 DB 반영 실패");
                } else {
                    // 파기 로그 작성
                    DestLogBean dataBean = new DestLogBean();
                    dataBean.setDestId("scheduler");
                    dataBean.setDestNm("자동스케쥴러");
                    dataBean.setTargetKey(userBean.getUserKey());
                    dataBean.setTargetId(userBean.getUserId());
                    dataBean.setTargetNm(userBean.getUserNm());
                    dataBean.setTermInfo("3년");
                    
                    int affected = destLogService.destLogAction(dataBean);
                    destCnt++;
                        
                    if(affected > ZERO)
                        logger.info("유저 "+dataBean.getTargetKey()+"의 자동파기 로그 건, 파기 및 DB 반영 완료");
                    else
                        logger.warn("유저 "+dataBean.getTargetKey()+"의 자동파기 로그 건, 파기 및 DB 반영 완료했지만 로그 기록 실패");
                    
                }
            }
    	    
    	    logger.info("대상 {}명의 자동파기 건, 파기 및 DB 작업 완료", userList.size());
    	    logger.info("개인정보 자동파기 성공한 건수 => " + destCnt);
	    }
	    
	    
	    logger.info("개인정보 자동파기 스케쥴러 종료");
	    logger.info("--------------------------------------------");
	}
}
