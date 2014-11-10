<html>
<body>

<h2>WildFly::Cookbook::Balancing Test</h2>

<h3>Who is serving your request? <%=System.getProperty("jboss.node.name")%></h3>

<%
    Object sessAttr = session.getAttribute("visitors");
    int visitors = sessAttr == null ? 0 : Integer.valueOf(sessAttr+"");
    System.out.println("********************************+");
	System.out.println("I'm serving your request");
    System.out.println("Visitor(s): " + visitors);
    System.out.println("********************************+");
%>

<h3>Visitor(s): <%=visitors%></h3>

<%
    session.setAttribute("visitors", ++visitors);
%>
</body>
</html>
