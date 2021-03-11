<html>
<title>Some application title</title>
<body>

<h2>WildFly::Cookbook::Balancing same context for different applications</h2>

<h3>Who is serving your request? <%=System.getProperty("jboss.node.name")%></h3>
<%
    System.out.println("********************************+");
	System.out.println("I'm serving your request and I'm \"app-bar-with-this-context\"");
    System.out.println("********************************+");
%>

<h3>Who are you? I'm "app-bar-with-this-context"</h3>


<h1>version 1.1_t2</h1>
</body>
</html>
