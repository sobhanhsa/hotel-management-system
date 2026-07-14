---
title: "Hotel Management System Documentation"
author: "Sobhan"
date: "July 2026"
---

<div align="center">

# Hotel Management System (HMS)

## Advanced Programming Course Project

---

### Ferdowsi University of Mashhad

**Faculty of Mathematical Sciences**  
**Department of Computer Science**

Academic Year: **1404–1405**

---

## Project Title

# Hotel Management System (HMS)

### A Console-Based Hotel Reservation and Management System Developed in Java

---

## Prepared By Seyed Sobhan Sadr

---

## Course

**Advanced Programming**

---

## Instructor

Doc Mehdi Cheet-saaz

---

## Programming Language

**Java**

---

## Version

**Version 1.0 (Final Release)**

---

**July 2026**

</div>

\newpage

# Abstract

The **Hotel Management System (HMS)** is a console-based application developed in **Java** as the final project for the Advanced Programming course. The primary objective of this project is to simulate the core operations of a modern hotel management system while demonstrating the practical application of object-oriented programming principles, software architecture, and clean code practices.

The system follows the **Model–View–Controller (MVC)** architectural pattern, separating the user interface, business logic, and data models into independent layers. This design improves maintainability, readability, and scalability while promoting a clear separation of responsibilities.

The application supports multiple user roles, including **Super Administrator**, **Hotel Manager**, **Receptionist**, and **Guest**, each with its own permissions and responsibilities. The implemented features include room management, reservation processing, guest management, invoice generation, payment processing, hotel services, report generation, and activity logging.

To satisfy the project requirements, several object-oriented concepts have been employed, including inheritance, abstraction, encapsulation, polymorphism, interfaces, enumerations, custom exception handling, and the Observer design pattern. The system also includes multiple report generators, financial management utilities, membership levels, room categorization, and service management.

**Keywords:** Java, Object-Oriented Programming, MVC Architecture, Hotel Management System, Reservation System, Console Application, Software Engineering.

\newpage

# 1. Introduction

The **Hotel Management System (HMS)** presented in this project is a console-based application developed entirely in **Java** as the final project for the Advanced Programming course. The system simulates the daily operations of a hotel by integrating room management, guest management, reservation processing, financial management, reporting, and user administration into a single application.

The project has been designed using the **Model–View–Controller (MVC)** architectural pattern to separate the presentation layer, business logic, and data model. This architecture improves maintainability, encourages modular development, and makes future extensions significantly easier.

To achieve a clean and extensible design, the application makes extensive use of object-oriented programming concepts such as inheritance, abstraction, encapsulation, polymorphism, interfaces, enumerations, and custom exception handling. In addition, the Observer design pattern has been implemented to notify guests on waiting lists when rooms become available.

The system supports four different user roles:

- **Super Administrator** – responsible for overall system administration and user management.
- **Hotel Manager** – responsible for hotel operations, room management, employee management, and report generation.
- **Receptionist** – responsible for reservation management, guest check-in/check-out, invoicing, and hotel services.
- **Guest** – able to search available rooms, make reservations, and manage personal bookings.

The developed application satisfies the functional requirements defined in the project specification while emphasizing software quality, modularity, and maintainability. Although the system is implemented as a console application with in-memory data storage, its layered architecture allows it to be extended into a graphical or database-driven application with minimal structural modifications.

\newpage

# 2. Project Objectives

The primary objective of this project is to design and implement a complete **Hotel Management System (HMS)** that demonstrates the practical application of object-oriented programming concepts and software engineering principles. The system aims to automate the core operations of a hotel while maintaining a modular, maintainable, and extensible architecture.

The project was developed according to the requirements of the Advanced Programming course and emphasizes clean code organization, separation of responsibilities, and proper use of Java language features.

The main objectives of the project are:

- Develop a role-based hotel management system supporting multiple categories of users.
- Manage hotel rooms with different room types, capacities, prices, and availability statuses.
- Allow guests to search available rooms and create reservations for specific dates.
- Prevent reservation conflicts by validating room availability and reservation periods.
- Support the complete reservation lifecycle, including reservation creation, confirmation, check-in, check-out, and cancellation.
- Automatically generate invoices for reservations and calculate accommodation costs, service costs, taxes, discounts, and remaining balances.
- Support multiple payment operations, including partial payments and refunds.
- Manage additional hotel services such as minibar, parking, maintenance requests, and extra cleaning.
- Maintain guest profiles, reservation histories, and membership levels with automatic membership upgrades.
- Generate administrative reports for hotel management, including occupancy reports, income reports, room status reports, debtor reports, popular room reports, and guest history reports.
- Record important system events through a centralized logging mechanism.
- Enforce role-based access control to ensure that each user can perform only authorized operations.
- Demonstrate the use of inheritance, abstraction, polymorphism, interfaces, enumerations, exception handling, and design patterns within a real-world software project.

By accomplishing these objectives, the project provides a comprehensive demonstration of object-oriented software development while producing a functional hotel management application that satisfies the specifications of the course project. :contentReference[oaicite:0]{index=0} :contentReference[oaicite:1]{index=1}

\newpage
# 3. System Architecture

## 3.1 Architectural Overview

The Hotel Management System follows the **Model–View–Controller (MVC)** architectural pattern with an additional **Manager (Service Layer)** that encapsulates the application's business logic. This layered architecture separates responsibilities between data representation, user interaction, application control, and business operations.

The overall architecture is illustrated below:

```text
                    +----------------------+
                    |        View          |
                    | (Console Interface)  |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    |     Controller       |
                    |  User Interaction    |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    |       Managers       |
                    |   Business Logic     |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    |        Models        |
                    |   Domain Objects     |
                    +----------------------+
```

Each layer has a well-defined responsibility, allowing modifications to one layer with minimal impact on the others.

Actually in this project some times controllers and managers are the same.

---

## 3.2 View Layer

The **View** layer provides the console-based user interface through which users interact with the system. Different menus are presented depending on the authenticated user's role.

Implemented views include:

- LoginView
- GuestView
- ReceptionistView
- HotelManagerView
- SuperAdminView
- Console (utility class)

The View layer is responsible only for receiving user input and displaying output. It does not perform any business logic.

---

## 3.3 Controller Layer

The Controller layer acts as the bridge between the user interface and the business layer. It receives requests from the views, validates user input when necessary, invokes the appropriate manager methods, and returns the results to the interface.

This separation ensures that presentation logic remains independent from application logic.

---

## 3.4 Manager Layer

The Manager layer contains the core business logic of the application. Instead of placing operational logic inside model classes, responsibilities are distributed among specialized manager classes.

The implemented managers are:

