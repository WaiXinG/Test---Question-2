Pre-requisites:

MySQL 5.7
JDK 1.8

1. Start you mysql server.
2. - If you are using command prompt, execute "klse_stock_db.sql" in setup folder.
	Tips: use command "source {file path}/klse_stock_db.sql"
   - If not, import "klse_stock_db.sql" in the folder mentioned above.
3. Open application.properties in main/resources, change in order to connect with database:
	- spring.datasource.url 
	- spring.datasource.username
	- spring.datasource.password
4. Open command prompt, cd to the project folder.
5. enter "mvn clean install" to download all the dependencies.
6. Enter "mvn spring-boot:run" to run the program.