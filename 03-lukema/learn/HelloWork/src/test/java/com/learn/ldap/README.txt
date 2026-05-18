

Solution to cert problem:

	1. Download cert of a remote server:
	
	   echo | openssl s_client -connect eagandcs.devsub.dev.dce.usps.gov:636 2>/dev/null | openssl x509 > cert.pem
	
	2. Copy cert.pem to java_home/jre/lib/security/ldap-cert-usps/cert.pem
	   
	3. Import cert to java_home/jre/lib/security:
	
	   cd java_home/jre/lib/security
	      keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias luke -file ldaps-cert-usps/cert.pem
	 
	4. Do step 3 for all of the three:
	
		C:\D\01-LukeTools\jdk1.8.0_144
		C:\Program Files\IBM\SDP\jdk
		C:\Program Files (x86)\IBM\WebSphere\AppServer\java
	   
	5. Display all certs:
	
	   keytool -list -keystore cacerts
	   keytool -list -keystore cacerts -alias luke

Problem to Solve:

	Caused by: sun.security.validator.ValidatorException: PKIX path building failed: 
   		sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target


Cause:
Whenever Java attempts to connect to another application over SSL (e.g.: HTTPS, IMAPS, LDAPS), it will only be 
able to connect to that application if it can trust it. The way trust is handled in the Java world is that you have 
a keystore (typically $JAVA_HOME/lib/security/cacerts), also known as the truststore. This contains a list of all known 
Certificate Authority (CA) certificates, and Java will only trust certificates that are signed by one of those CAs or 
public certificates that exist within that keystore. For example, if we look at the certificate for Atlassian, we can see 
that the *.atlassian.com certificate has been signed by the intermediate certificates, DigiCert High Assurance EV Root CA 
and DigiCert High Assurance CA-3. These intermediate certificates have been signed by the root Entrust.net 
Secure Server CA.

These three certificates combined are referred to as the certificate chain, and, as they are all within the Java 
keystore (cacerts), Java will trust any certificates signed by them (in this case, *.atlassian.com). Alternatively, 
if the *.atlassian.com certificate had been in the keystore, Java would also trust that site.
This problem is therefore caused by a certificate that is self-signed (a CA did not sign it) or a certificate chain 
that does not exist within the Java truststore. Java does not trust the certificate and fails to connect to the application.


Resolution:
Add SSL Certificates automatically!
We have SSL for JIRA and SSL for Confluence add-ons available for this process. Please install and use these 
add-ons for a simpler way to import the certificate.

	(warning) The SSL for Confluence add-on is only available for versions up to Confluence 5.7.4.
	
	(info) SSL for JIRA and SSL for Confluence add-ons are not supported by Atlassian.
	
	(info) These plugins are part of Atlassian Labs and may not be compatible with the latest versions of JIRA and Confluence

   1. Make sure you have imported the public certificate of the target instance into the truststore according to the Connecting 
      to SSL Services instructions.

   2. Make sure any certificates have been imported into the correct truststore; you may have multiple JRE/JDKs. See Installing 
      Java for this.
      
   3. Check to see that the correct truststore is in use. If -Djavax.net.ssl.trustStore has been configured, it will override the 
      location of the default truststore, which will need to be checked.
      
   4. Check if your Anti Virus tool has "SSL Scanning" blocking SSL/TLS. If it does, disable this feature or set exceptions for 
      the target addresses (check the product documentation to see if this is possible).
      
   5. If connecting to a mail server, such as Exchange, ensure authentication allows plain text.
   
   6. Verify that the target server is configured to serve SSL correctly. This can be done with the SSL Server Test tool.


Show cert of a remote server:
	openssl.exe s_client -showcerts -connect eagandcs.devsub.dev.dce.usps.gov:636 < /dev/null
	openssl.exe s_client -showcerts -connect eagandcs.devsub.dev.dce.usps.gov:636 < /dev/null 2> /dev/null | openssl x509 -text
	
	echo | openssl s_client -connect eagandcs.devsub.dev.dce.usps.gov:636 2>/dev/null | openssl x509 > cert.pem
	echo | openssl s_client -connect eagandcs.devsub.dev.dce.usps.gov:636 2>/dev/null | openssl x509 -outform DER > derp.der
	
	If the remote server is using SNI (that is, sharing multiple SSL hosts on a single IP address) you will also need to 
	send the correct hostname in order to get the right certificate:
	
	openssl s_client -showcerts -servername www.example.com -connect www.example.com:443 < /dev/null
   
 
 
Importing .cer certificate file downloaded from browser (open the url and dig for details) into cacerts keystore in 
java_home\jre\lib\security worked for me, as opposed to attemps to generate and use my own keystore.

Go to your java_home\jre\lib\security
(Windows) Open admin command line there using cmd and CTRL+SHIFT+ENTER
Run keytool to import certificate:
.

keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias luke -file cert.pem

 ..\..\bin\keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias yourAliasName -file path\to\certificate.cer
This way you don't have to specify any additional JVM options and the certificate should be recognized by the JRE.   
    
   