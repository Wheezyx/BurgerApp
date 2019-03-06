# BurgerApp

Project has mainly educational purpouse (to learn how everything works underneath). There could be some improvements like use Spring Boot to simplify configuration, add Spring Data to simplify mainly CRUD db operations, remove thymeleaf and use some frontend framework (like Angular or React)

Application preivew:
-


Team:
-
- [ernimg] as Frontend Developer
- [gromajster] as Backend Developer (focused mainly on connect thymeleaf's tags with html's code)
- [Me] as Backend Developer

Technology stack:
- 
- JAVA 8
- Thymeleaf 3.0
- Hibernate 5.3.1
- Spring Framework 5.0.6
- Spring Security 5.0.6
- AJAX (will be changed for websockets)
- Log4j for logging db and http operations.
- Aspectj

HOW TO RUN
-
The project is packaging as war, so u need to download Tomcat (as application container) and run command:
```sh
mvn clean install
```
It will build war file in ./target/ directory. Next, deploy war file on Tomcat and run your server.

By defauly, application uses H2 database. You can change it easily in application.properties.

[me]: <https://github.com/Wheezyx>
[ernimg]: <https://github.com/ernimg>
[gromajster]: <https://github.com/gromajster>
