<html>
<body>
<h2>WildFly::Cookbook::Cluster Test</h2>

<%
    Object sessAttr = session.getAttribute("visitors");
    int visitors = sessAttr == null ? 0 : Integer.valueOf(sessAttr+"");
    System.out.println("********************************+");
    System.out.println("Visitor(s): " + visitors);
    System.out.println("********************************+");
%>

<h2>Visitor(s): <%=visitors%></h2>

<%
    session.setAttribute("visitors", ++visitors);
%>

</body>
</html>
