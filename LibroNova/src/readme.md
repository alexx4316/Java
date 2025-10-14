# LibroNova - Library Management System

**LibroNova** is an internal management application for a network of libraries. It allows you to manage books, members, users, and loans with role control and business validations. The system includes CRUD functionalities, business validations, loan and return management, as well as report generation and activity logging.

## Table of Contents

- [Project Requirements](#project-requirements)
- [Project Structure](#project-structure)
- [Main Features](#main-features)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration Files](#configuration-files)
- [Unit Tests](#unit-tests)
- [Technologies Used](#technologies-used)
- [License](#license)
- [Screenshots](#screenshots)

## Project Requirements

This project is designed to meet the following functional and technical requirements:

1. **Functional Requirements**:
    - Management of books, members, users, and loans.
    - Business validations such as:
        - Unique ISBN.
        - Available stock.
        - Active members.
    - Exception handling with user messages and error logs.
    - Exporting reports in CSV format (book catalog, overdue loans).

2. **Technical Requirements**:
    - Use of JDBC and PreparedStatement for data persistence.
    - Implementation of transactions for critical operations (loans and returns).
    - User interface through menus with `JOptionPane`.
    - Configuration of connection parameters in a `config.properties` file.

---

## Project Structure

The project folder structure is organized as follows:

La estructura de carpetas del proyecto está organizada de la siguiente manera:

LibroNova/

├── app/ # Contains the main class and system execution.

│
├── controller/ # Controllers that manage business logic.

│
├── dao/ # Database access (Data Access Objects).

│
├── errors/ # Custom exceptions.

│
├── logs/ # Log files (e.g., app.log).

│
├── models/ # Data models (Books, Members, Loans, etc.).

│
├── resources/ #  Archivos de configuración y recursos estáticos (ej. config.properties).

│
├── service/ # Business logic related to models (books, users, etc.).

│
├── util/ # General utilities (e.g., validators).

│
├── view/ # User interface views (menus with JOptionPane).

│
└── README.md # This file.


### Descripción de las Carpetas:

- **app**: Contiene la clase principal que ejecuta la aplicación.
- **controller**: Gestiona la interacción entre la vista y el modelo. Los controladores contienen la lógica de negocio.
- **dao**: Maneja la persistencia de datos mediante JDBC, con `PreparedStatement` para evitar inyecciones SQL.
- **errors**: Define excepciones personalizadas como `BadRequestException`, `NotFoundException`, etc.
- **logs**: Almacena los archivos de log, como el registro de actividades y errores (`app.log`).
- **models**: Representa las entidades del sistema, como `Libro`, `Usuario`, `Socio`, `Préstamo`.
- **resources**: Contiene archivos de configuración, como `config.properties`, para parámetros de conexión y otros.
- **service**: Contiene la lógica de negocio que maneja la gestión de libros, usuarios, socios, etc.
- **util**: Proporciona clases utilitarias, como validadores de datos o herramientas para el cifrado de contraseñas.
- **view**: Contiene la lógica de la interfaz de usuario, principalmente los menús que se presentan con `JOptionPane`.


### Folder Description:

- **app**: Contains the main class that runs the application.
- **controller**: Manages the interaction between the view and the model. Controllers contain business logic.
- **dao**: Handles data persistence through JDBC, with `PreparedStatement` to prevent SQL injection.
- **errors**: Defines custom exceptions such as `BadRequestException`, `NotFoundException`, etc.
- **logs**: Stores log files, such as activity and error logs (`app.log`).
- **models**: Represents the system entities, such as `Book`, `User`, `Member`, `Loan`.
- **resources**: Contains configuration files, like `config.properties`, for connection parameters and other settings.
- **service**: Contains business logic that manages books, users, members, etc.
- **util**: Provides utility classes, such as data validators or password encryption tools.
- **view**: Contains the user interface logic, mainly the menus displayed with `JOptionPane`.

---

## Main Features

### 1. **Book Management**
- CRUD operations to add, list, update, and delete books.
- Validation of unique ISBN.
- Verification of available stock for loans.

### 2. **Member Management**
- CRUD operations to add, list, update, and delete members.
- Validation of unique email to prevent duplicates.

### 3. **User Management**
- Registration of new users.
- Authentication using email and password.
- Role-based control for different permissions.

### 4. **Loans and Returns**
- Registering book loans.
- Returning books with overdue fees.
- Managing active and overdue loans.

### 5. **User Interface**
- Interactive menus with `JOptionPane` for navigation.
- User messages for creating, deleting, or error operations.
- Exportable reports to CSV.

### 6. **Validations**
- Unique ISBN.
- Available stock for loans.
- Active member status for loans.

---

## Installation

To install and run the project, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/alexx4316/LibroNova.git


##  **Uso**

- Main Menu: Upon running the system, a menu is presented where the user can choose to manage books, members, users, or loans.

- Book Management: Allows adding, updating, and deleting books, as well as viewing the book catalog.

- Member Management: Allows adding, updating, deleting, and listing members.

- Loan Management: Allows managing book loans to members and recording returns, including overdue fee calculations.

---


