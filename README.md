#start 
mvn spring-boot:run 

#Run Test Method
 mvn clean compile test -Dtest=TransactionTypeServiceTest#<method> -Ptest

#Run Test Class
 mvn clean compile test -Dtest=<ClassName> -Ptest
#Run Test All
 mvn clean compile test -Dtest -Ptest

#find Process
netstat -ano | findstr :8888

#kill Process
taskkill /PID 6852 /F