| Manager | Responsibility |
|---------|----------------|
| UserManager | User authentication, registration, role management, and access control |
| RoomManager | Room creation, searching, updating, and status management |
| ReservationEngine | Reservation lifecycle, conflict detection, check-in, check-out, cancellation, and waitlist notification |
| FinancialManager | Payment processing, refunds, debt calculation, and invoice-related financial operations |
| ServiceManager | Hotel service management and service assignment to invoices |
| ReportManager | Generation of management reports |
| LogManager | Recording system events and user activities |
| WaitlistManager | Managing waiting lists and room availability notifications |

This additional service layer keeps the system modular and prevents domain objects from containing excessive business logic.

---

## 3.5 Model Layer

The Model layer represents the core entities of the hotel management domain. Each model encapsulates the data and behaviors associated with a specific business concept.

Major model classes include:

- Person
- User
- Guest
- Staff
- Receptionist
- HotelManager
- SuperAdmin
- Room and its subclasses
- Reservation
- Invoice
- Payment
- Service and its subclasses
- Report classes
- LogEntry

Models maintain the application's state while delegating complex operations to the corresponding managers.

\newpage
# 4. Project Structure

To keep the project organized and maintainable, the source code is divided into several packages. Each package is responsible for a specific part of the system, following the MVC architecture and the separation of business logic into manager classes.

The project structure is shown below:

```text
src
├── enums
├── exceptions
├── interfaces
├── managers
├── models
├── view
└── Main.java
```

## Package Description

### `models`

This package contains the main entities of the hotel management system. These classes represent real-world objects such as guests, rooms, reservations, invoices, payments, and hotel services.

Some important classes in this package include:

- Person
- User
- Guest
- Staff
- Receptionist
- HotelManager
- SuperAdmin
- Room
- Reservation
- Invoice
- Payment
- Service

---

### `managers`

The manager package contains the business logic of the application. Instead of placing complex operations inside model classes, each major responsibility is handled by a dedicated manager class.

The implemented managers are:

- UserManager
- RoomManager
- ReservationEngine
- FinancialManager
- ServiceManager
- ReportManager
- LogManager
- WaitlistManager

Each manager is responsible for a specific part of the system, making the code easier to maintain and extend.

---

### `view`

The view package implements the console-based user interface. Different menus are provided for different user roles.

The main view classes are:

- LoginView
- GuestView
- ReceptionistView
- HotelManagerView
- SuperAdminView
- Console

---

### `interfaces`

This package contains the interfaces used throughout the project, including:

- Billable
- Searchable<T>
- Exportable
- Notifiable
- RoomObserver

These interfaces improve flexibility and demonstrate the use of abstraction in object-oriented programming.

---

### `exceptions`

This package contains all custom exceptions used by the application. They make error handling more readable and allow invalid operations to be detected clearly.

Examples include:

- RoomNotFoundException
- ReservationConflictException
- GuestNotFoundException
- AccessDeniedException
- InvalidDateRangeException

---

### `enums`

The enums package defines fixed values used across the project, improving readability and preventing invalid values.

Examples include:

- RoomType
- RoomStatus
- ReservationStatus
- MembershipLevel
- UserRole
- Season
- PaymentMethod
- LogLevel

---

### `Main.java`

The `Main` class is the entry point of the application. It initializes the managers, creates sample data, and starts the login process. After a successful login, the appropriate user interface is displayed based on the user's role.

\newpage

# 5. Class Design

The classes in the Hotel Management System are designed based on the main entities of a hotel environment. The design follows object-oriented programming principles such as inheritance, abstraction, encapsulation, and polymorphism.

The system contains several groups of related classes, including:

- User hierarchy
- Room hierarchy
- Reservation and invoice classes
- Service classes
- Report classes

---

## 5.1 User Class Hierarchy

Users in the system have different responsibilities and access levels. To avoid code duplication, common attributes are placed in abstract parent classes.

The inheritance structure is:

```text
                    Person
                  (Abstract)
                       |
                       |
                    User
                  (Abstract)
                       |
          +------------+------------+
          |                         |
       Guest                      Staff
                                  |
                 +----------------+----------------+
                 |                |                |
          Receptionist     HotelManager      SuperAdmin
```

---

## Person Class

`Person` is the base abstract class for all users in the system.

It contains common information shared by users, such as:

- Name
- Username

The purpose of this class is to provide a common structure for all people in the system.

---

## User Class

`User` extends `Person` and represents system users who can authenticate.

Additional attributes include:

- Password
- User role

This class is also abstract because different types of users have different responsibilities.

---

## Guest Class

`Guest` represents customers of the hotel.

Important attributes:

- Full name
- National ID
- Phone number
- Membership level
- Total stays
- Reservation history

Guest users can:

- Search available rooms
- Create reservations
- View their reservations
- Request cancellation
- View previous stays

---

## Staff Class

`Staff` is an abstract class representing hotel employees.

Additional information:

- Employee ID
- Hiring date

It is extended by different staff roles.

---

## Receptionist Class

The receptionist is responsible for daily reservation operations.

Main responsibilities:

- Creating reservations
- Performing check-in
- Performing check-out
- Creating invoices
- Adding guest services

---

## HotelManager Class

The hotel manager is responsible for managing hotel operations.

Main responsibilities:

- Managing rooms
- Creating receptionist accounts
- Viewing reports
- Changing room status
- Handling maintenance requests

---

## SuperAdmin Class

The SuperAdmin has the highest level of access in the system.

Responsibilities:

- Creating hotel manager accounts
- Managing system settings
- Viewing complete financial reports
- Managing user accounts
- Viewing system logs

---

## 5.2 Room Class Hierarchy

Rooms are designed using inheritance to represent different room types with different pricing behaviors.

The structure is:

```text
                    Room
                 (Abstract)
                       |
        +--------------+--------------+---------+
        |              |              |         |
 StandardRoom   DeluxeRoom          Suite       PentHouse
   
```

The `Room` class contains common room properties:

- Room number
- Room type
- Room status
- Base price
- Floor number
- Capacity

It also defines the method:

```java
calculatePrice()
```

which is overridden by child classes to apply different price multipliers.

The room types are:

| Class | Description |
|------|-------------|
| StandardRoom | Basic room with standard services |
| DeluxeRoom | Includes additional facilities such as balcony and minibar |
| Suite | Larger room with premium facilities |
| PentHouse | Highest-level room with VIP services |

Using inheritance allows each room type to implement its own pricing logic while sharing common functionality.

---

## 5.3 Reservation Class

The `Reservation` class represents a booking made by a guest.

It stores:

- Reservation ID
- Guest information
- Selected room
- Reservation dates
- Reservation status

