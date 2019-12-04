
<%--
 * list.jsp
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="activities" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<c:set var="today" value="<%=new java.util.Date()%>"/>
	
	<spring:message code="activity.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="false">
		<a href="workProgramme/activity/manager/display.do?activityId=${row.id}"><jstl:out
				value="${row.title}" /></a>
	</display:column>
	
	<acme:column code="activity.description" property="description"/>
	<spring:message code="activity.format.date" var="pattern"></spring:message>
	<spring:message code="activity.startDate" var="startDate" />
		<display:column property="startDate" title="${startDate}"
			format="${pattern }" />
	
	<spring:message code="activity.format.date" var="pattern"></spring:message>
	<spring:message code="activity.endDate" var="endDate" />
		<display:column property="endDate" title="${endDate}"
			format="${pattern }" />
	<acme:column code="activity.status" property="status" />
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${(not fn:contains(mylist, row)) || (row.endDate lt today)}">
			<display:column>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${(fn:contains(mylist, row)) && (row.endDate ge today)}">
			<display:column>
				<a href="workProgramme/activity/manager/edit.do?activityId=${row.id}"><spring:message code="activity.edit"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

<!-- Action links -->

<security:authorize access="hasRole('MANAGER')">
	<div>
		<jstl:if test="${thiswk.endDate ge today}">
		<a href="workProgramme/activity/manager/create.do"> <spring:message
				code="activity.create" />
		</a>
		</jstl:if>
	</div>
</security:authorize>
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="activity.return" />
  </a>
 </div>