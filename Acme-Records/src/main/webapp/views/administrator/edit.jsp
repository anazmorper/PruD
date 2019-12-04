<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="administrator/edit.do" modelAttribute="administrator">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	
	
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
	
	
	<div>
	<form:label readonly="true" path="userAccount.username">
		<spring:message code="administrator.username"/>: 
	</form:label>
	<form:input minlength="5" path="userAccount.username" required="true"/>
	<form:errors  path="userAccount.username"/><br />
	</div>
	<br/>
	
	<div>
	<form:label path="userAccount.password">
		<spring:message code="administrator.password"/>: 
	</form:label>
	<form:password path="userAccount.password" required="true"/>
	<form:errors  path="userAccount.password"/><br />
	</div>

	<input type="submit" onclick="return validate('${countryCodeAttr}','<spring:message code="admin.script.message"/>')" name="save"
		value="<spring:message code="administrator.save" />" />
	<input type="button" name="cancel"
		value="<spring:message code="administrator.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do');" />
	<br />
	

</form:form>