A reservation can move through different states:

```text
PENDING
   |
CONFIRMED
   |
ACTIVE
   |
COMPLETED
```

It can also become:

```text
CANCELLED
```

when the guest cancels the reservation.

---

## 5.4 Invoice Class

The `Invoice` class represents the final cost of a guest's stay.

It manages:

- Accommodation cost
- Service costs
- Discounts
- Taxes
- Payments
- Remaining balance

The class supports partial payments and keeps track of the current financial status of a reservation.

---

This design allows the system to represent real hotel entities naturally while keeping responsibilities separated between classes.


\newpage
## 5.5 Interfaces and Design Patterns

Interfaces are used in the project to define common behaviors between different classes. They improve flexibility and reduce dependency between components by allowing different classes to implement the same functionality in their own way.

---

### Billable Interface

The `Billable` interface defines objects that can contribute to the final cost of an invoice.

Classes that implement this interface can provide their own price calculation logic.

Example usage:

- Hotel services
- Additional charges

This allows the financial system to calculate costs without depending on the exact type of the object.

---

### Searchable Interface

The `Searchable<T>` interface is used for objects that support searching operations.

It provides a general structure for search functionality and allows different managers to implement searching based on their own requirements.

Examples:

- Searching rooms by type
- Searching guests
- Searching reservations

Using a generic interface makes the searching mechanism reusable.

---

### Exportable Interface

The `Exportable` interface defines objects that can be converted into an external format.

It is mainly used by report-related classes to provide output generation.

Examples:

- Financial reports
- Occupancy reports
- Guest history reports

---

### Notifiable Interface

The `Notifiable` interface represents objects that can receive notifications.

It is used in situations where system events need to inform users.

For example:

- Room availability notifications
- Waiting list updates

---

\newpage

# 5.6 Observer Design Pattern

The project uses the **Observer design pattern** to implement the hotel waiting list mechanism.

The purpose of this pattern is to automatically notify interested users when a specific event occurs.

In this project:

- The `WaitlistManager` acts as the subject.
- Guests waiting for a room act as observers.
- When a room becomes available, registered guests are notified.

The general workflow is:

```text
Guest
  |
  | joins waiting list
  v
WaitlistManager
  |
  | room becomes available
  v
Notify registered guests
```

This design prevents the system from continuously checking for available rooms and provides a cleaner event-based solution.

---

# 5.7 Encapsulation

Encapsulation is applied throughout the project by keeping class attributes private and controlling access through methods.

Example:

```java
private String username;
private String password;
```

Instead of allowing direct modification, classes provide controlled methods for accessing or changing their data.

Benefits:

- Prevents invalid states
- Protects internal data
- Makes future changes easier

---

# 5.8 Polymorphism

Polymorphism is used in different parts of the system where objects with the same parent type can behave differently.

For example, different room types inherit from the `Room` class and override the price calculation method:

```java
calculatePrice()
```

Each room type applies its own pricing rules while being treated as a general `Room` object by the system.

This allows the system to support new room types without changing existing management logic.

---

# 5.9 Abstraction

Abstract classes are used to represent concepts that should not be instantiated directly.

Examples:

- `Person`
- `User`
- `Staff`
- `Room`

These classes contain common attributes and behaviors shared by their subclasses while leaving specific implementation details to child classes.

---

Using interfaces, inheritance, and design patterns makes the system easier to extend and maintain while demonstrating the main concepts of object-oriented programming.

\newpage

# 6. Manager Classes Documentation

The manager classes are responsible for handling the main operations and business rules of the hotel management system.

Instead of placing complex operations inside model classes, the project uses separate manager classes. Each manager focuses on one specific responsibility, which makes the code easier to understand, test, and extend.

The main manager classes are:

- UserManager
- RoomManager
- ReservationEngine
- FinancialManager
- ServiceManager
- ReportManager
- LogManager
- WaitlistManager

---

# 6.1 UserManager

The `UserManager` class is responsible for managing system users and authentication.

Its main responsibilities include:

- Creating new user accounts
- Checking login information
- Managing user roles
- Searching users
- Controlling access levels

The system supports four user roles:

- SuperAdmin
- HotelManager
- Receptionist
- Guest

Each role has different permissions, and the manager ensures that users can only access allowed parts of the system.

Example operations:

```java
login()
registerGuest()
createHotelManager()
createReceptionist()
```

---

# 6.2 RoomManager

The `RoomManager` class manages all hotel rooms.

Its responsibilities include:

- Adding rooms
- Removing rooms
- Finding rooms by room number
- Searching available rooms
- Updating room status

Rooms can have different statuses:

```text
AVAILABLE
RESERVED
OCCUPIED
MAINTENANCE
OUT_OF_SERVICE
```

The manager also works with `ReservationEngine` to check room availability before creating reservations.

Main operations:

```java
addRoom()

removeRoom()

findRoom()

searchAvailableRooms()

updateRoomStatus()
```

---

# 6.3 ReservationEngine

The `ReservationEngine` is the main component responsible for reservation management.

It handles the complete reservation lifecycle:

```text
Create Reservation

        ↓

Confirm Reservation

        ↓

Check-In

        ↓

Check-Out

        ↓

Complete Reservation
```

It also handles cancellation:

```text
Cancel Reservation

        ↓

Calculate Penalty

        ↓

Refund Payment

        ↓

Free Room
```

Main responsibilities:

- Creating reservations
- Checking date conflicts
- Validating room availability
- Managing reservation status
- Performing check-in/check-out
- Handling cancellation
- Updating room status
- Notifying waiting users when rooms become available

Important methods:

```java
createReservation()

confirmReservation()

checkIn()

checkOut()

cancelReservation()

searchAvailableRooms()
```

The engine prevents double booking by checking existing reservations before creating a new one.

---

# 6.4 FinancialManager

The `FinancialManager` handles all financial operations of the hotel.

Its responsibilities include:

- Processing payments
- Calculating remaining balance
- Checking unpaid invoices
- Processing refunds

The manager works with the `Invoice` class and keeps financial operations separate from reservation logic.

Main operations:

```java
makePayment()

calculateDebt()

hasDebt()

refund()
```

The system supports:

- Full payments
- Partial payments
- Refunds after cancellation

---

# 6.5 ServiceManager

The `ServiceManager` manages additional hotel services.

Examples of services:

- MiniBar
- Parking
- Extra Cleaning
- Maintenance services

Its responsibilities include:

- Providing available services
- Adding services to invoices
- Managing service orders

The separation of services from reservations allows new services to be added without modifying the reservation system.

Main operations:

```java
getServices()

addServiceToInvoice()

createServiceOrder()
```

