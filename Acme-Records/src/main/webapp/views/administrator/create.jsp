<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="administrator/create.do" modelAttribute="administrator">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	<form:hidden path="userAccount.authorities" value="ADMIN" />
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.version" />
	
	<jstl:if test=""></jstl:if>
	
	
	<div>
		<form:label path="name">
			<spring:message code="administrator.name" />:
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
	</div>
	<br />

	<div>
		<form:label path="surname">
			<spring:message code="administrator.surname" />:
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
	</div>
	<br />
	
	
	<div>
		<form:label path="photo">
			<spring:message code="administrator.photo" />:
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
	</div>
	<br />

	<%-- <div>
		<form:label path="password">
			<spring:message code="administrator.password" />:
		</form:label>
		<form:input path="password" />
		<form:errors cssClass="error" path="password" />
	</div>
	<br />
	
	<div>
		<form:label path="repeatPassword">
			<spring:message code="administrator.repeatPassword" />:
		</form:label>
		<form:input path="repeatPassword" />
		<form:errors cssClass="error" path="repeatPassword" />
	</div> 
	<br />--%>
	
	
	
	<div>
		<form:label path="phoneNumber">
			<spring:message code="administrator.phoneNumber" />:
		</form:label>
		<form:input path="phoneNumber" />
		<form:errors cssClass="error" path="phoneNumber" />
	</div>
	<br />
	
	<div>
		<form:label path="email">
			<spring:message code="administrator.email" />:
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
	</div>
	<br />
	

	
	<div>
		<form:label path="address">
			<spring:message code="administrator.address" />:
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
	</div>
	<br />

	<input type="submit" name="save" onclick="return validate
	('${countryCodeAttr}','<spring:message code="admin.script.message"/>')"
		value="<spring:message code="administrator.save" />" />
	<input type="button" name="cancel"
		value="<spring:message code="administrator.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do');" />
	<br />

</form:form>