<% session.setAttribute("app",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/page-tags" prefix="p"%>

<style type="text/css">
@import url("${app}/css/mobile_main.css");
@import url("${app}/css/screen.css");
@import url("${app}/js/jquery/css/smoothness/jquery-ui-1.7.1.custom.css");
@import url("${app}/css/page.css");
</style>
		<style type="text/css">
			.expanded-group{
				background: url("${app}/DataTables-1.10.4/media/images/minus.jpg") no-repeat scroll left center transparent;
				padding-left: 15px !important
			}

			.collapsed-group{
				background: url("${app}/DataTables-1.10.4/media/images/plus.jpg") no-repeat scroll left center transparent;
				padding-left: 15px !important
			}

		</style>