\newpage

# 6.6 ReportManager

The `ReportManager` is responsible for generating different hotel reports.

Available reports include:

| Report | Description |
|---|---|
| Room Status Report | Shows current status of rooms |
| Occupancy Report | Shows occupied and available rooms |
| Income Report | Calculates hotel income |
| Popular Room Report | Shows most reserved rooms |
| Debtor Report | Shows unpaid invoices |
| Guest History Report | Shows guest reservation history |

The manager collects required information from other managers and creates report objects.

Main operations:

```java
roomStatusReport()

occupancyReport()

monthlyIncomeReport()

popularRoomsReport()

debtorsReport()

guestHistoryReport()
```

\newpage

# 6.7 LogManager

The `LogManager` provides centralized logging for the application.

It records important events such as:

- Login attempts
- Reservation creation
- Payments
- Refunds
- Check-in/check-out operations
- Administrative actions

Each log entry contains:

- Log level
- Username
- Action
- Details
- Time information

Main operations:

```java
addLog()

getLogs()

printLogs()
```

Centralizing logs makes system monitoring easier.

\newpage

# 6.8 WaitlistManager

The `WaitlistManager` implements the observer mechanism used for room availability notifications.

When a room becomes available, registered observers are notified automatically.

The workflow is:

```text
Room becomes available

        ↓

WaitlistManager receives event

        ↓

Registered observers are notified
```

This manager implements:

```java
RoomObserver
```

and supports automatic notification when rooms are released.

---

The manager layer is one of the main design decisions in this project. It keeps business rules separated from data classes and makes each part of the system easier to modify and maintain.

\newpage

# 7. Model Classes Documentation

The model layer contains the main entities of the Hotel Management System. These classes represent real-world concepts such as users, rooms, reservations, invoices, payments, and services.

Each model is responsible for maintaining its own data and basic behaviors, while complex business operations are handled by manager classes.

---

# 7.1 Person Model

`Person` is the base abstract class for all people in the system.

It contains common information shared between different users.

Main attributes:

- Name
- Contact information

The purpose of this class is to avoid repeating common fields in different user classes.

Child classes:

- User
- Staff

---

# 7.2 User Model

`User` represents an authenticated user of the system.

It extends the `Person` class and adds authentication-related information.

Main attributes:

- Username
- Password
- User role

The user role determines the permissions available to the account.

Available roles:

```text
GUEST

RECEPTIONIST

HOTEL_MANAGER

SUPER_ADMIN
```

---

# 7.3 Guest Model

The `Guest` class represents hotel customers.

A guest can make reservations and use hotel services.

Important attributes:

- Guest ID
- Name
- Phone number
- National ID
- Membership level
- Reservation history

Main responsibilities:

- Store guest information
- Maintain reservation records
- Track membership status

The membership level is used by the financial system to apply possible discounts.

---

# 7.4 Staff Model

`Staff` is an abstract class representing hotel employees.

It extends `User` and contains common employee information.

Examples of staff members:

- Receptionist
- Hotel Manager
- SuperAdmin

Common attributes:

- Employee ID
- Employment information

---

# 7.5 Room Model

The `Room` class represents a hotel room.

It is an abstract class because different room types have different characteristics and pricing rules.

Main attributes:

- Room number
- Floor
- Capacity
- Base price
- Room status
- Room type

Room status values:

```text
AVAILABLE

RESERVED

OCCUPIED

MAINTENANCE

OUT_OF_SERVICE
```

The room class defines common behavior such as price calculation, which is implemented differently by subclasses.

Room subclasses include:

- StandardRoom
- DeluxeRoom
- Suite
- PentHouse

---

# 7.6 Reservation Model

The `Reservation` class represents a booking made by a guest.

It connects three main objects:

```text
Guest

  +

Room

  +

Reservation Dates
```

Main attributes:

- Reservation ID
- Guest
- Room
- Start date
- End date
- Reservation status

Reservation status:

```text
PENDING

CONFIRMED

ACTIVE

COMPLETED

CANCELLED
```

The reservation object stores the current state of the booking during its lifecycle.

---

# 7.7 Invoice Model

The `Invoice` class represents the financial document created for a reservation.

It contains all costs related to a guest's stay.

Main attributes:

- Invoice ID
- Reservation
- Room charges
- Service charges
- Payments
- Discount
- Tax

The invoice calculates:

- Total cost
- Paid amount
- Remaining balance

It supports multiple payments, allowing guests to pay partially.

---

# 7.8 Payment Model

The `Payment` class represents a financial transaction.

Main information:

- Payment ID
- Amount
- Payment method
- Date
- Related invoice

Payment methods include:

```text
CASH

CARD

ONLINE
```

Multiple payment objects can belong to one invoice.

---

# 7.9 Service Model

The `Service` class represents additional hotel services.

Examples:

- MiniBar
- Parking
- Extra Cleaning
- Maintenance

Services can be added to an invoice as additional charges.

The service hierarchy allows different services to have different behaviors.

Example:

```text
Service
   |
   +---- MiniBar
   |
   +---- Parking
   |
   +---- ExtraCleaning
```

Each service can define its own pricing logic.

---

# 7.10 Report Models

Report classes represent generated information about the hotel.

Examples:

- OccupancyReport
- IncomeReport
- GuestHistoryReport
- DebtorReport
- PopularRoomReport

Reports collect information from managers and provide a structured representation of system data.

---

# 7.11 Log Model

The log model represents recorded system activities.

Each log entry contains information such as:

- User
- Action
- Time
- Log level
- Description

Logs help administrators monitor important events in the system.

---

The model layer provides the foundation of the application by representing the main concepts of the hotel environment. By separating models from managers, the system keeps data structures simple while allowing business rules to evolve independently.

\newpage

# 8. Reservation Workflow

The reservation system is the main part of the Hotel Management System. It is responsible for managing the complete lifecycle of a guest reservation, from the initial request until the final payment and completion of the stay.

The reservation process is managed by the `ReservationEngine` class. This class communicates with other components such as `RoomManager`, `FinancialManager`, and `LogManager` to ensure that all operations follow the hotel rules.

---

# 8.1 Reservation Lifecycle

Each reservation moves through several states during its lifetime:

```text
PENDING

   |

   v

CONFIRMED

   |

   v

ACTIVE

   |

   v

COMPLETED
```

A reservation can also be cancelled:

```text
PENDING / CONFIRMED

        |

        v

    CANCELLED
```

The status is stored using the `ReservationStatus` enum.

---

# 8.2 Creating a Reservation

When a guest or receptionist creates a reservation, the system performs several checks before saving it.

The process is:

