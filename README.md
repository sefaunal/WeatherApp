# Weather App
A weather forecasting web application with user authentication and role management. Users can view, save, and manage weather data, while admins can manage users and perform restricted operations.

<br>

## Features

- **Weather Data**: API data is fetched using jQuery, and the rest of the information is displayed via Thymeleaf.
  
- **User Registration**: Users can register with a profile photo saved to Firebase Storage. The photo URL and other details are stored in MySQL.
  
- **Weather Records**: On the Weather Records page, users can view their saved weather entries. These records can be sorted by various criteria (e.g., weather date, country), and pagination is enabled to limit listings to 7 items per page.
  
- **Roles**: The system includes four user roles with different permissions:
  
    - **Guest**: Users without an account can search and view up to 3-day weather forecasts.
      
    - **User**: Registered users can search, save weather information for the current day and delete these saved weather entry at any given moment.
      
    - **Admin**: Admins can manage users, ban/unban users, and delete user accounts.
      
    - **Banned**: Banned users cannot access the website or perform any operations.

<br>

## Technologies Used
- Java 17

- MySQL

- jQuery

- Thymeleaf

- Spring Boot 2

- Firebase Storage

<br>

## Configuration

### MySQL
This project uses MySQL as its database. You will need to have MySQL installed on your machine.
- Edit the `application.properties` file located at `/src/main/resources/` to match your MySQL credentials.
  
- Create the schema manually in MySQL Workbench (e.g., `weatherapp`).

- The required tables will be created automatically by Hibernate/JPA.
  
- On the first run, an admin account will be created automatically. The default credentials are:
  - **Email**: `weatherapp@admin.com`
  - **Password**: `12345678`

These credentials can be changed in `WeatherAppApplication.java`.

### Firebase Storage
WeatherApp uses Firebase for storing images like profile pictures & project photos.

To configure Firebase Storage:

- Create a new project from [Firebase Console.](https://console.firebase.google.com/)

- Go to Project Settings -> Service Accounts -> Firebase Admin SDK -> Generate new private key.

- Download the `Firebase.js` configuration file from the Firebase project.

- Replace the existing `Firebase.js` file located in `src/main/resources/static/js/LoginPage/Firebase.js` with the downloaded configuration file.

## Running the Project
- Once you’ve set up the schema and completed Firebase configuration, run the project from your IDE.
- Visit `http://localhost:8080/` in your browser to access the application.

<br>

## Screenshots

<img width="1840" alt="1" src="https://github.com/user-attachments/assets/636d1c24-072a-4eab-973b-09621bb633bb" />

<img width="1840" alt="2" src="https://github.com/user-attachments/assets/ad9438d1-6f2c-4abd-9a25-3d6e8d46c47a" />

<img width="1840" alt="3" src="https://github.com/user-attachments/assets/24a7d2c4-6bd1-4fcd-a441-82d9301c7375" />

<img width="1840" alt="4" src="https://github.com/user-attachments/assets/b30af3bb-43b9-4794-b15c-e88459638837" />

<img width="1840" alt="5" src="https://github.com/user-attachments/assets/1f7fea52-dbd2-4419-814f-13f726b74d47" />

<img width="1840" alt="6" src="https://github.com/user-attachments/assets/c3a3a877-306b-4703-96b9-77c4a1478b77" />

<img width="1840" alt="7" src="https://github.com/user-attachments/assets/4bd67e1b-c9c8-4085-8b85-4abbb4f831c1" />

<img width="1840" alt="8" src="https://github.com/user-attachments/assets/6a0bca68-9b9b-44ae-9fde-a233be65285e" />
