# Multi-Tenancy Hostel Management System

The Multi-Tenancy Hostel Management System is a robust, web-based application designed to simplify and optimize the management of private hostels within educational institutions. Our system offers a seamless experience by efficiently handling hostel allocation, student registration, and staff management tasks. This comprehensive solution empowers administrators, hostel staff, and students to carry out their responsibilities with ease, ensuring a streamlined and hassle-free hostel management process.

## Prerequisites

Before you begin, ensure you have the following prerequisites installed:

- Java 17
- Spring 6
- Spring Boot 3
- Spring Security
- MySQL database
- Gradle Groovy

## Problem Statement

In educational institutions, the management of hostels is often plagued by inefficiencies and complexities. Manual processes for hostel allocation, student registration, and staff management can lead to errors, delays, and administrative challenges. Without a centralized and user-friendly system, administrators, hostel staff, and students face difficulties in coordinating and executing their respective tasks, which can hinder the smooth operation of hostel facilities and lead to dissatisfaction among stakeholders.

The need for a solution arises from the following problems:

1. **Inefficient Hostel Allocation:** The current manual system for allocating hostel rooms is time-consuming and prone to errors, resulting in suboptimal room assignments and dissatisfaction among students.

2. **Booking Inconvenience:** Often, learners waste precious time looking for hostels, especially during exams, which reduces study time and can lead to subpar academic performance.

3. **Funds Embezzlement:** There have been issues in recent years where guardians fell victim to embezzlement from their wards.

4. **Tedious Student Registration:** The process of registering students for hostel accommodation is cumbersome, often requiring excessive paperwork and administrative effort.

5. **Lack of Transparency:** Without a centralized system, there is a lack of transparency in the allocation and management of hostel resources, leading to disputes and confusion among stakeholders.

The Multi-Tenancy Hostel Management System seeks to address these challenges by providing an integrated and user-friendly platform that automates key processes, enhances transparency, and ensures a smooth and efficient hostel management experience for all stakeholders.

## Table of Contents

- [Dependencies](#dependencies)
- [Installation](#installation)
- [Database Configuration](#database-configuration)
- [JSON Web Token Configuration (JWT)](#json-web-token-configuration-jwt)
- [Mail Configuration](#mail-configuration)
- [Usage/Examples](#usageexamples)
- [Key Highlights / Uniqueness](#key-highlights--uniqueness)
- [Challenging Moments](#challenging-moments)
- [Further Improvements](#further-improvements)
- [Special Gratitude](#special-gratitude)
- [Author](#author)

## Dependencies

The following dependencies are required:

- Spring Web
- Spring Data Jpa
- Spring WebFlux
- Project Lombok
- Spring Validation
- Http Client
- Spring Doc & Open API
- Java Mail Service
- Spring Security
- JSON Web Token (JWT)
- MySQL
- Spring Boot Dev Tools

## Installation

1. Clone the repository to your local machine: `git clone https://github.com/moseshunsu/hngx-stage-two`
2. Build the project.
3. Configure the application.

The configuration for this API is stored in the `application.properties` file. To configure the API or make changes to its behavior, you can edit this file.

## Database Configuration

```properties
spring.datasource.url=your-database-url
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## JSON Web Token Configuration (JWT)

```properties
application.security.jwt.secret-key=
application.security.jwt.expiration=
application.security.jwt.refresh-token.expiration=
```

## Mail Configuration

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email
spring.mail.password=google-given-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Usage/Examples

This project has a well-documented OpenAPI (Swagger) interface, which can be accessed [here](https://multi-tenancy-hostel-software-production.up.railway.app/swagger-ui/index.html#/) or [here](https://multi-tenant-hostel-application.onrender.com/swagger-ui/index.html#/). Note that when using the Swagger documentation, make sure select to the server which corresponds to the domain name.

## Key Highlights / Uniqueness

- This software prioritizes security by requiring user email verification before allowing login.
- Special features, such as password reset for forgotten passwords and password changes for authenticated users, have been implemented.
- Emails are generated upon successful hostel booking.
- Due to the complexity of multi-tenant applications, unique codes have been generated to handle complex routing efficiently.
- Tight mappings have been minimized through the use of Java 8 streams and lambda expressions.
- The software enforces strong password verification, including a minimum length of 8 characters, a maximum length of 16 characters, and the inclusion of special characters.
- Users can easily search for registered hostels in their respective schools and book rooms directly from the site.
- This software is built on Java 8 features, utilizing streams and lambda expressions.
- Sample test cases for Quality Assurances as the software was built based on best practices
- Possible Exceptions that may arise has been catered for in the Application Exception Handler Class

## Challenging Moments

- Building this application required analytical skills similar to those needed for platforms like Jumia or Udemy, due to complex routing.
- Implementing the logic that allows hostel managers to approve bookings of occupants upon successful payment was a challenging task.

## Further Improvements

The project could benefit from a dedicated service for managing full hostel staffs.

## Special Gratitude

- [@i-Africa-Prudential-Plc](https://www.linkedin.com/company/africa-prudential-plc/mycompany/)
- [@i-Academy.org](https://www.linkedin.com/company/iacademybyap/)
- [@Obong](https://www.linkedin.com/in/obong-idiong-6a113829/)
- [@Ivy](https://www.linkedin.com/in/ivyikpemembakwem/)
- [@Joy](https://www.linkedin.com/in/joy-amuda/)
- [@Keren](https://www.linkedin.com/in/keren-otiono-337290a9/)

## Author

- [@moseshunsu-linkedin](https://www.linkedin.com/in/moses-hunsu-94b86a169/)
- [@moseshunsu-git](https://github.com/moseshunsu)
