<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
	<body>
		
		<h1>Delete Text</h1>
		<sf:form commandName="text" method="POST">
			Name: <sf:input path="name"  disabled="true"/><sf:errors path="name"/><br/>
			<input type="submit" value="OK" />
		</sf:form>

	</body>
</html>