```text
Guest selects room

        |

        v

Check room availability

        |

        v

Check reservation date conflict

        |

        v

Create Reservation object

        |

        v

Change room status

        |

        v

Save reservation
```

Before creating a reservation, the system verifies:

- The room exists.
- The room is available.
- The requested dates are valid.
- No other reservation conflicts with the selected period.

If any validation fails, the appropriate exception is thrown.

Examples:

- `RoomNotFoundException`
- `RoomNotAvailableException`
- `ReservationConflictException`
- `InvalidDateRangeException`

---

# 8.3 Room Availability Checking

The system prevents double booking by checking existing reservations before assigning a room.

A room cannot be reserved if:

- It is already occupied.
- It is under maintenance.
- Another reservation exists during the requested date range.

The available rooms can be searched based on:

- Room type
- Date range
- Capacity

Example:

```text
Search Request

Room Type: Deluxe

Check-in: 2026/07/20

Check-out: 2026/07/25


Result:

Available Deluxe Rooms
```

---

# 8.4 Check-In Process

Check-in converts a confirmed reservation into an active stay.

The workflow:

```text
Confirmed Reservation

        |

        v

Receptionist performs check-in

        |

        v

Reservation status = ACTIVE

        |

        v

Room status = OCCUPIED
```

During check-in, the system verifies that:

- The reservation exists.
- The reservation is in a valid state.
- The guest information is correct.

---

# 8.5 Check-Out Process

Check-out completes the guest's stay and creates the final invoice.

The workflow:

```text
Active Reservation

        |

        v

Receptionist performs check-out

        |

        v

Calculate final cost

        |

        v

Create Invoice

        |

        v

Reservation status = COMPLETED

        |

        v

Room status = AVAILABLE
```

The final cost includes:

- Room cost
- Additional services
- Discounts
- Taxes

---

# 8.6 Cancellation Process

Guests can request reservation cancellation.

The cancellation process:

```text
Cancellation Request

        |

        v

Check reservation status

        |

        v

Calculate possible penalty

        |

        v

Process refund

        |

        v

Release room

        |

        v

Reservation status = CANCELLED
```

The financial operation is handled separately by `FinancialManager`.

This prevents reservation logic from directly managing payment operations.

---

# 8.7 Reservation and Manager Communication

The reservation workflow uses multiple managers:

```text
              ReservationEngine

                    |
     +--------------+--------------+
     |              |              |
     v              v              v

RoomManager   FinancialManager   LogManager
```

Responsibilities:

| Component | Responsibility |
|---|---|
| ReservationEngine | Controls reservation lifecycle |
| RoomManager | Checks and updates room status |
| FinancialManager | Handles payments and refunds |
| LogManager | Records important events |

---

# 8.8 Logging Reservation Events

Important reservation actions are recorded using `LogManager`.

Examples:

- Reservation created
- Check-in performed
- Check-out completed
- Reservation cancelled
- Payment processed

Logging helps administrators monitor system activity and track important operations.

---

The reservation workflow separates different responsibilities between classes and ensures that each operation follows the defined business rules of the hotel system.

\newpage

# 9. Financial System

The financial system is responsible for managing all monetary operations in the Hotel Management System. It handles invoice creation, payment processing, refunds, discounts, taxes, and debt tracking.

To keep financial operations independent from reservation logic, all financial-related responsibilities are handled by the `FinancialManager` class.

The main components of the financial system are:

- Invoice
- Payment
- FinancialManager
- Service charges
- Discounts and taxes

---

# 9.1 Invoice Management

The `Invoice` class represents the final bill of a guest's stay.

An invoice is created after the guest completes the check-out process. It contains all costs related to the reservation.

The invoice includes:

- Room accommodation cost
- Additional service costs
- Membership discounts
- Seasonal pricing effects
- Taxes
- Payments

The general calculation flow is:

```text
Room Cost

      +

Additional Services

      -

Membership Discount

      +

Tax

      =

Final Invoice Amount
```

---

# 9.2 Room Cost Calculation

The room price is calculated based on several factors.

The final room cost depends on:

- Room type
- Season
- Number of guests
- Guest membership level

The calculation formula is:

```text
Final Price =
Base Price × Room Type Multiplier
× Seasonal Multiplier
× Guest Count Multiplier
× (1 - Membership Discount)
```

Different room types have different price multipliers.

Example:

| Room Type | Multiplier |
|---|---:|
| Standard | 1.0 |
| Deluxe | 1.5 |
| Suite | 2.5 |
| PentHouse | 5.0 |

The calculation logic is implemented through the `calculatePrice()` method in room subclasses.

---

# 9.3 Membership Discounts

The system supports different guest membership levels.

Membership discounts:

| Level | Discount |
|---|---:|
| Bronze | 0% |
| Silver | 5% |
| Gold | 10% |
| Platinum | 15% |

The discount is automatically applied when generating the invoice.

This allows loyal guests to receive benefits based on their previous stays.

---

# 9.4 Payment Management

The `Payment` class represents a financial transaction.

Each payment contains:

- Payment ID
- Amount
- Payment method
- Date
- Related invoice

An invoice can have multiple payments, which allows the system to support partial payments.

Example:

```text
Invoice Amount: 10,000,000

Payment 1:
5,000,000

Payment 2:
3,000,000

Remaining Debt:
2,000,000
```

---

# 9.5 FinancialManager

The `FinancialManager` class controls financial operations.

Its responsibilities include:

- Processing payments
- Calculating remaining debt
- Checking unpaid invoices
- Processing refunds
- Updating financial records

Main methods:

```java
makePayment()

calculateDebt()

hasDebt()

refund()
```

Keeping these operations inside a separate manager prevents financial logic from being mixed with reservation or user management.

\newpage

# 9.6 Refund System

When a reservation is cancelled, the system calculates the refundable amount based on cancellation rules.

The cancellation process is:

```text
Reservation Cancellation

          |

          v

Calculate Penalty

          |

          v

Calculate Refund Amount

          |

          v

Process Refund

          |

          v

Update Invoice
```

The refund operation is handled by `FinancialManager` instead of `ReservationEngine`.

This separation keeps each class responsible for its own task.

---

# 9.7 Debt Management

The system tracks unpaid invoices.

The financial manager can:

- Check whether a guest has unpaid balance.
- Calculate remaining debt.
- Generate debtor reports.

This information is also used by report generation modules.

---

# 9.8 Financial Logging

Important financial operations are recorded using `LogManager`.

Examples:

- Payment completed
- Refund issued
- Invoice created
- Debt updated

Logging financial operations helps administrators monitor system activities.

---

The financial system separates calculation, payment, and reservation responsibilities. This design makes future changes, such as adding new payment methods or modifying pricing rules, easier without affecting other parts of the application.

