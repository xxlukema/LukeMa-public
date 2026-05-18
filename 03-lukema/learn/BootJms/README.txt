
1. Run server

	spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=local"

1. Run ActiveMQ Message Broker:

	mvn activemq:run
	or
	mvn org.apache.activemq.tooling:maven-activemq-plugin:5.7.0:run

	mvn jetty:run

2. Monitoring the broker

	http://localhost:8161/admin
	Login: admin
	Passwort: admin or secret
	
3. Failover

	failover:tcp://host:port
	
	
	
	
	