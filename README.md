# Gustosa🍐

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 🐛 Gustosa. Best brazilian restaurant ever.
Web app for the best fast food franchaise Gustosa, made for Web Applications Development course for URJC.

## 👤 Team members

| Members                 | Emails                           |
|-------------------------|----------------------------------|
| Alejandro López Adrados | a.lopezad.2019@alumnos.urjc.es   |
| Guillermo Grande Santi  | g.grande.2019@alumnos.urjc.es    |
| Jesús Gonzalez Gironda  | j.gonzalezg.2019@alumnos.urjc.es |
| Jorge Vega Arias        | j.vega.2019@alumnos.urjc.es      |

## 👽 Build instructions

### Pre-Requirements
- Java api 17+
- Docker 20.10.23

### Important Dependency Note
Project uses

- Spring Boot version 3.0.2
- MySql 8.0.32

### Install
Assuming java and docker are installed on your machine run:
```
git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git
cd webapp17/backend
./mvnw install
```

## 👽 Execution Instructions

### Run backend with h2 for testing
Keep in mind project uses public api keys for google mail and google api. Change them to your own on application.properties file.

To run first install and on backend folder run
```
./mvnw spring-boot:run
```

### Run backend with my SQL
To run first install and on backend folder run
```
docker run --rm -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=gustosa -p 3306:3306 -d mysql:8.0.32
./mvnw spring-boot:run 
```

## #️⃣ Features

### Entities
- Users
- Coupons
- Dishes
- Booking
- Shopping Cart
- Comment

<img src=documents/database/azimutt_diagram/database_relations.png width="1024"/>

### 👩‍👦‍👦 User types

- 🧑‍🎓 Guest - Can access basic information such as menu, timetables but can not create any booking nor use any coupon.
    - Access timetable, menu and restaurant info
    - Create an account
    - Can see comments

- 👨‍💻 Registered User - Typical use case. A registered user has permission to do everything from Guest and can buy coupons, book tables, write comments and own a shopping cart.
    - Buy coupons
    - Book tables
    - Own a shopping cart
    - Edit profile data
    - Write comments

- 👮‍♀️ Admin access - Can edit timetables, coupons and menu. Also, they have tool access to analytical data and users.
    - Add/remove dishes from menu
    - Add coupons
    - Edit timetables
    - Manage users
    - Access analytics panel

### 🍃 Images
- Each dish will have an image
- Each user will have an image
- Each coupon may have an image

### 💹 Charts
- Admin chart, analytical data of the web site.

### ✉️ Complementary technology
- Email system for handling bookings and registration
- Pdf creation tool for orders
- Google log-in

### 📖 Advanced Algorithm
- Graph based algorithm for recommendations

### 📜 Page map

<img src=documents/wireframes/export.png width="1024"/>
<img src=documents/backend/pagemap/pagemap.png width="1024"/>

### 🍻 Team contribution

#### Jorge Vega Arias:

📗 Job participation:  
- Configured project, template design and dependency tests.
- Assisted in most template designs and mainly designed users templates with ajax, index, menu(admin, local), image addition modals, admin orders and admin books
- Security and OAuth2 integration for Google logins and Error.
- Email and PDF integration
- Graph Based recomendation query
- Database integration
- Made Login/Register/Validation/ForgotPassword scheme
- Made order management and booking management
- Made user management and comments
- Js integration with Cropperjs and Tinymcu for images and post edition
- Bug fixing and testing
- Database relations documentation, Page map, Github actions integration and general workflow.
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/2d3cb34abdfbf719f247e103f36bb80470be2c17) Security.  
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/ab3f35eea11d338986379cd74c552ef6014ce94c) User Profile.  
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/8ed0a3f452b1e9f891122865db3371d5d792a45b) Add comment.  
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/029e8e849e8724aca8706c17bcd3396ca5f04763) Booking and pdf integration.
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/4d8067090f9a09b699f153c42cce9518f4a8259f) General cleanup and error fixing. 
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/service/UsersService.java) UsersService.java
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/UsersController.java) UsersController.java  
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/security/SecurityConfig.java) SecurityConfig.java
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/info/user.mustache) user.mustache  
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/tools/Tools.java) Tools.java


#### Guillermo Grande Santi:
  
📗 Job participation:  
- Template for ordering products, including add to cart functionality and Pagination with Ajax for showing more than 8 products.  
- Template for showing the description of a product, every image of the product and their respective comments. This comments are also paginated with Ajax for showing more than 8 comments.   
- Template for showing the cart, being able to add or subtract amounts to added products and showing every product price besides of the total price. Also, added the functionality to redeem coupons.  
- Template for checking out, being able to see a billing form and the cart before proceeding to payment.  
- Global Navigation, Java controllers and Views initialized.  
- Access to the admin dashboard and admin sidebar.  
- Top 5 products sales chart with chart.js in Admin Dashboard.  
- Added Menu table in Menu template.  
- Fixed general errors, global testing and final translation.  
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/2bf51aa4fde78515714b289d9de09ea62a279544) Completed Pagination with Ajax and JS.  
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/864c73332b2b17deb8172dff3a85fc16070fe31c) Cart template fully functional.  
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/8378d5645c577925eab4a647b9ed962f4d9a0f5d) Sales chart available in admin dashboard.  
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/d8c072bbd2c227cf706e3b744d8c1e62836f68d5) Comments Pagination with Ajax in description page.  
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/bfcdd0fa5deda9988394d426016b9887eb3e4919) Added carousel for images in description.  
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/CartController.java) CartController.java  
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/ProductsController.java) ProductsController.java  
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/dishes/order.mustache) order.mustache  
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/dishes/description.mustache) description.mustache  
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/menu/cart.mustache) cart.mustache 
  
   
#### Alejandro López Adrados:

#### Jesús González Gironda:

### ⌛ Class and Templates UML Diagram
![uml1](https://user-images.githubusercontent.com/68378676/222984262-bd0e884b-6241-4cab-a1b2-43a1267127d4.png)
![uml2](https://user-images.githubusercontent.com/68378676/222984269-8bfb35d5-9302-4840-adb3-2defa4ab02e3.png)





