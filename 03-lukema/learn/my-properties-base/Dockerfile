FROM tomcat:latest

ADD ./my-properties-boot/target/my-properties-boot.war /usr/local/tomcat/webapps

ADD ./my-properties-ng/dist/my-properties-ng /usr/local/tomcat/webapps/my-properties-ng

EXPOSE 8080

# ENTRYPOINT ["/usr/sbin/httpd", "-D", "FOREGROUND"]
CMD ["catalina.sh", "run"]
