<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.packtpub.wildflycookbook.subsystems.logging.Logging" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>WildFly Cookbook::Subsystem::Logging</title>
</head>
<body>
<%
    Logging.log();
%>
Look at the log file.
</body>
</html>