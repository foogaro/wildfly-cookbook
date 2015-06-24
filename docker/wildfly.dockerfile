# Base on the Fedora 20 image
FROM fedora:21

MAINTAINER Luigi Fugaro l.fugaro@gmail.com

# Update the system
#RUN yum -y update
RUN yum -y install java-1.8.0-openjdk
RUN yum -y install tar unzip wget telnet net-tools

# Make sure the distribution is available from a well-known place
RUN cd /opt && curl http://download.jboss.org/wildfly/9.0.0.Beta2/wildfly-9.0.0.Beta2.tar.gz | tar zx

RUN yum -y install iproute

ENV IPADDR $(ip a s | sed -ne '/127.0.0.1/!{s/^[ \t]*inet[ \t]*\([0-9.]\+\)\/.*$/\1/p}')

# Add the Wildfly distribution to /opt
RUN ln -s /opt/wildfly-9.0.0.Beta2 /opt/wildfly

# Create the wildfly user and group
RUN groupadd -r service -g 11235 && useradd -u 54322 -r -g service -d /opt/wildfly -s /sbin/nologin -c "WildFly user" wildfly
RUN /opt/wildfly/bin/add-user.sh wildfly cookbook.2015 --silent
RUN chown -R wildfly:service /opt/wildfly/*
# RUN sed '/<management-interfaces>/a <native-interface security-realm="ManagementRealm"><socket interface="management" port="${jboss.management.native.port:9999}"/></native-interface>' /opt/wildfly/domain/configuration/host.xml
RUN sed '/native-interface>/a <http-interface security-realm="ManagementRealm" http-upgrade-enabled="true"><socket interface="management" port="${jboss.management.http.port:9990}"/></http-interface>' /opt/wildfly/domain/configuration/host.xml

# Expose HTTP port and Management port
EXPOSE 8080 9990

# Switch to user wildfly
USER wildfly

# Run WildFly after container boot
# Default parameters which can be overridden with "docker run ..."
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
