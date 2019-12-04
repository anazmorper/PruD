<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<div class="col-md-6 col-centered">
	<div class="well bs-component">
		<form:form action="${requestURI }" modelAttribute="m">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="sender" />
			<form:hidden path="recipient" />
			<form:hidden path="moment" />


			
			<jstl:if test="${language=='en'}">
				<form:label path="topic">
					<spring:message code="message.topic" />:
				</form:label>
				<form:select path="topic">
					<form:options items="${topicsEN}" />
				</form:select>
				<form:label path="priority">
					<spring:message code="message.priority" />:
				</form:label>
				<form:select path="priority">
					<form:options items="${priorityEN}" />
				</form:select>
			</jstl:if>
			
			<jstl:if test="${language=='es'}">
				<form:label path="topic">
					<spring:message code="message.topic" />:
				</form:label>
				<form:select path="topic">
					<form:options items="${topicsES}" />
				</form:select>
				<form:label path="priority">
					<spring:message code="message.priority" />:
				</form:label>
				<form:select path="priority">
					<form:options items="${priorityES}" />
				</form:select>
			</jstl:if>
			
			

			<br />
			<br />	
					
			<acme:textarea code="message.subject" path="subject" />
			<br />	
			
			<acme:textarea code="message.body" path="body" />
			<br />  
			


			<input type="submit" name="save"
				value="<spring:message code="message.send.broadcastAllActors.link" />" />
			
			<input type="button" name="cancel"
				value="<spring:message code="message.cancel.link" />"
				onclick="javascript: location.replace('welcome/index.do')" />

				

<br />  
		</form:form>
	</div>
</div>