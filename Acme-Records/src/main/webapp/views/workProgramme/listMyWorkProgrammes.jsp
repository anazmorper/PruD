
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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Listing grid -->


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="workProgrammes" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<c:set var="today" value="<%=new java.util.Date()%>"/>
	
	<spring:message code="workProgramme.codeName" var="codeNameHeader" />
	<display:column title="${codeNameHeader}" sortable="false">
		<a href="workProgramme/manager/display.do?workProgrammeId=${row.id}"><jstl:out
				value="${row.codeName}" /></a>
	</display:column>
	
	<spring:message code="workProgramme.format.date" var="pattern"></spring:message>
	<spring:message code="workProgramme.startDate" var="startDate" />
		<display:column property="startDate" title="${startDate}"
			format="${pattern }" />
	
	<spring:message code="workProgramme.format.date" var="pattern"></spring:message>
	<spring:message code="workProgramme.endDate" var="endDate" />
		<display:column property="endDate" title="${endDate}"
			format="${pattern }" />
	
	<spring:message code="workProgramme.activities" var="activityHeader"/>
	<display:column title="${activityHeader}">
		<a href="workProgramme/activity/manager/listbyworkprogramme.do?workProgrammeId=${row.id}">
		<spring:message code="workProgramme.activities" />
		</a>
	</display:column> 
	
	<spring:message code="workProgramme.records" var="recordHeader"/>
	<display:column title="${recordHeader}">
		<a href="workProgramme/record/manager/listbyworkprogramme.do?workProgrammeId=${row.id}">
		<spring:message code="workProgramme.records" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${row.endDate lt today}">
			<display:column>
			</display:column>
		</jstl:if>
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${row.endDate ge today}">
			<display:column>
				<a href="workProgramme/manager/edit.do?workProgrammeId=${row.id}"><spring:message code="workProgramme.edit"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

<!-- Action links -->

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="workProgramme/manager/create.do"> <spring:message
				code="workProgramme.create" />
		</a>
	</div>
</security:authorize>
	<div>
  	<a href="javascript:window.history.back()"> <spring:message
    code="workProgramme.return" />
  </a>
 </div>