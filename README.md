# Android Application README

## Project Overview
This Android application is part of a digital wallet banking system. It connects to the backend service which the project my in the wallet-api repository to manage user interactions such as logging in, creating cards, performing financial transactions, and viewing transaction history.

The primary goals of this application include:
- Simplifying personal finance management.
- Providing a user-friendly interface for card and transaction operations.
- Enabling secure and efficient money transfers and payments.

## Features
- **User Authentication**: Login and registration functionalities.
- **Card Management**: Add, view, and manage virtual cards.
- **Transaction Management**:
  - View transaction history.
  - Perform money transfers, bill payments, and deposits/withdrawals.
- **User Profile**: View user details such as email, number of cards, and transaction counts.
- **Material Design**: Clean and modern UI design for enhanced user experience.

## Technologies Used
- **Programming Language**: Kotlin
- **UI Design**: XML with Material Design principles
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit for API communication
- **State Management**:
  - SharedPreferences for lightweight data storage (e.g., rememberMe retrieval)
  - ViewBinding for efficient view handling
- **Navigation**:
  - Navigation Component for fragment transitions
- **RecyclerView**: To display lists of cards and transactions

## Endpoints Used
This application communicates with a backend system using the following endpoints:
- **Authentication**:
  - `POST /auth/login`: Login with email and password.
  - `POST /auth/register`: Register a new user.
- **User Management**:
  - `GET /user/{userID}`: Get user details.
  - `POST /user/changePassword`: Change user password.
- **Card Operations**:
  - `POST /card/createCard`: Create a new card.
  - `POST /card/getCard`: Fetch user cards.
- **Transactions**:
  - `POST /transaction/moneyTransfer`: Transfer money.
  - `GET /transaction/getTransactions/{userID}`: Fetch transaction history.

## Screenshots
Below at the end of the readme are some screenshots of the application.

## Setup and Installation
### Prerequisites
- Android Studio installed (latest version recommended).
- Internet connection for API communication.

### Steps to Run the Application
1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd <project_folder>
   ```

2. Open the project in Android Studio.

3. I used Ngrok to setup the API. Ngrok makes your computer as server and provides URL. It helps us to run the API on the computer without any host.

4. Build and run the application on an emulator or a physical device.

## Future Improvements
- Implementing push notifications for transaction updates.
- Adding biometric authentication for enhanced security.
- Supporting multiple languages for global accessibility.

![1](https://github.com/user-attachments/assets/885b5f4f-6d5a-4700-85c3-f79de0c615e4)
![2](https://github.com/user-attachments/assets/fc2e570e-2d0e-4fc8-88bb-ce866dcf3fc5)
![3](https://github.com/user-attachments/assets/1bb52b24-862b-47e2-9d0f-db4edc4509fb)
![4](https://github.com/user-attachments/assets/0a07a5df-3668-48f3-b19c-bdc430b1b744)
![5](https://github.com/user-attachments/assets/697edd54-e057-4fbf-a217-f38452386cdd)
![6](https://github.com/user-attachments/assets/7cf27cea-1f8f-4052-97cc-f73d8181fc20)
![7](https://github.com/user-attachments/assets/3e09c605-109f-41bb-bd93-2eb0e65b2c63)
![8](https://github.com/user-attachments/assets/6528c806-d4e3-4d37-abdd-d114b1f5bcd5)

