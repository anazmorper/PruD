<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="administrator.broadcast" />
</p>

<form action="administrator/broadcast2.do" >

	<p>
		<input name="subject" type="text" placeholder="<spring:message code="message.subject" />" required >
	</p>
	<p>
		<input name="body" type="text" placeholder="<spring:message code="message.body" />" required >
	</p>
	<p>
		<input type="submit" value="<spring:message code="message.broadcast" />">
	</p>

</form>
