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

## Build instructions

### Requirements
· Java api 17+


## #️⃣ Planned Features

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
- Each dish may have an image
- Each user will have an image
- Each coupon will have an image

### 💹 Charts
- User chart, will show data explaining delicious food value of each dish and fit statistics.
- Admin chart, analytical data of the web site.

### ✉️ Complementary technology
- Email system for handling bookings and registration
- E-commerce integration for handling coupons and payments
- Google log-in

### 📖 Advanced Algorithm
- There will be an IA algorithm graph based for recommendations

### 📜 Page map

<img src=documents/wireframes/export.png width="1024"/>

### 👽 Execution Instructions

- Una vez clonado el repositorio, se necesitará JDK 17 o mayor y MySQL versión 8.0.32 (Community Server). También se ha usado la versión 4.0.0 Maven y la versión 3.0.2 de Spring.  
- También usamos extensiones de Visual Studio Code como Spring Extension Pack, Java Extension Pack y Mustache para facilitar     el desarrollo.  
- Variables de entorno usadas:  
    👾 $Env:HTTPS_PASSWORD = "gustosa"  
    👾 $Env:GOOGLE_CLIENT_ID = "129753407343-ptmtctev4mmnm2treadjd6farkpseh0i.apps.googleusercontent.com"  
    👾 $Env:GOOGLE_CLIENT_SECRET = "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"  
    👾 $Env:EMAIL_USERNAME = "gustosabestrestaurant@gmail.com"  
    👾 $Env:EMAIL_PASSWORD = "avgotokbmiwqldyz"  
- Ejecución por consola de comandos:  

### 🍻 Team contribution

#### Jorge Vega Arias:

#### Guillermo Grande Santi:
  
📗 Tareas realizadas:  
    - Template for ordering products, including add to cart functionality and Pagination with Ajax for showing more than 8 products.  
    - Template for showing the description of a product, every image of the product and their respective comments. This comments are also paginated with Ajax for showing more than 8 comments.   
    - Template for showing the cart, being able to add or subtract amounts to added products and showing every product price besides of the total price. Also, added the functionality to redeem coupons.  
    - Template for checking out, being able to see a billing form and the cart before proceeding to payment.  
    - Global Navigation, Java controllers and Views initialized.  
    - Access to the admin dashboard and admin sidebar.  
    - Top 5 products sales chart with chart.js in Admin Dashboard.  
    - Added Menu table in Menu template.  
    - Fixed general errors, global testing and final translation.  
      
📃 Commits más significativos:  
    - [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/2bf51aa4fde78515714b289d9de09ea62a279544) Completed Pagination with Ajax and JS.  
    - [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/864c73332b2b17deb8172dff3a85fc16070fe31c) Cart template fully functional.  
    - [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/8378d5645c577925eab4a647b9ed962f4d9a0f5d) Sales chart available in admin dashboard.  
    - [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/d8c072bbd2c227cf706e3b744d8c1e62836f68d5) Comments Pagination with Ajax in description page.  
    - [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/commit/bfcdd0fa5deda9988394d426016b9887eb3e4919) Added carousel for images in description.  
      
📂Ficheros más relevantes:  
    - [File 1](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/CartController.java) CartController.java  
    - [File 2](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/java/es/codeurjc/webapp17/controller/ProductsController.java) ProductsController.java  
    - [File 3](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/dishes/order.mustache) order.mustache  
    - [File 4](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/dishes/description.mustache) description.mustache  
    - [File 5](https://github.com/CodeURJC-DAW-2022-23/webapp17/blob/main/backend/src/main/resources/templates/menu/cart.mustache) cart.mustache  
  
   
#### Alejandro López Adrados:

#### Jesús González Gironda:



