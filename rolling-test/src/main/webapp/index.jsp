<html>
<body>

<h2>WildFly::Cookbook::Rolling Test</h2>

<h3 style="color:#ccff00;">I'm running version 1.1</h3>

<h4>Who is serving your request? <%=System.getProperty("jboss.node.name")%></h4>

<%
    Object sessAttr = session.getAttribute("visitors");
    int visitors = sessAttr == null ? 0 : Integer.valueOf(sessAttr+"");
    System.out.println("********************************+");
    System.out.println("I'm running version 1.1");
	System.out.println("I'm serving your request and I'm " + System.getProperty("jboss.node.name"));
    System.out.println("Visitor(s): " + visitors);
    System.out.println("********************************+");
%>

<h4>Visitor(s): <%=visitors%></h4>

<%
    session.setAttribute("visitors", ++visitors);
%>

</body>
</html>
