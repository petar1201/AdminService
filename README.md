# AdminService
AdminService is a MicroService for admins in some company, that is used to store/import data about employees, vacations, and used vacations, and this service is only accessible by admins.

## Getting Started
To use this service, you need to set up a PostgreSQL database with the name VacationTracker. The port should be 5432, the user should be "postgres", and the password is "admin". Once the database is set up, the project is ready to be started.

## Starting the Application
This Service works on localhost:8080 and it is written in SpringBoot. You can run the application just by starting it in whichever working environment you are using. All of system properties are already set, since project is using JDK17 it is needed to have it installed. For full functionalities of the application EmployeeService should also be started(localhost:8082).

For easier testing Docker files are also provided, and this service(applocation) can also be started on Docker. And there are instructions for that:
1. As i already said it is nedeed to make database in a way described in previous section.
2. Here is the link ${link} to download target folder with jar file needed to start docker, add target folder to your project folder (git refuesed to push files becaouse of their size), same needs to be done in EmployeeService and link for that is provided in EmployeeService/readme.md, if target folder and jar in it are available to you without downloading it in here, you do not need to download it in this step and can just skip it
3. Projects AdminService and EmployeeService need to be in a same directory in order to simplify running it on Docker(this can also be resolved for example with Environment Variables but in terms of making running it on Docker as simple as it can be, just put both projects in a same directory)
4. Download and open DockerDesktop app
5. Navigate to AdminService(docker-compose.yml is here) and run this command: docker-compose up
6. That should create Docker Images and Container
7. Go to your DockerDesktop app where you can now see both container and images and you can start or stop services.
8. That should be all and you shall go to for example Postman and use the application with endpoints described


## Default Admin
When booting the project, the system creates a default admin with credentials "petar@rbt.rs" and password "admin", which can be used for next functionalities.

## Samples
Sample files are provided in resources/samples and they are used to import data

## Endpoints
Services endpoints are:

1. /api/admin/employee/new - Adds a new employee with form parameters email and password.
2. /api/admin/employee/import - Imports employee data from a CSV file. This endpoint has a form parameter "path", which is the name of the CSV file from which to load.
3. /api/admin/employee/delete - Deletes an employee with form parameter of email.
4. /api/admin/new - Creates a new admin with form parameters username and password.
5. /api/admin/delete - Deletes an admin with form parameter username.
6. /api/admin/vacations/new - Adds single vacation info with form parameters email of the employee for which data is adding, form param year indicating for which year is adding, and form param days indicating amount of days to be given to an employee.
7. /api/admin/vacations/import - Imports the data described in the previous endpoint from a CSV file, which name is given as form parameter path.
8. /api/admin/usedVacations/new - Adds a single record of used vacation days with form param email representing for which employee used days are, form param start date, and form param end date(Strings in format dd yy mmmm)..
9. /api/admin/usedVacation/import - Imports data described in the previous endpoint with form param path, which is the name of the CSV file.

All of the endpoints are protected. So, when an admin wants to do any of this, they first need to access @POST http://localhost:8080/login and send their email and password. If the credentials are good, the admin will be provided with a token that they can use to access any endpoint. They just need to add bearer token authorization to the header with the token they got from logging in.

## Author
petar3747@gmail.com