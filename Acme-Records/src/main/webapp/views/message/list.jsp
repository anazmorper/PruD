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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="messages" requestURI="${requestURI}" id="row">

	<!-- Botones para administrator -->
	<security:authorize access="hasRole('ADMIN')">
		
		<spring:message code="message.display" var="displayMessageHeader" />
		<display:column title="${displayMessageHeader}" sortable="true">
			<spring:url value="message/administrator/display.do" var="displayAdministratorURL">
				<spring:param name="messageId" value="${row.id}" />
			</spring:url>

			<a href="${displayAdministratorURL}"><spring:message
					code="message.display" /></a>
		</display:column>
		
		<spring:message code="message.format.date" var="pattern"></spring:message>
		<spring:message code="message.moment" var="momentHeader" />
		<display:column property="moment" title="${momentHeader}"
			format="${pattern }" />

		<spring:message code="message.subject" var="subjectHeader" />
		<display:column property="subject" title="${subjectHeader}" />
		
		<spring:message code="message.sender" var="senderHeader" />
		<display:column title="${senderHeader}" sortable="true">
		
			<jstl:out value="${row.sender.name}"></jstl:out> ---->
			<spring:url value="message/administrator/listBySender.do" var="senderMessageURL">
				<spring:param name="actorId" value="${row.sender.id}" />
			</spring:url>
			<a href="${senderMessageURL}"><spring:message
					code="message.orderBySender.link" /></a>
		</display:column>
		
		<spring:message code="message.recipient" var="recipientHeader" />
		<display:column title="${recipientHeader}" sortable="true">
			<jstl:out value="${row.recipient.name}"></jstl:out> ---->
			<spring:url value="message/administrator/listByRecipient.do" var="recipientMessageURL">
				<spring:param name="actorId" value="${row.recipient.id}" />
			</spring:url>

			<a href="${recipientMessageURL}"><spring:message
					code="message.orderByRecipient.link" /></a>
		</display:column>
		
		<spring:message code="message.topic" var="topicHeader" />
		<display:column title="${topicHeader}" sortable="true">
			<jstl:out value="${row.topic}"></jstl:out> ---->
			<spring:url value="message/administrator/listByTopic.do" var="topicMessageURL">
				<spring:param name="topic" value="${row.topic}" />
			</spring:url>

			<a href="${topicMessageURL}"><spring:message
					code="message.orderByTopic.link" /></a>
					
			<br>
		
		</display:column>
		
		<spring:message code="message.priority" var="priorityHeader" />
		<display:column title="${priorityHeader}" sortable="true">
			<jstl:out value="${row.priority}"></jstl:out> ---->
			<spring:url value="message/administrator/listByPriority.do" var="priorityMessageURL">
				<spring:param name="priority" value="${row.priority}" />
			</spring:url>

			<a href="${priorityMessageURL}"><spring:message
					code="message.orderByPriority.link" /></a>
					
			<br>
		
		</display:column>

		<%-- <spring:message code="message.delete.link" var="deleteMessageHeader" />
		<display:column title="${deleteMessageHeader}" sortable="true">
			<a href="message/administrator/delete.do?messageId=${row.id}"><spring:message
					code="message.delete.link" /></a>
		</display:column> --%>

	</security:authorize>


	<!-- Botones para customer -->
	<security:authorize access="hasRole('CUSTOMER')">
	
		<spring:message code="message.display" var="displayMessageHeader" />
		<display:column title="${displayMessageHeader}" sortable="true">
			<spring:url value="message/customer/display.do" var="displayCustomerURL">
				<spring:param name="messageId" value="${row.id}" />
			</spring:url>

			<a href="${displayCustomerURL}"><spring:message
					code="message.display" /></a>
		</display:column>
		
		<spring:message code="message.format.date" var="pattern"></spring:message>
		<spring:message code="message.moment" var="momentHeader" />
		<display:column property="moment" title="${momentHeader}"
			format="${pattern }" />

		<spring:message code="message.subject" var="subjectHeader" />
		<display:column property="subject" title="${subjectHeader}" />
		
		<spring:message code="message.sender" var="senderHeader" />
		<display:column title="${senderHeader}" sortable="true">
		
			<jstl:out value="${row.sender.name}"></jstl:out> ---->
			<spring:url value="message/customer/listBySender.do" var="senderMessageURL">
				<spring:param name="actorId" value="${row.sender.id}" />
			</spring:url>
			<a href="${senderMessageURL}"><spring:message
					code="message.orderBySender.link" /></a>
		</display:column>
		
		<spring:message code="message.recipient" var="recipientHeader" />
		<display:column title="${recipientHeader}" sortable="true">
			<jstl:out value="${row.recipient.name}"></jstl:out> ---->
			<spring:url value="message/customer/listByRecipient.do" var="recipientMessageURL">
				<spring:param name="actorId" value="${row.recipient.id}" />
			</spring:url>

			<a href="${recipientMessageURL}"><spring:message
					code="message.orderByRecipient.link" /></a>
		</display:column>
		
		<spring:message code="message.topic" var="topicHeader" />
		<display:column title="${topicHeader}" sortable="true">
			<jstl:out value="${row.topic}"></jstl:out> ---->
			<spring:url value="message/customer/listByTopic.do" var="topicMessageURL">
				<spring:param name="topic" value="${row.topic}" />
			</spring:url>

			<a href="${topicMessageURL}"><spring:message
					code="message.orderByTopic.link" /></a>
					
			<br>
		
		</display:column>
		
		<spring:message code="message.priority" var="priorityHeader" />
		<display:column title="${priorityHeader}" sortable="true">
			<jstl:out value="${row.priority}"></jstl:out> ---->
			<spring:url value="message/customer/listByPriority.do" var="priorityMessageURL">
				<spring:param name="priority" value="${row.priority}" />
			</spring:url>

			<a href="${priorityMessageURL}"><spring:message
					code="message.orderByPriority.link" /></a>
					
			<br>
		
		</display:column>

		<%-- <spring:message code="message.delete.link" var="deleteMessageHeader" />
		<display:column title="${deleteMessageHeader}" sortable="true">
			<a href="message/customer/delete.do?messageId=${row.id}"><spring:message
					code="message.delete.link" /></a>
		</display:column> --%>

	</security:authorize>


	<!-- Botones para manager -->
	<security:authorize access="hasRole('MANAGER')">
		
	<spring:message code="message.display" var="displayMessageHeader" />
		<display:column title="${displayMessageHeader}" sortable="true">
			<spring:url value="message/manager/display.do" var="displayManagerURL">
				<spring:param name="messageId" value="${row.id}" />
			</spring:url>

			<a href="${displayManagerURL}"><spring:message
					code="message.display" /></a>
		</display:column>
		
		<spring:message code="message.format.date" var="pattern"></spring:message>
		<spring:message code="message.moment" var="momentHeader" />
		<display:column property="moment" title="${momentHeader}"
			format="${pattern }" />

		<spring:message code="message.subject" var="subjectHeader" />
		<display:column property="subject" title="${subjectHeader}" />
		
		<spring:message code="message.sender" var="senderHeader" />
		<display:column title="${senderHeader}" sortable="true">
		
			<jstl:out value="${row.sender.name}"></jstl:out> ---->
			<spring:url value="message/manager/listBySender.do" var="senderMessageURL">
				<spring:param name="actorId" value="${row.sender.id}" />
			</spring:url>
			<a href="${senderMessageURL}"><spring:message
					code="message.orderBySender.link" /></a>
		</display:column>
		
		<spring:message code="message.recipient" var="recipientHeader" />
		<display:column title="${recipientHeader}" sortable="true">
			<jstl:out value="${row.recipient.name}"></jstl:out> ---->
			<spring:url value="message/manager/listByRecipient.do" var="recipientMessageURL">
				<spring:param name="actorId" value="${row.recipient.id}" />
			</spring:url>

			<a href="${recipientMessageURL}"><spring:message
					code="message.orderByRecipient.link" /></a>
		</display:column>
		
		<spring:message code="message.topic" var="topicHeader" />
		<display:column title="${topicHeader}" sortable="true">
			<jstl:out value="${row.topic}"></jstl:out> ---->
			<spring:url value="message/manager/listByTopic.do" var="topicMessageURL">
				<spring:param name="topic" value="${row.topic}" />
			</spring:url>

			<a href="${topicMessageURL}"><spring:message
					code="message.orderByTopic.link" /></a>
					
			<br>
		
		</display:column>
		
		<spring:message code="message.priority" var="priorityHeader" />
		<display:column title="${priorityHeader}" sortable="true">
			<jstl:out value="${row.priority}"></jstl:out> ---->
			<spring:url value="message/manager/listByPriority.do" var="priorityMessageURL">
				<spring:param name="priority" value="${row.priority}" />
			</spring:url>

			<a href="${priorityMessageURL}"><spring:message
					code="message.orderByPriority.link" /></a>
					
			<br>
		
		</display:column>

		<%-- <spring:message code="message.delete.link" var="deleteMessageHeader" />
		<display:column title="${deleteMessageHeader}" sortable="true">
			<a href="message/manager/delete.do?messageId=${row.id}"><spring:message
					code="message.delete.link" /></a>
		</display:column> --%>

	</security:authorize>


	<!-- Attributes -->


</display:table>

