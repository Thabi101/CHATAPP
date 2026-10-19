# PROG5121 ChatAPP Part 2 — Video Presentation Script

**Introduction**

Good day. My name is Thabang Mohapi, and today I will be presenting Part 2 of my PROG5121 programming project, which focuses on developing the messaging features for the QuickChat application.

The purpose of this application is to allow users to register, log in, and send messages while applying Java programming concepts such as classes, methods, loops, conditional statements, and data validation.

**Application and Login**

When the application starts, the user must successfully log in before accessing the messaging menu. Once authenticated, the application displays the welcome message, “Welcome to QuickChat.”

The menu provides three options: send messages, show recently sent messages, or quit the application. The recently sent messages feature displays “Coming Soon” because it is still under development. The application continues running until the user chooses to quit.

**Sending and Validating Messages**

Before sending messages, the user specifies how many messages they want to enter. The application then allows them to process that number of messages.

Each message contains a unique ten-digit message ID, a recipient's cellphone number, the message text, and a message hash.

Validation is used to ensure that the message meets the application's requirements. The recipient's cellphone number must follow the required international format, and the message must not exceed the permitted character limit.

The message hash is generated using the message ID, the message number, and the first and last words of the message. This helps identify each message according to the assignment's requirements.

**Sending, Storing and Disregarding Messages**

After creating a message, the user can choose to send it, store it, or disregard it.

When a message is sent, the application displays its message ID, message hash, recipient, and message text. Stored messages are saved in JSON format using the Gson library, allowing message information to be written to a file. Disregarded messages are not counted as sent messages.

The application also keeps track of the total number of messages sent during the session.

**Testing and Conclusion**

Testing is important to confirm that the application validates user input correctly, handles messages as expected, and performs its intended functions. Unit tests can be used to check message validation, message hashing, and login functionality.

The project can also use GitHub Actions to automate testing whenever code changes are pushed to the repository.

In conclusion, this project demonstrates how Java programming concepts can be used to develop a simple messaging application with login authentication, input validation, message handling, and JSON storage.

Thank you for listening to my presentation.
