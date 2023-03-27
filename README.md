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

## 🏗️ Build instructions

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

### Run backend with MySQL
To run first install and on backend folder run
```
docker run --rm -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=gustosa -p 3306:3306 -d mysql:8.0.32
./mvnw spring-boot:run 
```

## #️⃣ Features

### Entities
- Bookings
- Carts
- Cart items
- Comments
- Coupons
- Coupons image
- Credentials
- Posts
- Products
- Products images
- Profile image
- Users

<img src=documents/database/azimutt_diagram/database_relations.png width="1024"/>

### 👩‍👦‍👦 User types

- 🧑‍🎓 Guest - Can access basic information such as menu, timetables but can not create any booking nor use any coupon.
    - Access timetable, menu, cart and restaurant info
    - Create an account
    - See comments

- 👨‍💻 Registered User - Typical use case. A registered user has permission to do everything from Guest and can book tables, write comments and make orders using coupons.
    - Access to the user control panel (Profile data, last orders, bookings)
    - See product recomendations based on his last orders
    - Book tables
    - Make orders
    - Edit profile data
    - Write comments

- 👮‍♀️ Admin access - Can edit timetables, coupons and menu. Also, they have tool access to analytical data and users.
    - Access to the admin control panel (Graphic and stats)
    - Change the status (In process/finished) of the orders and remove them
    - Add/remove/edit products from cart
    - Add/remove/edit user/admin profiles
    - Delete comments
    - Edit the menu
    - Add/remove/edit coupons
    - Change the status (Confirmed/not confirmed) of the bookings and remove them

### 🍃 Images
- Each dish will have some images
- Each user will have a profile picture
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


### ⌛ Class and Templates UML Diagram
![uml1](https://user-images.githubusercontent.com/68378676/227949724-cc067a7a-d746-42e8-bea0-29c643b1a7fe.png)!

![uml2](https://user-images.githubusercontent.com/68378676/227949860-72e146bd-6f7a-4505-be1e-5e10b618db1e.png)!



### 🍻 Team contribution

#### FASE 2️⃣

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
- Fixed general errors, global testing, translation and documentation.  
      
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

📗 Job participation:
- Creation of the database classes and the relationships between them.
- Configuration of MySQL as the database of the project.
- Implemented the possibility of uploading/downloading images from database.
- Addition of examples to database (Added products, users, comments and coupons with all of their features).
- Implemented the functionality of adding a product into the cart and the cart view controller to show and delete items on it.
- Implemented the functionality of the coupons.
- Implemented the functionality and UI of the admin users manager (Show/create/delete/modify users/admins with all of their fields).
- Implemented the functionality and UI of the admin products manager (Show/add/delete/modify products with all of their fields).
- Implemented the functionality and UI of the admin coupons manager (Show/create/delete/modify coupons with all of their fields).
- Implemented the functionality and UI of the admin comments manager (Show/delete comments of the users).
- Implemented some SQL queries to add info to the admin dashboard and improve this view.
- Tested the web app and fixed general errors.
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/3a455e6f799ee7647dbe0ddf152c72fd894ffb57) Added admin products manager (func and UI)
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/d325f74380e7c95230bd677436734c752571a918) Added admin clients manager (functionality and UI).
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/18d578bd2139f2cae8299c7d4ab9d15b8509b939) Added coupons manager for admin (func and UI)
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/2960a6014c94d6bb0e8a782c0f56a27ee2b4fa09) Added database classes and relationships.
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/bcc16c8ed3b9ffc158cc8cc5477a36850ff18ce1) Added food examples
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/service/DatabaseInitializer.java) DatabaseInitializer.java  
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/admin/clients.mustache) clients.mustache 
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/admin/coupons.mustache) coupons.mustache  
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/admin/AdminClientsController.java) AdminClientsController.java 
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/admin/AdminProductsController.java) AdminProductsController.java 

#### Jesús González Gironda:

📗 Job participation:
- Booking template form
- Homepage changes and added animations for general look
- Help with translations
- Information page
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/18152293740f459e46cd57adea9aa1d3499ef76b) Added homepage design
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/b53716364b587d48a52b142dc65a849548c8331b) Information page design
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/2864a8fe824bf173af89d922fdd4d631805fe588) Basic forum for reservations
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/1461a06014802854f1dd8ea28555c7c14dd46e0a) Reservation form structure finished
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/5cc77e991a82192accd47647dfc03ffa3653c0cf) Fix up for broken css 
      
📂 Top files:  
- [File 1](backend/src/main/resources/static/css/styles2.css)
- [File 2](backend/src/main/resources/static/css/styles3.css)
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/index.mustache)
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/info/info.mustache)
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/static/js/scripts.js)



#### FASE 3️⃣

#### Jorge Vega Arias:

📗 Job participation:  

      
📃 Top commits:  
- [Commit 1] 
- [Commit 2]  
- [Commit 3] 
- [Commit 4]
- [Commit 5]
      
📂 Top files:  
- [File 1]
- [File 2]
- [File 3]
- [File 4]
- [File 5]


#### Guillermo Grande Santi:
  
📗 Job participation:  

      
📃 Top commits:  
- [Commit 1]
- [Commit 2]
- [Commit 3]
- [Commit 4]
- [Commit 5]
      
📂 Top files:  
- [File 1] 
- [File 2]
- [File 3]
- [File 4]
- [File 5]
  
   
#### Alejandro López Adrados:

📗 Job participation:

      
📃 Top commits:  
- [Commit 1]
- [Commit 2]
- [Commit 3]
- [Commit 4]
- [Commit 5]
      
📂 Top files:  
- [File 1] 
- [File 2]
- [File 3] 
- [File 4]
- [File 5]

#### Jesús González Gironda:

📗 Job participation:

      
📃 Top commits:  
- [Commit 1]
- [Commit 2]
- [Commit 3]
- [Commit 4]
- [Commit 5]
      
📂 Top files:  
- [File 1]
- [File 2]
- [File 3]
- [File 4]
- [File 5]


