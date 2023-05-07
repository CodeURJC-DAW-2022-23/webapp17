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
- Java api 17+ (Only for the non dockerized app)
- Docker 20.10.23

### Important Dependency Note
Project uses

- Spring Boot version 3.0.2
- MySql 8.0.32

### Install the dockerized application 
Assuming docker is installed on your machine run:
```
git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git
cd webapp17/docker
sh create_image.sh
```

### Install the non dockerized application 
Assuming java and docker are installed on your machine run:
```
git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git
cd webapp17/backend
./mvnw install
```

## 👽 Execution Instructions

### Run the dockerized application
To execute the application run the following command on the docker folder
```
docker compose up
```
Then, open in your browser
```
https://localhost:8443/
```

### Run frontend for testing
Node js is needed for running the app as it uses Angular. For running the frontend just install:
```
git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git
cd frontend/gustosa
npm install
```
And then run:
```
npx ng serve --proxy-config src/proxy.conf.json
```

### Run backend with h2 for testing (Non dockerized app)
Keep in mind project uses public api keys for google mail and google api. Change them to your own on application.properties file.

To run first install and on backend folder run
```
./mvnw spring-boot:run
```
Then, open in your browser
```
https://localhost:8443/
```
### Run backend with MySQL (Non dockerized app)
To run first install and on backend folder run
```
docker run --rm -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=gustosa -p 3306:3306 -d mysql:8.0.32
./mvnw spring-boot:run 
```
Then, open in your browser
```
https://localhost:8443/
```

## Deployment instructions
1. If the docker image has to be updated first recreate the image using(assuming docker and git is already installed):
```
git clone https://github.com/CodeURJC-DAW-2022-23/webapp17.git
cd webapp17/docker
sh create_image.sh
```
2. Once the image is created deploy it by running(if the image was already created skip 1 and cd to webapp17/docker in the repo):
```
docker compose up
```

## 💻 API documentation
To see the API documentation there are two options:
- [api-docs.yaml](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/api-docs/api-docs.yaml): To visualize the content in a file
- [api-docs.html](https://rawcdn.githack.com/CodeURJC-DAW-2022-23/webapp17/76a602669b935c7d168969d08f948255eb449469/api-docs/index.html): To visualize the content in a web page

## #️⃣ Features

### Video Demonstration
[![Video demonstration](https://user-images.githubusercontent.com/68378676/234138441-64f6d23d-6e13-4ef1-b3c2-c42b8ab2e1db.png)](https://youtu.be/ofAT3sMlRaQ)

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

#### FASE 2 Y 3:
![uml1](https://user-images.githubusercontent.com/68378676/227949724-cc067a7a-d746-42e8-bea0-29c643b1a7fe.png)!

![uml2](https://user-images.githubusercontent.com/68378676/227949860-72e146bd-6f7a-4505-be1e-5e10b618db1e.png)!

#### FASE 4:
![uml3](https://user-images.githubusercontent.com/68378676/234035994-64260775-aca7-45fa-a77c-e0f703f14a36.png)!




### 🍻 Team contribution

#### FASE 2️⃣:

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



#### FASE 3️⃣:

#### Jorge Vega Arias:

📗 Job participation:  
- UserApiController, BookingsApiController, MenuApiController and Postman api calls.
- Dockerfile and docker interaction as well as docker compose fix.
- Maven configuration to handle profiles and generate Open Api.
- Postman configuration for auto login.
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/33ef67e938e066b3f7ead78f49032b8635ab2938)
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/1e70571dd836d6bb9b2916d0880efa5c8a8a8d3a)  
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/6f6a1657a0d3edfece00337d4a0c4154b02150b2) 
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/a9b68871f0dec9840033a54f9b6df4203041d0ea)
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/8ba002860f09fc434ac40303caa129e90fea9cbe)
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/UserApiController.java)
- [File 2](https://github.com/Codehttps://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/CartApiController.javaURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/MenuApiController.java)
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/pom.xml)
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/docker/Dockerfile)
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/api.postman_collection.json)


#### Guillermo Grande Santi:
  
