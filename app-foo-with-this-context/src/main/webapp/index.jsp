<html>
<body>

<h2>WildFly::Cookbook::Balancing same context for different applications</h2>

<h3>Who is serving your request? <%=System.getProperty("jboss.node.name")%></h3>
<%
    System.out.println("********************************+");
	System.out.println("I'm serving your request and I'm \"app-foo-with-this-context\"");
    System.out.println("********************************+");
%>

<h3>Who are you? I'm "app-foo-with-this-context"</h3>

</body>
</html>
