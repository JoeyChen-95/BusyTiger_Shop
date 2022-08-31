# BusyTiger Shop

An online marketplace allowing users trade their second-hand items.e

Users can access the application by signing up their accounts and login. They can view all selling items and create order. Furthermore, users can publish their own items which they want to sell. Users are also able to edit theor personal profile, e.g username, email, head portrait.

The application also has a back-stage management page. Administrator can login and manage all users, items and orders. 

## Quick Start

1. Clone the repository to local.

2. Create a database named 'temp_db' in MySQL.

3. Execute `Item.sql` and `Order.sql` and `User.sql` in `BusyFish-Server/src/main/resources/sql`.

4. Open `BusyFish-Server/src/main/resources/application.yml` and modify the password of redis and MySQL to your own passwords.
5. Run `ServerApplication`.
6. Open `localhost:8080` to check it runs successfully.
7. Open terminal and go to `BusyFish-Server/BusyFish-Frontend`
8. `npm intall`
9. `npm run dev`




## Tech
Backend
- Java (jdk1.8 8u241)
- Spring Boot 2.5.6
- MySQL
- MyBatis 
- Redis
- Swagger2 

Frontend
- node and npm 
- Vue.js
- BootstrapVue

Deployment
- Tomcat
- Nginx
