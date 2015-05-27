FROM fedora:21

MAINTAINER Foogaro "l.fugaro@gmail.com"

RUN yum -y install tar net-tools wget telnet mlocate unzip
RUN yum -y update
RUN yum -y install yum-fastestmirror
RUN yum -y install gcc
RUN yum -y install openssl openssl-devel


WORKDIR /opt

RUN wget http://www.us.apache.org/dist/httpd/httpd-2.2.29.tar.gz
RUN tar xvfz httpd-2.2.29.tar.gz

WORKDIR /opt/httpd-2.2.29

RUN ./configure --prefix=/opt/httpd --with-mpm=worker --enable-mods-shared=most --enable-maintainer-mode --with-expat=builtin --enable-ssl --enable-proxy --enable-proxy-http --enable-proxy-ajp --disable-proxy-balancer --with-included-apr
RUN make
RUN make install

WORKDIR /opt/httpd/bin

EXPOSE 80

CMD ["/opt/httpd/bin/httpd", "-k", "start", "-f", "/opt/httpd/conf/httpd.conf", "-D", "FOREGROUND"]
