
mvn -Dtest=LoggingBridgeTest test

mvn -Dtest=LoggingBridgeCliArgTest test

mvn -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager -Dtest=LoggingBridgeCliArgTest test


# The system will look for this config file, first using
# a System property specified at startup:
#
# >java -Djava.util.logging.config.file=myLoggingConfigFilePath
#
# If this property is not specified, then the config file is
# retrieved from its default location at:
#
# JDK_HOME/jre/lib/logging.properties 

