<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="adminconfig/administrator/edit.do" modelAttribute = "adminConfig">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="maxNumberFinder"/>
	<form:hidden path="status"/>
	<form:hidden path="types"/>
	
	
	<form:label path="defaultCountryCode">
		<spring:message code="adminconfig.defaultCountryCode"/>
	</form:label>
	<form:input path="defaultCountryCode" placeholder="+CCC"/>
	<form:errors cssClass="error" path="defaultCountryCode" />
	<br />
	
	<form:label path="creditCardNames">
		<spring:message code="adminconfig.creditCardNames"/>
	</form:label>
	<form:input path="creditCardNames" />
	<form:errors cssClass="error" path="creditCardNames" />
	<br />
	
	<form:label path="banner">
		<spring:message code="adminconfig.banner"/>
	</form:label>
	<form:input path="banner"/>
	<form:errors cssClass="error" path="banner" />
	<br />
	
	<form:label path="welcomeMessageEnglish">
		<spring:message code="adminconfig.welcomeMessageEnglish"/>
	</form:label>
	<form:input path="welcomeMessageEnglish"/>
	<form:errors cssClass="error" path="welcomeMessageEnglish" />
	<br />
	
	<form:label path="welcomeMessageSpanish">
		<spring:message code="adminconfig.welcomeMessageSpanish"/>
	</form:label>
	<form:input path="welcomeMessageSpanish"/>
	<form:errors cssClass="error" path="welcomeMessageSpanish" />
	<br />
	
	<form:label path="defaultTopicsSpanish">
		<spring:message code="adminconfig.defaultTopicsSpanish"/>
	</form:label>
	<form:input path="defaultTopicsSpanish"/>
	<form:errors cssClass="error" path="defaultTopicsSpanish" />
	<br />
	
	<form:label path="defaultTopicsEnglish">
		<spring:message code="adminconfig.defaultTopicsEnglish"/>
	</form:label>
	<form:input path="defaultTopicsEnglish"/>
	<form:errors cssClass="error" path="defaultTopicsEnglish" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="adminconfig.save" />" />
	
	<input type="button" name="cancel"
		value="<spring:message code="adminconfig.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do')" />
	<br />
</form:form>