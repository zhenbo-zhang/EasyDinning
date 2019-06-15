#### EasyEating - A full-stack web application using MVC architecture for the seller side of takeout websites

- Created RESTful API on server side with Spring Boot and applied SLF4J and Logback to record system logs 

- Applied MySQL database to store products and orders information, using Spring Data JPA for efficient database looking-up and manipulation, supplemented with Redis as Cache layer to improve server performance

- Built the front end with Bootstrap, FreeMarker and JQuery, using WebSocket to push notifications 

- Implemented distributed lock with Redis, enabling server to handle 300 request per second tested by Apache ab



##### How to start the App?

1. Open the project using Intellij IDEA
2. Click "run" button
3. Backend URL: http://127.0.0.1:8080/sell/seller/order/list
4. Frontend URL: 192.168.0.113/#/order (sell.com/#/)



##### Related information:

- If you want to see the SQL for creating tables in MySQL database, please refer to sell.sql 
- For clients to placing orders, API are listed in API.md