package com.srpost.va.policy;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.srpost.salmon.lang.StringUtil;

public class PolicyConditionProvider {

    public String selectVocSeqs(Map<String, Object> dataMap) {

        SQL sql = new SQL() {{
            SELECT("CIVIL_SRV_NUM");
            FROM("POLICY_SUMMARY_TBL");
            WHERE("1=1");
        }};
        
        String condition = (String) dataMap.get("CONDITION");
        
        if ( StringUtil.isNotEmpty(condition) ) {
            String[] conditionArray = StringUtil.split(condition, "^");
            
            for (String item : conditionArray) {
                String[] splitItem = item.split(" ");
                String operator = splitItem[0];
                
                if ( StringUtil.equals(operator, "AND") ) sql.AND();
                if ( StringUtil.equals(operator, "OR")  ) sql.OR();
                
                item = item.replaceFirst(operator, "");
                sql.WHERE(item);
            }
        }
        
        Integer policySeq = (Integer)dataMap.get("POLICY_SEQ");
        
        if ( StringUtil.isNotEmpty(policySeq) ) {
            sql.AND();
            sql.WHERE("POLICY_CD = (SELECT Z.POLICY_CD "
                    + "               FROM POLICY Z "
                    + "              WHERE Z.POLICY_SEQ = " +policySeq + ")");
        }
        
        sql.AND();
        sql.WHERE("ANAL_DT  >=  TO_DATE(TO_CHAR(SYSDATE-14, 'YYYY-MM-DD') || ' 00:00:00', 'YYYY-MM-DD HH24:MI:SS')");
        return sql.toString();
    }

    public String selectVocList(Map<String, Object> dataMap) {

        String selectVocSeqsSql = this.selectVocSeqs(dataMap);
        
        StringBuilder sb = new StringBuilder();
        sb.append("     SELECT Y.* FROM (\r\n");
        sb.append("            SELECT ROWNUM NUM, X.* FROM (\r\n");
        sb.append("                SELECT ROW_NUMBER() OVER(ORDER BY A.CIVIL_SRV_NUM DESC) AS ORDER_NO\r\n");
        sb.append("                     , A.CIVIL_SRV_NUM\r\n");
        sb.append("                     , A.TITLE\r\n");
        sb.append("                     , A.CONTENT\r\n");
        sb.append("                     , A.ANSWER\r\n");
        sb.append("                     , (SELECT DISTINCT Z.BLIND_YN FROM POLICY_SUMMARY_TBL Z WHERE A.CIVIL_SRV_NUM = Z.CIVIL_SRV_NUM) AS BLIND_YN\r\n");
        sb.append("                     , (SELECT Z.ALL_DEPT_NM FROM ORG_CHART Z WHERE Z.DEPT_CD = A.PROC_DEPT_CD) AS DEPT_NM\r\n");
        sb.append("                     , (SELECT Z.USER_NM FROM OPERATOR_GRP Z WHERE Z.USER_ID = A.PROC_ID) AS PROC_NM\r\n");
        sb.append("                     , TO_CHAR(A.PROC_DT, 'YYYY.MM.DD') AS PROC_DT\r\n");
        sb.append("                     , TO_CHAR(A.REAL_REG_DT, 'YYYY.MM.DD') AS DAY\r\n");
        sb.append("                  FROM MINWON_MAIN A\r\n");
        sb.append("                 WHERE 1=1\r\n");

        this.conditionBuilder(sb, dataMap);
        
        sb.append("                AND A.CIVIL_SRV_NUM IN (\r\n");
        sb.append(selectVocSeqsSql);
        sb.append("\r\n                )\r\n");
        sb.append("            ) X WHERE ROWNUM <= "+ dataMap.get("END_NUM") +"\r\n");
        sb.append("        ) Y WHERE NUM >= "+ dataMap.get("START_NUM"));
        return sb.toString();
    }

    public String selectVocListCount(Map<String, Object> dataMap) {

        String selectVocSeqsSql = this.selectVocSeqs(dataMap);
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(CIVIL_SRV_NUM)");
        sb.append("   FROM MINWON_MAIN A\r\n");
        sb.append("  WHERE 1=1\r\n");

        this.conditionBuilder(sb, dataMap);
        
        sb.append("    AND A.CIVIL_SRV_NUM IN (\r\n");
        sb.append(selectVocSeqsSql);
        sb.append("\r\n)\r\n");
        
        return sb.toString();
    }
    
    public StringBuilder conditionBuilder(StringBuilder sb, Map<String, Object> dataMap) {

        if ( StringUtil.isEmpty(dataMap.get("BASE_DT_TYPE")) ) {
            sb.append("    AND A.REAL_REG_DT  >=  TO_DATE(TO_CHAR(SYSDATE-14, 'YYYY-MM-DD') || ' 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\r\n");
            sb.append("    AND A.REAL_REG_DT  <=  SYSDATE\r\n");
        }
        if ( StringUtil.isNotEmpty(dataMap.get("BASE_DT_TYPE")) ) {
            sb.append("    AND A."+ dataMap.get("BASE_DT_TYPE")+"  >=  TO_DATE('"+ dataMap.get("ST_DT")+"' || ' 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\r\n");
            sb.append("    AND A."+ dataMap.get("BASE_DT_TYPE")+"  >=  TO_DATE(TO_CHAR(SYSDATE-14, 'YYYY-MM-DD') || ' 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\r\n");
            sb.append("    AND A."+ dataMap.get("BASE_DT_TYPE")+"  <=  SYSDATE\r\n");
        }
        if ( StringUtil.isNotEmpty(dataMap.get("SEARCH_VAL")) ) {
            sb.append("    AND A."+ dataMap.get("SEARCH_KEY")+" LIKE '%' || '" + dataMap.get("SEARCH_VAL") +"' || '%'");
        }
        if ( StringUtil.isNotEmpty(dataMap.get("KIND_CD")) ) {
            if ( !StringUtil.equals((String)dataMap.get("KIND_CD"), "all") ) {
                sb.append("    AND A.CIVIL_KIND_CD = '"+ dataMap.get("KIND_CD") +"'");
            }
        }
        
        return sb;
    }
}