📗 Job participation:  
- ProductsRestController implementation (except admin methods), including testing in swagger-ui and their inclusion in postman collection with examples. Also, errors for unauthorized access and not found elements have been implemented. 
- CartRestController whole implementation, including testing in swagger-ui and their inclusion in postman collection with examples. Also, errors for unauthorized access and not found elements have been implemented.
- Docker-compose file.
- Updated class diagram and documentation.
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/2b9aeb24d9f9d5007214af3fd566d68c4ee72b7d) CartApiController Fully Implemented
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/f0a8e557c8a5a3a8a7811fb4a706e586a5900285) ProductsApiController Fully Implemented
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/46ae526311c4abb3817bd6a0592b9ae59aa3b5b0) Docker Compose File Structure
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/442d17a909af357cbb88228790ad0367a0045d87) Unauthorized access errors updated
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/c3af40c7e12c7f4e19b472873697c8fd3e254a2f) Update Class Diagram (RestControllers)
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/CartApiController.java) CartApiController.java
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/ProductsApiController.java) ProductsApiController.java
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/service/CartsService.java) CartService.java
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/service/ProductsService.java) ProductsService.java
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/docker/docker-compose.yml) docker-compose.yml
  
   
#### Alejandro López Adrados:

📗 Job participation:
- Implemented the whole Coupons API Controller (Methods, descriptions and error codes)
- Implemented the whole Comments API Controller (Methods, descriptions and error codes)
- Implemented the part of the admin (Add/Modify/Remove products) in Products API Controller (Methods, descriptions and error codes)
- Helped in the implementation of the Admin API Controller (Methods, descriptions and error codes)
- Helped in the implementation of the Orders API Controller (Methods, descriptions and error codes)
- Added the calls and examples for Coupons/Comments/Products(admin part)/Admin/Orders API Controllers in Postman and tested them (in Swagger and Postman)
- Tested some of the rest API calls and fixed some global errors

📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/0fdbf8d29d6ac0707ca182c6130e38346ba5aaa9) Coupons API Controller implemented
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/3d38a74a30e38e118988257c4f71cbb3452f2c6b) Comments API Controller implemented
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/9e28a253142a42c19fef8491d817e5bc281a27a2) Admin part of Products API Controller implemented
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/828d7aaf516ad0ee879dc14e3098d23113943090) Fixes in Orders API Controller
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/e936c29638853698b520f8a95d7fe5ffa4d3d7a8) Added upload/delete images in Products API Controller
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/CouponsApiController.java) CouponsApiController.java
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/ProductsApiController.java) ProductsApiController.java
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/CommentsApiController.java) CommentsApiController.java
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/service/ProductsService.java) ProductsService.java
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/OrdersApiController.java) OrdersApiController.java

#### Jesús González Gironda:

📗 Job participation:
-Implemented the AdminApiController.
-Implemented the OrdersApiController.
-Fixed some missing html.
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/a27cbf6837c7cf0f387416111ce167e85182b271)
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/d880190546512001a33c69ec4ba1c71d0130a689)
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/39adaf609752a316e11560fef90567959eef4559)
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/0f0f93b4984cb651136d937fe04e59c0e9e436c5)
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/13298a24bde814f5dfdbec19bde65a21767bf42f)
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/OrdersApiController.java)
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/api/AdminApiController.java)
- [File 3]()
- [File 4]()
- [File 5]()


#### FASE 4️⃣:

#### Jorge Vega Arias:

📗 Job participation:  
- Started Angular project and made starting examples for methodology
- Updated Dockerfile
- Made the app serve on URJC infastructure
- Made Login/Logout, Products, User Profile and general module and fix ups in angular
      
📃 Top commits:  
- [Commit 1 - Started angular project](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/9d299eb5adfe5eeb73a5d9c2356700d4ad1c56a1)
- [Commit 2 - Added HttpClient](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/4d96dd9528a2c73a82690561f86d0e631c7da5f3)  
- [Commit 3 - Login Logout](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/ea44a40220ecd45e47792f698d7c4ed13edd05c5) 
- [Commit 4 - Menu Products](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/6d9963e15e6f710ce439e88bdd8358a4fbda9191)
- [Commit 5 - User profile](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/176e03f3a7a33748949517a37fe0a5b8d8b9f2da)
      
