<%--
 * footer.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jsp:useBean id="date" class="java.util.Date" />

<hr />


<security:authorize access="hasRole('ADMIN')">
	<hr />
	<a href="breache/create.do"><spring:message
			code="master.page.breache.admin" /></a>
	<br />
</security:authorize>

<hr />
<a href="breache/list.do"><spring:message code="master.page.breache" /></a>
<br />
<a href="termsAndConditions/show.do"><spring:message code="master.page.terms" /></a> <br>
<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme-Records Co., Inc.</b>