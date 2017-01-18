<%@ page language = "java" %>
<%@ page import = "comv2.aunwesha.param.*, learnityInit.Host, java.io.File" %>
<%-- <%@ page session = "true" %> --%>
<% /* Author : Subir Bhaumik */ %>
<%
String course_id = (String)session.getAttribute("unit_id");
if(course_id == null) {
	response.sendRedirect("./sessionout.jsp");
}
String indexLocation = Host.getServerDocumentPath() + File.separatorChar + course_id + File.separatorChar + "index";
String serverDocFolderURL = Host.getServerDocumentPathEngine();
String appTitle = "LMS Content Search";
String appfooter = "Powered By LMS";
%>
