<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.srpost.co.kr/jsp/taglib" prefix="sm" %>

<sm:static var="CONSTANT" clazz="com.srpost.salmon.constant.Constant"/>
<sm:static var="VOC_CONSTANT" clazz="com.srpost.cm.bo.fm.voc.core.VocConstant"/>
<c:set var="mgrSession" value="${sessionScope[CONSTANT.MGR_SESSION_KEY]}" />

<html>
<head>
    <title>${curMenuBean.menuNm}</title>
    <meta name="title" content="개인정보 파기이력을 조회합니다." />
</head>
 
<body>

<form:form id="csrfForm" name="csrfForm"></form:form>

<table class="tbl-header">
    <tr>
        <th style="width: 120px;">
            <span class="title-big">개인정보 파기이력</span>
        </th>
        <td class="tr">
            <form name="dataForm" method="get" onsubmit="return js_search();">
                
                <div id="startDt" class="smf-date"></div>
                <div id="endDt" class="smf-date"></div>
                
                <select name="searchKey" id="searchKey" class="smf-select">
                    <option value="1000">파기자</option>
                    <option value="1001">파기대상 아이디</option>
                    <option value="1002">파기대상자</option>
                    <option value="1003">처리방법</option>
                </select>
                <input type="text" name="searchVal" id="searchVal" value="" class="smf-text"/>
                <sm:button type="submit" glyph="search" value="검색" cssClass="btn-default"/>
                <span id="resetSearchDiv" class="sr-hidden">
                    <sm:button value="해제" cssClass="btn-default" onclick="js_resetSearch();" />
                </span>
            </form>
        </td>
    </tr>
</table>

<div id="dataGrid"></div>

<sm:button-layout align="both">
    <div class="col-xs-7">
        <div class="btn-group">
            <%-- <sm:button value="파기" glyph="remove" onclick="js_destAction(this)"/> --%>
        </div>
    </div>
    <div class="col-xs-5 text-right">
        <sm:button value="엑셀" glyph="floppy-save" onclick="js_excelAction(this);"/>
    </div>
</sm:button-layout>

<div id="viewDiv"></div>

<sm:js names="smgrid" />
<script type="text/javascript" src="<c:url value="/resources/spi/jquery/fullcalendar/lib/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/spi/jquery/fullcalendar/lib/moment.precise.range.js"/>"></script>
<script type="text/javascript">

    Ext.onReady(function() {

        SM_GRID({
            id: "gridPanel",
            renderTo: "dataGrid",
            dataListUrl: "JR_list.do",
            height: 265,
            showRownum: true,
            pageSize: "<c:out value='${empty smp.rowPerPage ? CONSTANT.RPP_CNT : smp.rowPerPage}' />",
            dataField: ["destSeq", "destId", "destNm", "targetId", "targetNm", "mthdNm", "termInfo", "destDt"],
            dataColumns: [
                { text: "파기자", dataIndex: "destId", sortable: false, width: 100 },
                { text: "파기대상 아이디", dataIndex: "targetId", sortable: false, width: 110 },
                { text: "파기대상자", dataIndex: "targetNm", sortable: false, width: 215 },
                { text: "처리방법", dataIndex: "mthdNm", sortable: false, width: 60 },
                { text: "개인정보 이용기간", dataIndex: "termInfo", sortable: false, width: 165 },
                { text: "파기일시", dataIndex: "destDt", sortable: false, width: 135 }
            ],
            itemClick: function(record, panel) {
                panel.setLoading(false);
            }
        });
        
        $(".smf-date").SM_DATEPICKER();
    });
    
    var js_search = function() {

        var searchKey = $("#searchKey").val(),
            searchVal = $("#searchVal").val(),
            startDt = $("input[name=startDt]").val(),
            endDt = $("input[name=endDt]").val();
        
        if (startDt != "" && endDt != "") {
            var sDt = startDt.split("-").join("");
            var eDt = endDt.split("-").join("");
            if (sDt > eDt) {
                alert("검색 시작일자가 종료 일자보다 클 수 없습니다.\n\n검색 기한을 다시 확인하세요.");
                return false;
            }
        }
        
        SM.getCmp("gridPanel").reload({
            currentPage: 1
        }, {
            searchKey: searchKey,
            searchVal: searchVal,
            startDt: startDt,
            endDt: endDt
        }, function() {
            if ( startDt != "" || endDt != "" || (searchKey != "" && searchVal != "") )
                $("#resetSearchDiv").show();
            $("#viewDiv").html("");
        });
        
        return false;
    };
    
    var js_resetSearch = function() {
        
        SM.getCmp("gridPanel").reload({
            currentPage: 1
        }, {
        }, function() {
            $("#resetSearchDiv").hide();
            $("#searchVal, input[name=startDt], input[name=endDt]").val("");
            $("#searchKey option")[0].selected = true;
            $("#viewDiv").html("");
        });
    };
    
    var js_excelAction = function() {

        var totalCount = SM.getCmp("gridPanel").store.totalCount;
        if (totalCount <= 0) {
            alert("대상 자료가 존재하지 않습니다."); return false; 
        }
        
        SM.modal({
            title: "엑셀 변환 옵션 선택",
            width: 640,
            height: 410,
            loadUrl: "PR_excelForm.do"
        });
    };

    var js_getSearchCondition = function() {
        var gridStore = SM.getCmp("gridPanel").store;
        var sorter = gridStore.sorters.items[0] || {}; 
        return {
            currentPage: gridStore.currentPage,
            rowPerPage: gridStore.pageSize,
            sortName: sorter.property,
            sortOrder: sorter.direction,
            searchKey: $("#searchKey").val(),
            searchVal: $("#searchVal").val(),
            startDt: $("input[name=startDt]").val(),
            endDt: $("input[name=endDt]").val()
        };
    };
    
    
</script>
    
</body>
</html>