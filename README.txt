initial commit
access REST:
- mvn tomcat7:run-war
- call http://localhost:8080/qat-service/api/kafka/test

Note:
ALWAYS extend from SpringBeanAutowiringSupport in ANY class that uses Autowired Spring feature.
This is because we use Tomcat a runner of the App, and common spring features are not working properly in such case.