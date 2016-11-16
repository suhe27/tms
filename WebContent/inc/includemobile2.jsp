<% session.setAttribute("app",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/page-tags" prefix="p"%>
<script src="${app}/scripts/jquery-1.7.1.js"></script>
<script src="${app}/js/jquery/js/jquery-ui-1.7.1.custom.min.js"></script>
<script src="${app}/js/jquery/js/jquery.bgiframe.min.js"></script>
<script src="${app}/js/common.js"></script>
<script src="${app}/js/ajax2.js"></script>
<style type="text/css">
@import url("${app}/css/mobile_main.css");
@import url("${app}/css/screen.css");
@import url("${app}/js/jquery/css/smoothness/jquery-ui-1.7.1.custom.css");
@import url("${app}/css/page.css");
</style>