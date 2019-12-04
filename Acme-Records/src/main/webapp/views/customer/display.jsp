<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- Attributes -->

	<p>
		<b><spring:message code="actor.name" /></b> :
		<jstl:out value="${actor.name}" />
	</p>
	
	
	<p>
		<b><spring:message code="actor.surname" /></b> :
		<jstl:out value="${actor.surname}" />
	</p>
	
	<p>
		<b><spring:message code="actor.photo" /></b> :
		<jstl:out value="${actor.photo}" />
	</p>
	
	<p>
		<b><spring:message code="actor.email" /></b> :
		<jstl:out value="${actor.email}" />
	</p>
	
	<p>
		<b><spring:message code="actor.phoneNumber" /></b> :
		<jstl:out value="${actor.phoneNumber}" />
	</p>

	<br />
					
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="customer.return" />
  </a>
 </div>