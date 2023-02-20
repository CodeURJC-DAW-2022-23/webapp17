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

## #️⃣ Planned Features

### Entities
- Users
- Coupons
- Dishes
- Booking
- Shopping Cart
- Comment

<img src=documents/database/azimutt_diagram/database_relations.png width="720"/>

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