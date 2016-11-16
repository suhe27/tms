
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/includemobile2.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Execution</title>
<style type="text/css">
@import url("<%=basePath%>css/mobile_main.css");
</style>
</head>
<body class="mobile">
<div>
		<form method="post" name="caseHistoryForm" id="caseHistoryForm">
		<h6  align="center">${TestResult.testCaseName }</h6>
		<display:table name="ResultTrack" class="mobile" id="row" style="width:100%;"
			cellpadding="0" cellspacing="0" >
			<display:caption><thead>
			<tr  class="pageHeader">
		  		<th style="width: 5%;" >No.</th>
				<th style="width: 5%;" ></th>
				<th style="width: 10%;">Result</th>
				<th style="width: 55%;">Performance Result</th>
				<th style="width: 25%;">Executed Time</th>
			</tr>
			</thead></display:caption>
			<display:column  title="Row Number" class="fixTitle1">
				<c:out value="${row_rowNum}"></c:out>
			</display:column>
			<display:column>
				<input type="checkbox" name="multiTestExecution" value="${row.resultTrackId }" />
			</display:column>
			<s:if test="#attr.row.resultTypeId == 3">
					<display:column style="background:green;" property="resultTypeName" />
			</s:if>
			<s:if test="#attr.row.resultTypeId == 4">
						<display:column style="background:yellow;" property="resultTypeName" />
			</s:if>
			<s:if test="#attr.row.resultTypeId == 2">
						<display:column style="background:red;" property="resultTypeName" />
			</s:if>
			<s:if test="#attr.row.resultTypeId == 1">
						<display:column style="background:none;" property="resultTypeName" />
			</s:if>
			<s:if test="#attr.row.resultTypeId == ''">
						<display:column style="background:none;" value="Not Run" />
			</s:if>
			<display:column property="performance" />
			<display:column property="modifyDate" />
			<display:setProperty name="basic.show.header" value="false"/>
		</display:table>		
		</form>
</div>
			<div style="width:100%;height:30px;border:1px gray dotted;">
				<div style="float:left;width:45%;height:25px;">
			 	&nbsp;<input type="button" class="btn" value="Top Selected Result " onclick="ChooseResult();"/> 
				</div>
			</div>

</body>
<script type="text/javascript">

	function ChooseResult() {
		var checkboxs = $('input:checkbox:checked');
		
		if(checkboxs.length <= 0)
		{
			alert("Please choose one result to show as final result!");
			return;
		}
		else if(checkboxs.length != 1)
		{
			alert("Can only select one result!");
			return;
		}

        var cboxes = document.getElementsByName('multiTestExecution');
        var len = cboxes.length;
     
        for (var i=0; i<len; i++) {
        	if(cboxes[i].checked) {
        		var id = cboxes[i].value;
        		//alert(id);
        	}
        }
        //alert(id);
        window.location = "topSelectedResult.action?multiTestExecution=" + id +"";
		//MyWindow.OpenCenterWindowScroll("topSelectedResult.action?multiTestExecution=" + id,'detail',700,900);
	}	

</script>
</html>