\newpage

# 10. Reporting System

The reporting system provides administrators and managers with useful information about hotel operations. It collects data from different parts of the system and generates reports that help monitor reservations, rooms, guests, and financial activities.

All reporting operations are handled by the `ReportManager` class. Separating report generation from other managers keeps the business logic organized and prevents duplicated code.

The main report categories are:

- Room reports
- Reservation reports
- Financial reports
- Guest reports

\newpage

# 10.1 ReportManager

The `ReportManager` class is responsible for creating and managing different types of reports.

Its responsibilities include:

- Collecting required data from managers
- Creating report objects
- Providing formatted report information
- Supporting report export functionality

The manager communicates with:

- `RoomManager`
- `ReservationEngine`
- `FinancialManager`
- `UserManager`

to gather the required information.

Main operations:

```java
generateRoomStatusReport()

generateOccupancyReport()

generateIncomeReport()

generateGuestHistoryReport()

generateDebtorReport()

generatePopularRoomReport()
```

\newpage

# 10.2 Room Status Report

The room status report provides information about the current state of hotel rooms.

It shows:

- Room number
- Room type
- Current status
- Availability

Example:

```text
Room Number: 204

Type: Deluxe

Status: AVAILABLE
```

This report helps hotel managers monitor room conditions.

---

# 10.3 Occupancy Report

The occupancy report provides information about hotel usage.

It includes:

- Number of occupied rooms
- Number of available rooms
- Occupancy percentage

Example calculation:

```text
Occupancy Rate =

Occupied Rooms / Total Rooms × 100
```

This report helps managers understand hotel capacity usage.

\newpage

# 10.4 Income Report

The income report shows financial information generated from completed reservations.

It includes:

- Total income
- Payment records
- Reservation payments
- Service charges

The report receives information from `FinancialManager`.

This separation ensures that financial calculations remain inside the financial layer while reports only display the results.

\newpage

# 10.5 Guest History Report

The guest history report displays previous activities of a guest.

It contains:

- Guest information
- Previous reservations
- Stay dates
- Total spending
- Membership information

This report is useful for understanding guest behavior and maintaining customer records.

---

# 10.6 Debtor Report

The debtor report identifies guests who have unpaid balances.

It includes:

- Guest information
- Invoice information
- Remaining debt

The report uses information provided by `FinancialManager`.

Example:

```text
Guest: Ali Ahmadi

Invoice: 1025

Remaining Balance: 2,000,000
```

---

# 10.7 Popular Room Report

The popular room report shows which rooms are reserved most frequently.

It helps managers analyze:

- Room popularity
- Customer preferences
- Room demand

This information can be used for future pricing and management decisions.

---

# 10.8 Report Generation Flow

The general process of creating a report is:

```text
Manager requests report

          |

          v

ReportManager collects data

          |

          v

Create Report Object

          |

          v

Display / Export Report
```

---

# 10.9 Exportable Reports

Report classes implement the `Exportable` interface.

This design allows reports to define their own output format while keeping the report generation process independent from the output method.

Future extensions can add:

- PDF export
- CSV export
- Database storage

without changing the main reporting logic.

---

The reporting system provides a centralized way to analyze hotel information. By separating report generation from data management classes, the project maintains a cleaner architecture and makes adding new reports easier.


\newpage

# 11. User Interface and Application Flow

The user interface of the Hotel Management System is implemented as a **console-based interface (CLI)**. Users interact with the system through menus that are displayed according to their role.

The interface layer is separated from the business logic. Views are responsible only for:

- Receiving user input
- Displaying information
- Calling the appropriate managers

The main business operations are handled by manager classes.

---

\newpage
# 11.1 Application Startup Flow

When the application starts, the `Main` class performs the initial setup.

The startup process is:

```text
Start Application

        |

        v

Create Managers

        |

        v

Initialize Sample Data

        |

        v

Create SuperAdmin Account

        |

        v

Display Login Menu
```

The required managers are created first:

- UserManager
- RoomManager
- ReservationEngine
- FinancialManager
- ServiceManager
- ReportManager
- LogManager

After initialization, the user is redirected to the login screen.

---

\newpage
# 11.2 LoginView

The `LoginView` is the first interface shown to users.

Its responsibilities:

- Receive username and password
- Authenticate users
- Identify user roles
- Redirect users to the correct menu

The workflow:

```text
Enter Username and Password

          |

          v

UserManager Authentication

          |

          v

Check User Role

          |

          v

Open Related View
```

Depending on the role, the user is redirected to:

- GuestView
- ReceptionistView
- HotelManagerView
- SuperAdminView

---

# 11.3 GuestView

The `GuestView` provides limited access for hotel customers.

Available operations:

- Search available rooms
- Create reservations
- View personal reservations
- Request reservation cancellation
- View stay history
- View membership level

The guest cannot access administrative operations.

Example menu:

```text
1. Search Rooms

2. Make Reservation

3. View My Reservations

4. Cancel Reservation

5. View History

6. Logout
```

---

\newpage
# 11.4 ReceptionistView

The `ReceptionistView` provides access to daily hotel operations.

The receptionist can:

- Create reservations for guests
- Perform check-in
- Perform check-out
- Generate invoices
- Add guest services
- Search available rooms

Example workflow:

```text
Receptionist

      |

      v

Create Reservation

      |

      v

Guest Check-In

      |

      v

Guest Check-Out

      |

      v

Generate Invoice
```

---

# 11.5 HotelManagerView

The `HotelManagerView` is used for managing hotel operations.

Available features:

- Add and remove rooms
- Change room status
- Create receptionist accounts
- View reservations
- Generate reports
- Manage maintenance requests

The hotel manager has access to operational reports but cannot perform system-level administration.

---

# 11.6 SuperAdminView

The `SuperAdminView` provides the highest level of access.

Available operations:

- Create hotel manager accounts
- Manage users
- View system logs
- View complete financial reports
- Modify system settings

The SuperAdmin account is initialized automatically when the system starts.

---

# 11.7 Console Utility Class

The `Console` class provides helper methods for displaying messages and receiving input.

It is used throughout the view layer to keep console operations consistent.

Examples:

```java
Console.print()

Console.success()

Console.error()

Console.readString()
```

This avoids repeating input/output code in different views.

---

\newpage

# 11.8 User Flow Summary

The complete user interaction flow is:

```text
                 Start

                   |

                   v

              LoginView

                   |

        +----------+----------+
        |          |          |
        v          v          v

     Guest   Receptionist  Manager/Admin

        |

        v

    Perform Operations

        |

        v

       Logout
```

