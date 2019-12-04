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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="administrator/setter/edit.do" modelAttribute="setter">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="publicationMoment" />
	<form:hidden path="ticker" />

	
	
	
	<acme:textarea code="setter.body" path="body"/>
	<br />
	<acme:textbox code="setter.picture" path="picture"/>
	<br />
	
	<form:label path="finalMode">
	<spring:message code="setter.finalMode"/>
	</form:label>
	<form:select path="finalMode">
	<form:option
	label="Yes"
	value="true">
	</form:option>
	<form:option
	label="No"
	value="false">
	</form:option>
	</form:select><br /><br />
	
	<label>${errores}</label><br /><br />
	
	<jstl:if test="${canEdit=='true'}">	
	<acme:submit name="save" code="setter.save"/>
	</jstl:if>
	
	<jstl:if test="${canEdit=='false'}">	
	<spring:message code="setter.cannotEdit" />
	</jstl:if>
	
	<acme:cancel url="administrator/setter/list.do" code="setter.cancel"/> 

</form:form>