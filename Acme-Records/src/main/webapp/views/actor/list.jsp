<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table id="actor" name="actors" requestURI="${requestURI}"
	pagesize="8">
	
	<spring:message code="actor.name" var="actorName" />
	<display:column property="name" title="${actorName}" />
	
	<spring:message code="actor.surname" var="actorSurname" />
	<display:column property="surname" title="${actorSurname}" />
	
	<jstl:if test="${actor.userAccount.enabled == true}">
	<display:column title="${banActor}">
			<a href="administrator/ban.do?actorId=${actor.id}"> <spring:message
					code="actor.ban" /></a>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${actor.userAccount.enabled == false">
	<display:column title="${unbanActor}">
			<a href="administrator/unban.do?actorId=${actor.id}"> <spring:message
					code="actor.unban" /></a>
		</display:column>
	</jstl:if>

</display:table>

	