The separation between views and managers allows the interface to be changed in the future without modifying the main business logic.

# 12. Exception Handling

Exception handling is used throughout the Hotel Management System to detect and manage invalid operations. Instead of allowing errors to terminate the program, the system uses custom exceptions to provide meaningful error messages and maintain a stable execution flow.

The project uses Java's exception handling mechanism together with custom exception classes to represent different types of errors that can occur during system operation.

---

# 12.1 Purpose of Exception Handling

The main goals of using exceptions in the project are:

- Prevent invalid operations.
- Provide clear error messages to users.
- Separate error handling from normal program logic.
- Improve code readability and maintainability.
- Prevent the system from entering an invalid state.

For example, attempting to reserve an unavailable room should not crash the program. Instead, the system should notify the user about the reason of failure.

---

# 12.2 Custom Exception Structure

Custom exceptions are placed in the `exceptions` package.

The general structure is:

```text
Exception

    |

    Runtime Errors

    +

    |

    Custom Exceptions      
    |
    Hotel Exceptions    
```

Each exception represents a specific problem in the system.

---

# 12.3 Reservation Exceptions

Reservation-related exceptions handle problems during booking operations.

Examples:

## RoomNotFoundException

Thrown when a requested room does not exist.

Example:

```text
User selects room number 999

        |

        v

RoomNotFoundException
```

---

## RoomNotAvailableException

Thrown when a room cannot be reserved because it is unavailable.

Possible reasons:

- Already reserved
- Occupied
- Under maintenance

---

## ReservationConflictException

Thrown when a new reservation overlaps with an existing reservation.

Example:

```text
Existing reservation:

20 July - 25 July


New request:

22 July - 27 July


Result:

ReservationConflictException
```

This prevents double booking.

---

## InvalidDateRangeException

Thrown when the selected reservation dates are invalid.

Examples:

- Check-out before check-in
- Reservation duration is invalid
- Past dates

---

# 12.4 User and Access Exceptions

These exceptions handle authentication and permission problems.

## UserNotFoundException

Thrown when a user cannot be found in the system.

---

## InvalidCredentialsException

Thrown when login information is incorrect.

Example:

```text
Username: user1

Password: wrong_password


Result:

InvalidCredentialsException
```

---

## AccessDeniedException

Thrown when a user tries to perform an operation that is not allowed for their role.

Example:

```text
Guest attempts to create a receptionist account

        |

        v

AccessDeniedException
```

---

# 12.5 Financial Exceptions

Financial operations also require validation.

Examples:

## InsufficientBalanceException

Thrown when the payment amount is not valid.

---

## InvoiceNotFoundException

Thrown when a requested invoice does not exist.

---

\newpage
# 12.6 Exception Handling Flow

The general exception flow in the application is:

```text
User Action

      |

      v

Controller / View calls Manager

      |

      v

Manager validates operation

      |

      +----------------+

      |                |

      v                v

 Success          Exception

      |                |

      v                v

 Continue        Show Error Message
```

The view layer catches exceptions and displays appropriate messages to the user.

---

\newpage
# 12.7 Example Usage

Example of handling a reservation error:

```java
try {
    reservationEngine.createReservation(...);
}
catch (RoomNotAvailableException e) {
    Console.error(e.getMessage());
}
```

This approach keeps the application running while informing the user about the problem.

---

# 12.8 Benefits of the Exception Design

Using custom exceptions provides several advantages:

- Makes errors easier to understand.
- Avoids using generic exceptions everywhere.
- Improves debugging.
- Keeps business rules clear.
- Makes future development easier.

The exception system helps maintain the reliability of the application by ensuring that invalid operations are detected before they affect the system state.

\newpage
# 13. Logging System

The logging system is responsible for recording important events that happen during the execution of the Hotel Management System.

Logging helps administrators monitor system activities, track user actions, and identify possible problems. All logging operations are centralized inside the `LogManager` class.

---

# 13.1 Purpose of Logging

The main purposes of the logging system are:

- Recording important user actions.
- Monitoring system operations.
- Providing information for debugging.
- Tracking administrative activities.
- Improving system transparency.

Examples of actions that should be logged:

- User login attempts.
- Creating reservations.
- Check-in and check-out operations.
- Payment operations.
- Refund operations.
- Room management actions.

---

# 13.2 LogManager Class

The `LogManager` class is responsible for creating and storing log records.

Its responsibilities include:

- Adding new log entries.
- Maintaining the list of system logs.
- Providing logs for administrators.
- Displaying recorded activities.

Main operations:

```java
addLog()

getLogs()

printLogs()
```

The manager is shared between different components of the system so that all important events are stored in one centralized location.

---

# 13.3 LogEntry Model

Each log record is represented using the `LogEntry` class.

A log entry contains information about an event:

- Log level
- User who performed the action
- Action description
- Additional details
- Time of occurrence

Example:

```text
Level: INFO

User: receptionist1

Action: Created Reservation

Details: Room 205 reserved for Guest 12
```

---

# 13.4 Log Levels

The system uses different log levels to classify events.

The `LogLevel` enum contains:

```text
INFO

WARNING

ERROR
```

## INFO

Used for normal successful operations.

Examples:

- Successful login
- Reservation created
- Payment completed

---

## WARNING

Used for situations that may require attention.

Examples:

- Failed login attempt
- Cancellation request
- Low availability warning

---

## ERROR

Used when an operation fails.

Examples:

- Invalid operation
- Failed payment
- Unauthorized access attempt

---

\newpage
# 13.5 Logging Workflow

The general logging process is:

```text
User performs an action

          |

          v

Manager processes request

          |

          v

Create LogEntry

          |

          v

Store in LogManager
```

For example, when a receptionist creates a reservation:

```text
Receptionist

      |

      v

ReservationEngine

      |

      v

LogManager

      |

      v

Reservation Created Log
```

---

# 13.6 Access to Logs

System logs are mainly available for administrative users.

The SuperAdmin can view complete system logs because this role has the highest access level.

This prevents normal users from accessing sensitive system activity information.

---

# 13.7 Benefits of the Logging System

The logging mechanism provides several advantages:

- Easier debugging during development.
- Better monitoring of system behavior.
- Tracking important business operations.
- Increased accountability for user actions.
- Support for future auditing features.

The centralized logging design keeps monitoring responsibilities separate from other system components and allows future improvements such as saving logs to files or databases.

\newpage

# 14. Testing and Sample Execution

Testing was performed to verify that the main features of the Hotel Management System work correctly and that invalid operations are handled properly.

The testing process focused on the most important workflows:

- User authentication
- Room management
- Reservation creation
- Check-in and check-out
- Payment processing
- Report generation
- Exception handling

