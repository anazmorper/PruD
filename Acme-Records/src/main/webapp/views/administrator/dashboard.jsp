<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<p>
	<b><spring:message code="administrator.avgMinMaxStRecordByWorkProgramme" /> :</b> 
	<jstl:out value="${avgMinMaxStRecordByWorkProgramme[0]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStRecordByWorkProgramme[1]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStRecordByWorkProgramme[2]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStRecordByWorkProgramme[3]}" /><jstl:out value="" />
</p>

<p>
	<b><spring:message code="administrator.avgMinMaxStRecordPrice" /> :</b> 
	<jstl:out value="${avgMinMaxStRecordPrice[0]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStRecordPrice[1]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStRecordPrice[2]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStRecordPrice[3]}" /><jstl:out value="" />
</p>

<p>
	<b><spring:message code="administrator.avgMinMaxStWorkProgrammeByManager" /> :</b> 
	<jstl:out value="${avgMinMaxStWorkProgrammeByManager[0]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStWorkProgrammeByManager[1]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStWorkProgrammeByManager[2]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStWorkProgrammeByManager[3]}" /><jstl:out value="" />
</p>

<p>
	<b><spring:message code="administrator.avgMinMaxStPurchasesByCustomer" /> :</b> 
	<jstl:out value="${avgMinMaxStPurchasesByCustomer[0]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStPurchasesByCustomer[1]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStPurchasesByCustomer[2]}" /><jstl:out value=", " />
	<jstl:out value="${avgMinMaxStPurchasesByCustomer[3]}" /><jstl:out value="" />
</p>

<p>
	<b><spring:message code="administrator.findTop3RecordsByPurchase" /> :</b> 
	<jstl:out value="${findTop3RecordsByPurchase[0].title}" /><jstl:out value=", " />
	<jstl:out value="${findTop3RecordsByPurchase[1].title}" /><jstl:out value=", " />
	<jstl:out value="${findTop3RecordsByPurchase[2].title}" /><jstl:out value="" />
</p>

<p>
	<b><spring:message code="administrator.findTop3RecordsByMoney" /> :</b> 
	<jstl:out value="${findTop3RecordsByMoney[0].title}" /><jstl:out value=", " />
	<jstl:out value="${findTop3RecordsByMoney[1].title}" /><jstl:out value=", " />
	<jstl:out value="${findTop3RecordsByMoney[2].title}" /><jstl:out value="" />
</p>

<p>
	<b><spring:message code="administrator.findTopRecord" /> :</b> 
	<jstl:out value="${findTopRecord.title}" />
</p>

<p>
	<b><spring:message code="administrator.findTopManager" /> :</b> 
	<jstl:out value="${findTopManager.name}" />
</p>


