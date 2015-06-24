
The following steps show how HornetQ cluster base on message replication as showed in https://github.com/kylinsoong/kylinsoong.github.io/blob/master/img/wildfly/hornetq-cluster.png

### Install

~~~
$ unzip wildfly-9.0.0.CR2.zip
~~~

### Create User

~~~
$ ./bin/add-user.sh admin password1!

$ ./bin/add-user.sh

What type of user do you wish to add? 
 a) Management User (mgmt-users.properties) 
 b) Application User (application-users.properties)
(a): b

Enter the details of the new user to add.
Using realm 'ApplicationRealm' as discovered from the existing property files.
Username : democlient
Password recommendations are listed below. To modify these restrictions edit the add-user.properties configuration file.
 - The password should be different from the username
 - The password should not be one of the following restricted values {root, admin, administrator}
 - The password should contain at least 8 characters, 1 alphabetic character(s), 1 digit(s), 1 non-alphanumeric symbol(s)
Password : 
Re-enter Password : 
What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]: guest
About to add user 'democlient' for realm 'ApplicationRealm'
Is this correct yes/no? yes
Added user 'democlient' to file '/home/kylin/server/wildfly-9.0.0.CR2/node1/configuration/application-users.properties'
Added user 'democlient' with groups guest to file '/home/kylin/server/wildfly-9.0.0.CR2/node1/configuration/application-roles.properties'
Is this new user going to be used for one AS process to connect to another AS process? 
e.g. for a slave host controller connecting to the master or for a Remoting connection for server to server EJB calls.
yes/no? yes
To represent the user add the following to the server-identities definition <secret value="cGFzc3dvcmQxIQ==" />
~~~

### create 2 cluster nodes

~~~
$ cp -a standalone/ node1
$ cp -a standalone/ node2
~~~

### Cluster setup

~~~
$ mvn clean install

$ cp standalone-full-ha-1.xml ~/server/wildfly-9.0.0.CR2/node1/configuration/
$ cp standalone-full-ha-2.xml ~/server/wildfly-9.0.0.CR2/node2/configuration/

$ cp queue-jms.xml ~/server/wildfly-9.0.0.CR2/node1/deployments/
$ cp queue-jms.xml ~/server/wildfly-9.0.0.CR2/node2/deployments/

$ cp target/cluster-demo-jms.jar ~/server/wildfly-9.0.0.CR2/node1/deployments/
$ cp target/cluster-demo-jms.jar ~/server/wildfly-9.0.0.CR2/node2/deployments/
~~~

### Start WildFly

~~~
$ ./bin/standalone.sh -c standalone-full-ha-1.xml -Djboss.server.base.dir=node1
$ ./bin/standalone.sh -c standalone-full-ha-2.xml -Djboss.server.base.dir=node2 -Djboss.socket.binding.port-offset=100
~~~

### Balance Test

Run JMSClient send 10 messages to node1, check the console output.

The node1 recieve message 1, 3, 5, 7, 9

~~~
12:06:52,305 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-1050658442)) 1: WildFly 9 HornetQ Messaging High Available
12:07:02,323 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-1050658442)) 3: WildFly 9 HornetQ Messaging High Available
12:07:12,334 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-1050658442)) 5: WildFly 9 HornetQ Messaging High Available
12:07:22,344 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-1050658442)) 7: WildFly 9 HornetQ Messaging High Available
12:07:32,355 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-1050658442)) 9: WildFly 9 HornetQ Messaging High Available
~~~

The node2 recieve message 2, 4, 6, 8, 10

~~~
12:06:52,350 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-589728564)) 2: WildFly 9 HornetQ Messaging High Available
12:07:02,358 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-589728564)) 4: WildFly 9 HornetQ Messaging High Available
12:07:12,363 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-589728564)) 6: WildFly 9 HornetQ Messaging High Available
12:07:22,370 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-589728564)) 8: WildFly 9 HornetQ Messaging High Available
12:07:32,379 INFO  [stdout] (Thread-7 (HornetQ-client-global-threads-589728564)) 10: WildFly 9 HornetQ Messaging High Available
~~~

### Failover test

Run JMSClient send 10 messages to node1, while node1 recieve message 3, then force shut down node1, check the console output.

The node1 recieve message 1, 3

~~~
12:15:44,592 INFO  [stdout] (Thread-13 (HornetQ-client-global-threads-1050658442)) 1: WildFly 9 HornetQ Messaging High Available
12:15:54,602 INFO  [stdout] (Thread-13 (HornetQ-client-global-threads-1050658442)) 3: WildFly 9 HornetQ Messaging High Available
~~~

The node2 recieve message 2, 4, 5, 6, 7, 8, 9, 10

~~~
12:15:44,599 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 2: WildFly 9 HornetQ Messaging High Available
12:15:54,606 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 4: WildFly 9 HornetQ Messaging High Available
12:16:04,613 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 6: WildFly 9 HornetQ Messaging High Available
12:16:14,622 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 8: WildFly 9 HornetQ Messaging High Available
12:16:24,629 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 10: WildFly 9 HornetQ Messaging High Available
12:16:34,642 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 5: WildFly 9 HornetQ Messaging High Available
12:16:44,653 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 7: WildFly 9 HornetQ Messaging High Available
12:16:54,663 INFO  [stdout] (Thread-12 (HornetQ-client-global-threads-589728564)) 9: WildFly 9 HornetQ Messaging High Available
~~~

### TODO--

* Change the Application User to match the style of book(current is democlient/password1! which it be hard coded in JMSClient)
* Convert the XML file to CLI
* Some exist issue


