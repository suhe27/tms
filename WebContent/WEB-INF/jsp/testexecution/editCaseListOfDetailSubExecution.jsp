<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/page/taglib.jsp"%>

<%@ include file="/inc/includemobile2.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>Execution</title>
		<script type="text/javascript" src="<%=basePath %>scripts/testexecution.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body topmargin="0" leftmargin="0" class="mobile">
		<h6  align="center">${subExecution.subExecutionName}</h6>
		<form action="editCaseListOfSubExecution.action" method="post" onsubmit="return checkForm()">
		
		<input name="testResultLists[0].resultId" value="0" style="display:none"/>
		<input name="testResultLists[0].log" value="0" style="display:none"/>
		<input name="testResultLists[0].bugId" value="0" style="display:none"/>
		<input name="testResultLists[0].bugName" value="0" style="display:none"/>
		<input name="testResultLists[0].comments" value="0" style="display:none"/>
		<input name="testResultLists[0].resultTypeId" value="0" style="display:none"/>
		
		<display:table name="testresultlist" class="mobile" id="row" style="width:100%;" cellpadding="0" cellspacing="0">
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 2%;" >No.</th>
				<th style="width: 18%;">Test Case Name</th>
				<th style="width: 5%;">Result</th>
				<th style="width: 15%;">Log</th>
				<th style="width: 18%;">Bug Id/Name</th>
				<th style="width: 15%;">Comments</th>
				<th style="width: 13%;">Target</th>
				<th style="width: 7%;">Platform</th>
				<th style="width: 7%;">ExecutionOS</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<input name="testResultLists[${row_rowNum}].resultId" value="${row.resultId }" id="resultId" style="display:none"/> ${row_rowNum}
			</display:column>
			<display:column  property="testCaseName" />
			<display:column>
        		<select id="resultTypeId" name="testResultLists[${row_rowNum}].resultTypeId">
					<s:iterator value="resulttypelist" var="result">
    				<s:if test="%{#result.resultTypeId == #attr.row.resultTypeId}">
        				<option value="${result.resultTypeId}" selected="selected">${result.resultTypeName}</option>
    				</s:if>
    				<s:else>
        				<option value="${result.resultTypeId}">${result.resultTypeName}</option>
    				</s:else>
					</s:iterator>
        		</select>
			</display:column>
			<display:column>
			<input name="testResultLists[${row_rowNum}].log" value="${row.log }" id="log" />
			</display:column>
			<display:column  >
				<input name="testResultLists[${row_rowNum}].bugId" value="${row.bugId }" id="bugId" class="bugId"/>
				/<input name="testResultLists[${row_rowNum}].bugName" size="3" value="${row.bugName }" id="bugName" class="bugName"/>
			</display:column>
			<display:column>
				<input name="testResultLists[${row_rowNum}].comments" value="${row.comments }" id="comments"/>
			</display:column>
			<display:column  property="targetName" />
			<display:column  property="platformName" />
			<display:column  property="osName" />
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>
		<div style="width:100%;height:30px;border:1px gray dotted; text-align:center">
			<input type="submit" value=" Update " class="btn1" />
		</div>
		<div style="width:100%;height:30px;border:1px gray dotted;">
			<div style="float:left;width:60%;height:25px;">
	
			</div>
			<div style="float:right;width:38%;height:25px;">
				<p:page	action="toEditCaseListOfSubExecution.action" beanName="#pageBean"	classForId="pagination-digg" activeClass="active" linkOffClass="link-off" totalPageClass="total-page">
				<p:param name="subExecutionId" value="#subExecutionId"/>
				</p:page>
			</div>
		</div>
		<input name="subExecutionId" id="subExecutionId" value="${subExecution.subExecutionId}" style="display:none"/>
		</form>
	</body>	
	
<script type="text/javascript">
	function checkForm(){

	      var ua = window.navigator.userAgent
	      var msie = ua.indexOf ( "MSIE " )

	      if ( msie > 0 ) {     // If Internet Explorer, return version number
	    	  var bugs = document.getElementsByName('bugId');
	    	  var bugNames = document.getElementsByName('bugName');
	      }
	      else {                 // If another browser, return 0
	    	  var bugs = document.getElementsByClassName('bugId');
	    	  var bugNames = document.getElementsByClassName('bugName');
	      }	

        var len = bugs.length;
        
        for (var i=0; i<len; i++) {
        	var bugId = bugs[i].value;
        	var bugName = bugNames[i].value;
    		if(bugName.length > 0){
    			//alert(bugName[i].value + i);
    			if(bugId.length == 0){
    				alert("Row "+i+" error: BugId can't be null when BugName have value");
    				return false;
    			}
    		}
        	
    		if(bugId.length>0){
    	        var RegexUrl = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
    	        result = RegexUrl.test(bugId);
    	        if (!result){
    	        	j = i+1;
    				alert('Invalid URL address for BugId in row :' + j); 
    				return false;
    			}
    		}
        }
		
	}
	
</script>

	</html>