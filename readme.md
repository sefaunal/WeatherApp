# Weather-App
## Features
* API Data are being fetched with jQuery
* Rest of the Information are displayed via Thymeleaf.
* On Register Page - Profile Photo are being saved to Firebase Storage, Photo's URL adress & rest of the information are then saved to the MySQL Database.
* Requests are being sent to the backend via Thymeleaf with one exception on Register Page that is jQuery.
* On Weather Records Page (where users can see their saved weather infos), saved weather entries can be sorted by couple of things (Like weather date, country etc.) and listed. Pagination added to limit the items that listed on 1 page to 7.

#### Roles - Admin, Guest, User, Banned
###### A Guest user which has no account in the database
* Guests can see and search a city's weather condition. (Forecast can only be seen up to 3 days due to my API account being free)
###### User account which has an entry in the database with 'USER' role
* Users can see and search a city's weather condition.
* Users can save or delete those weather information. (Only the current day info can be saved).
###### Admin account which has an entry in the database with 'ADMIN' role
* Admins can see and search a city's weather condition.
* Admins can ban or unban a user.
* Admins can delete a user.
###### Restricted account which has an entry in the database with 'BANNED' role
* Banned users can no longer access to the website nor can do any operations.
 
### Features - To Be Added.
* Profile page for users where they can edit their information
* Log page for admins to see the activity.

## How To Run The Project
* This project uses MySQL as its database so you will need to have it installed on your PC
* The part below of application.properties (can be found on /src/main/resources/) should be edited with an IDE or text editor to match your MySQL credentials.
* You will also have to create the Schema from MySQL Workbench manually (In this case it should be "weatherapp").

![image](https://user-images.githubusercontent.com/83312431/179203585-af53a397-5eba-4004-bef2-8656afc86656.png)

* Required tables will be created automatically by Hibernate/JPA Technology
* On the first run of this project an admin account will be created automatically since there is no user on the database. The credentials are as follows: (Credentials can be changed via editing CvmakerApplication.java)
  - Mail:     sefa@admin.com
  - Password: 123456

#### Run With IDE (e.g. IntelliJ)
If you have created the schema you are good to go. Run the project from IDE and type http://localhost:8080/ to your browser once the application is up and running.

## Screenshots
![2022-08-07 19 31 57](https://user-images.githubusercontent.com/83312431/183301248-3f852970-d814-4a18-baf7-eb16690858d3.png)

![2022-07-16 01 08 12](https://user-images.githubusercontent.com/83312431/179317547-90eebcd0-7019-4545-aad3-5969b4c4daf2.png)

![2022-07-15 13 26 50](https://user-images.githubusercontent.com/83312431/179205624-f1cdd0ed-c424-4f9b-8247-d64ba084f66d.png)

![2022-07-15 13 27 02](https://user-images.githubusercontent.com/83312431/179205628-4e924b5a-aab7-4e30-b4e7-95dcf5eb9db0.png)

![2022-07-15 13 28 47](https://user-images.githubusercontent.com/83312431/179205898-015eee48-058e-43a5-966e-bd5e02140f3e.png)

![2022-07-15 13 30 14](https://user-images.githubusercontent.com/83312431/179206153-59a8997a-8801-46aa-a45c-3a40c1752774.png)

![2022-07-15 13 31 45](https://user-images.githubusercontent.com/83312431/179206412-77e76dbc-b7e7-4b30-a945-3fb63554766a.png)