---

# 14.1 Testing Approach

The system was tested using manual scenario-based testing.

For each feature:

1. A test case was created.
2. The required input was provided.
3. The system behavior was observed.
4. The result was compared with the expected behavior.

The main goal was to verify that the system follows the defined business rules.

---

\newpage
# 14.2 Login Testing

## Test Case: Successful Login

Input:

```text
Username:
admin

Password:
correct_password
```

Expected Result:

```text
Login successful

Open SuperAdmin menu
```

Result:

```text
Passed
```

---

## Test Case: Invalid Login

Input:

```text
Username:
admin

Password:
wrong_password
```

Expected Result:

```text
Display authentication error
```

Result:

```text
Passed
```

---

# 14.3 Room Management Testing

## Test Case: Adding a New Room

Operation:

```text
Hotel Manager adds a new Deluxe room
```

Expected Result:

```text
Room is added successfully

Room appears in available rooms list
```

Result:

```text
Passed
```

---

## Test Case: Searching Available Rooms

Input:

```text
Room Type:
Suite

Date Range:
Available period
```

Expected Result:

```text
Show matching available rooms
```

Result:

```text
Passed
```

---

# 14.4 Reservation Testing

## Test Case: Creating Reservation

Scenario:

```text
Guest selects an available room

Reservation dates are valid
```

Expected Result:

```text
Reservation created

Room status updated

Log entry created
```

Result:

```text
Passed
```

---

## Test Case: Double Booking Prevention

Scenario:

```text
Two guests attempt to reserve the same room
for overlapping dates
```

Expected Result:

```text
ReservationConflictException is thrown
```

Result:

```text
Passed
```

---

# 14.5 Check-In and Check-Out Testing

## Check-In Test

Input:

```text
Confirmed reservation
```

Expected Result:

```text
Reservation status changes to ACTIVE

Room status changes to OCCUPIED
```

Result:

```text
Passed
```

---

## Check-Out Test

Input:

```text
Active reservation
```

Expected Result:

```text
Invoice generated

Reservation completed

Room becomes AVAILABLE
```

Result:

```text
Passed
```

---

# 14.6 Financial Testing

## Test Case: Partial Payment

Scenario:

```text
Invoice amount:
10,000,000

First payment:
5,000,000
```

Expected Result:

```text
Remaining balance:
5,000,000
```

Result:

```text
Passed
```

---

## Test Case: Refund

Scenario:

```text
Reservation cancelled
```

Expected Result:

```text
Refund calculated

Invoice updated
```

Result:

```text
Passed
```

---

# 14.7 Report Testing

The following reports were tested:

| Report | Result |
|---|---|
| Room Status Report | Passed |
| Occupancy Report | Passed |
| Income Report | Passed |
| Guest History Report | Passed |
| Debtor Report | Passed |
| Popular Room Report | Passed |

The generated reports contained the expected information from the system data.

---

# 14.8 Exception Testing

Invalid operations were tested to verify that custom exceptions work correctly.

Examples:

| Operation | Expected Exception |
|---|---|
| Reserving unavailable room | RoomNotAvailableException |
| Invalid reservation dates | InvalidDateRangeException |
| Unknown user login | UserNotFoundException |
| Unauthorized operation | AccessDeniedException |

All tested exceptions were handled correctly.

---

# 14.9 Testing Result

The testing process showed that the main functionalities of the Hotel Management System work correctly.

The system successfully handles:

- Different user roles
- Reservation lifecycle
- Room availability checking
- Financial operations
- Report generation
- Invalid operations

The use of managers, custom exceptions, and separated components helped make testing each part of the system easier.

\newpage

# 15. Conclusion and Future Improvements

## 15.1 Conclusion

In this project, a console-based Hotel Management System was designed and implemented using Java.

The main goal of the project was to replace manual hotel operations with a software system capable of managing users, rooms, reservations, payments, services, and reports.

The implemented system provides:

- Multiple user roles with different access levels.
- Room management for different room types.
- Complete reservation lifecycle management.
- Check-in and check-out operations.
- Invoice generation and payment handling.
- Additional hotel service management.
- Report generation for administrators.
- Logging of important system activities.
- Exception handling for invalid operations.

The project was designed using object-oriented programming principles. Concepts such as:

- Inheritance
- Encapsulation
- Abstraction
- Polymorphism
- Interfaces
- Design patterns

were applied to create a modular and maintainable structure.

The use of separate manager classes helped separate business logic from data models and user interfaces. This made the system easier to understand, test, and extend.

---

# 15.2 Implemented Features Summary

The following features from the project requirements were implemented:

| Feature | Status |
|---|---|
| User authentication | Implemented |
| Role-based access control | Implemented |
| Room management | Implemented |
| Room availability search | Implemented |
| Reservation management | Implemented |
| Check-in / Check-out | Implemented |
| Invoice generation | Implemented |
| Payment management | Implemented |
| Refund handling | Implemented |
| Hotel service management | Implemented |
| Reports | Implemented |
| System logging | Implemented |
| Exception handling | Implemented |
| Waitlist notification system | Implemented |

---

# 15.3 Future Improvements

Although the current version satisfies the required functionality, several improvements can be added in future versions.

---

## Database Integration

Currently, the system stores information in memory during execution.

A future version can add database support using technologies such as:

- MySQL
- PostgreSQL
- SQLite

This would allow:

- Permanent data storage.
- Recovery after restarting the application.
- Handling larger amounts of information.

---

## Graphical User Interface

The current system uses a console interface.

A GUI version can improve usability by providing:

- Forms for entering information.
- Visual room availability.
- Better navigation.
- More user-friendly interaction.

Possible technologies:

- JavaFX
- Swing

---

## Web Application Version

The system can be extended into a web-based application.

Possible architecture:

```text
Frontend

    |

Backend API

    |

Database
```

This would allow guests and employees to access the system remotely.

---

## Automated Testing

More automated tests can be added using testing frameworks such as:

- JUnit

This would allow:

- Faster verification of changes.
- Automatic regression testing.
- Better code reliability.

---

## Improved Security

Future versions can improve security by adding:

- Password encryption.
- Session management.
- More detailed permission control.
- Audit logs stored permanently.

---

## Advanced Reporting

The reporting system can be improved by adding:

- Graphical charts.
- Export to PDF/Excel.
- Monthly and yearly analytics.
- Occupancy prediction.

---

# 15.4 Final Remarks

The Hotel Management System demonstrates the application of object-oriented programming concepts in a complete software project.

The final implementation provides a structured solution for managing hotel operations while keeping different responsibilities separated through proper software design.

The project also provides a strong foundation for future expansion into a full-scale hotel management application.