📂 Top files:  
- [File 1 - Dockerfile](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/docker/Dockerfile)
- [File 2 - User Component](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/user/profile.component.ts)
- [File 3 - User Service](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/service/user.service.ts)
- [File 4 - Login](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/auth/login.component.ts)
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/book/bookingform.component.ts)


#### Guillermo Grande Santi:
  
📗 Job participation:  
- Order products template with its corresponding service and components.
- Cart and Checkout template with its corresponding services and components.
- Description of an individual template with its corresponding services and components, including comments per product.
- Final testing and preparation of the video and delivery.
- Updated Class Diagram and Job participation.
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/f3f90a856829b6b1a1c4c9bb50ef45c24cf8a198) Pagination of products with ts and api calls.
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/4fe0a986572937a41065131673258c9b93a3b9b6) Cart Page Initialized and Order Page Completed
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/34821b3ec6f42ad8bf4c494c35cc1bff8dd9f7c4) Cart fully functional
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/f406484748ca6bf00913d694c2c0fd80c2c6a257) Checkout form fully functional
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/374462103f3e894243e13d64b961946179077e78) Description page with comments paginated completed
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/service/products.service.ts) products.service.ts
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/service/cart.service.ts) cart.service.ts
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/model) product.model.ts & cart.model.ts
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/component/cart) cart.component.ts & cart.component.html & checkout.componente.ts & checkout.component.html
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/component/productsInfo) products.component.ts & products.component.html & description.componente.ts & description.component.html
  
   
#### Alejandro López Adrados:

📗 Job participation:
- Implemented template and functionality of Admin Coupons Menu (component, service and model)
- Implemented template and functionality of Admin Comments Menu (component, service and model)
- Implemented template and functionality of Admin Product Menu (component, service and model)
- Implemented template and partial functionality of Admin Orders Menu (component, service and model)
- Implemented template and partial functionality of Admin Users Menu (component, service and model)
- Implemented template and partial functionality of Admin Bookings Menu (component, service and model)
- Implemented template and partial functionality of Admin statistics (without charts)
- Fixed some errors in API controllers
- Implemented the sidebar of Admin Menu
- Tested the whole application and some errors
- Added documentation of the application

📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/94ff2b28fec18133b6136d2d2f11abadc86d9830) Products Admin Menu implemented
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/7ba35e9325de0e1241b21d7b6b401402b7ac7bcb) Comments Admin Menu implemented
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/1bcf36a84d20ab2fa1e123d229c408a9914972c5) Partial implementation of Users Admin Menu
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/ff91bef78456aaabcd5eec6059b783bd76a3bfd1) Finished the implementation of Coupons Admin Menu
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/1d65d79a58bd51a9809b244dcbb8896a323199af) Implementation of Admin Dashboard without charts (only statistics)
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/component/admin/coupons) coupons-admin.component (Main page and modals ts and html)
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/component/admin/products) products-admin.component (Main page and modals ts and html)
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/component/admin/comments) comments-admin.component ts and html
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/component/admin/users) users-admin.component (Main page and modals ts and html)
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/tree/main/frontend/gustosa/src/app/component/admin) sidebar.component ts and html

#### Jesús González Gironda:

📗 Job participation:
- Implemented template and functionality of Home Page (component, service and model)
- Implemented template and functionality of Information Page (component, service and model)
- Implemented footer template
- Implemented animations
      
📃 Top commits:  
- [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/7e2b8d9c8b88fd7fd9cd3b159e310a5db56f9448) Home page
- [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/b6c457fe0f9f9d61303bc6ea4ddd6ef08f14ee1d) Footer
- [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/a8b352b56b85124a953be08925b27bd4257ca171) Information page
- [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/356e46e151eb0f17e307522a2deac55ff3c48fcb) 
- [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/ca03f56f6e2eb0126d6ba61d837a70c6bb36594e)
      
📂 Top files:  
- [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/home/home.component.ts) Home components
- [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/footer.component.ts) Footer components
- [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/info/info.component.ts) Information components
- [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/info/info.component.html)
- [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/frontend/gustosa/src/app/component/home/home.component.html)
