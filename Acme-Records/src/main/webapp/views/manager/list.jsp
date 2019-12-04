<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="managers" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="actor.name" var="actorName" />
	<display:column property="name" title="${actorName}" />

	<spring:message code="actor.surname" var="actorSurname" />
	<display:column property="surname" title="${actorSurname}" />

	<spring:message code="actor.photo" var="actorPhoto" />
	<display:column property="photo" title="${actorPhoto}" />

	<spring:message code="actor.email" var="actorEmail" />
	<display:column property="email" title="${actorEmail}" />

	<spring:message code="actor.phoneNumber" var="actorPhoneNumber" />
	<display:column property="phoneNumber" title="${actorPhoneNumber}" />


</display:table>






<div>
	<a href="javascript:window.history.back()"> <spring:message
			code="return" />
	</a>
</div>

