FROM foogaro/httpd-2.2

MAINTAINER Foogaro "l.fugaro@gmail.com"

WORKDIR /opt/httpd/modules

RUN wget http://downloads.jboss.org/mod_cluster//1.2.6.Final/linux-x86_64/mod_cluster-1.2.6.Final-linux2-x64-so.tar.gz
RUN tar zxvf mod_cluster-1.2.6.Final-linux2-x64-so.tar.gz
RUN rm mod_cluster-1.2.6.Final-linux2-x64-so.tar.gz

WORKDIR /opt/httpd/conf/extra

RUN touch httpd-mod_cluster.conf

RUN echo "LoadModule slotmem_module modules/mod_slotmem.so" >> httpd-mod_cluster.conf
RUN echo "LoadModule manager_module modules/mod_manager.so" >> httpd-mod_cluster.conf
RUN echo "LoadModule proxy_cluster_module modules/mod_proxy_cluster.so" >> httpd-mod_cluster.conf
RUN echo "LoadModule advertise_module modules/mod_advertise.so" >> httpd-mod_cluster.conf
RUN echo "" >> httpd-mod_cluster.conf
RUN echo "Listen 192.168.59.103:80" >> httpd-mod_cluster.conf
RUN echo "Listen 192.168.59.103:6666" >> httpd-mod_cluster.conf
RUN echo "" >> httpd-mod_cluster.conf
RUN echo "<VirtualHost 192.168.59.103:80>" >> httpd-mod_cluster.conf
RUN echo "    <Location /mcm>" >> httpd-mod_cluster.conf
RUN echo "        SetHandler mod_cluster-manager" >> httpd-mod_cluster.conf
RUN echo "        Order deny,allow" >> httpd-mod_cluster.conf
RUN echo "        Deny from all" >> httpd-mod_cluster.conf
RUN echo "        Allow from all" >> httpd-mod_cluster.conf
RUN echo "    </Location>" >> httpd-mod_cluster.conf
RUN echo "</VirtualHost>" >> httpd-mod_cluster.conf
RUN echo "" >> httpd-mod_cluster.conf
RUN echo "<VirtualHost 192.168.59.103:6666>" >> httpd-mod_cluster.conf
RUN echo "    <Directory />" >> httpd-mod_cluster.conf
RUN echo "        Order deny,allow" >> httpd-mod_cluster.conf
RUN echo "        Deny from all" >> httpd-mod_cluster.conf
RUN echo "        Allow from all" >> httpd-mod_cluster.conf
RUN echo "    </Directory>" >> httpd-mod_cluster.conf
RUN echo "    ServerAdvertise on http://192.168.59.103:6666" >> httpd-mod_cluster.conf
RUN echo "    EnableMCPMReceive" >> httpd-mod_cluster.conf
RUN echo "</VirtualHost>" >> httpd-mod_cluster.conf
RUN echo "" >> httpd-mod_cluster.conf

WORKDIR /opt/httpd/conf

RUN sed -e '/Listen 80/ s/^#*/#/' -i httpd.conf
RUN echo "" >> httpd.conf
RUN echo "# Mod_Cluster Settings" >> httpd.conf
RUN echo "Include conf/extra/httpd-mod_cluster.conf" >> httpd.conf

WORKDIR /opt/httpd/bin

EXPOSE 80

CMD ["/opt/httpd/bin/httpd", "-k", "start", "-f", "/opt/httpd/conf/httpd.conf", "-D", "FOREGROUND"]
