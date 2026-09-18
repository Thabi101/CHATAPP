
PROG5121 Part 1 – Registration and Login Presentation Script

Good day. My name is Thabang Mohapi, and this presentation demonstrates my PROG5121 Part 1 registration and login application.

The purpose of this application is to allow a user to create an account securely and then use the same details to log into the system.

Registration Feature

I will first demonstrate the registration process.

The username is validated to make sure that it contains an underscore and is no more than five characters long. I implemented this validation because these are the requirements specified for the registration feature.

The password is also validated. It must contain at least eight characters, a capital letter, a number, and a special character. This helps ensure that the password meets the required complexity rules.

The cell phone number is checked using a regular expression. The regular expression verifies that the number uses the required international country code and follows the required format. I also included an attribution for the regular expression as required by the task.

If all the information is valid, the application displays the appropriate success messages and stores the registration details for the login process. If something is incorrect, the system gives the user an error message explaining what needs to be corrected.

Login Feature

Next, I will demonstrate the login feature.

The user enters the same username and password that were used during registration. The loginUser() method compares the entered details with the stored registration details.

An appropriate decision structure is used to determine whether the credentials are correct. If they match, the system displays a successful welcome message. If they do not match, it displays an error message asking the user to try again.

Unit Testing

I also created unit tests to verify that the main methods work correctly.

The tests check valid and invalid usernames, valid and invalid passwords, valid and invalid cell phone numbers, as well as successful and unsuccessful login attempts.

This is important because unit testing helps identify errors before the application is submitted.

Code Structure

The application uses a separate Login class containing methods for username checking, password complexity, cell phone validation, registration, login and login status.

I used meaningful variable names, methods and comments to keep the code readable and maintainable.


In conclusion, my application meets the main Part 1 requirements by providing registration, validation, authentication and unit testing.

The application is console-based, and the validation and decision structures ensure that incorrect information is handled appropriately.

Thank you.


