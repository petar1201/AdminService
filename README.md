# AdminService
AdminService is a MicroService which was made as a Demo project for interview taskm for admins in some company, that is used to store/import data about employees, vacations, and used vacations, and this service is only accessible by admins.


For easier testing Docker files are also provided, and this service(applocation) can also be started on Docker. And there are instructions for that:


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

All of the endpoints are protected. So, when an admin wants to do any of this, they first need to access @POST http://localhost:8080/login and send their username and password as json form {"email":"value", "password":"value"}. If the credentials are good, the admin will be provided with a token that they can use to access any endpoint. They just need to add bearer token authorization to the header with the token they got from logging in.

## Author
petar3747@gmail.com
