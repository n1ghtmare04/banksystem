# banksystem
This is a simple API which simulates online bank operations including basic transactions and security done by hashing passwords. It uses cookies to store the user's Id to enable user's interaction with the operations.

## Features
  HomePage:  "/" (GET) no arguments
    
  Login page:  "/log-in" (POST), arguments are {"userLogin": String, "userPassword": String} in body section
    
  Registration page:  "/sign-up" (POST), arguments are {"userName": String, "email": String, "userLogin": String, "userPassword": String, "yearsCountry": int, "employed": int, "totalLoans": Long, "userBalance": Long, "isAdmin": int, "contactNumber": String} in body section
    
  Page showing list of banks:  "/main-page/list-banks" (GET) no arguments
    
  History of past loans:  "/main-page/loans-history" (GET) no arguments
    
  Bank details:  "/main-page/bank/{bankId}" (GET) no arguments<br><br>
  Pages for cash operations (POST):
  
    "/main-page/bank/{bankId}/transfer" - transferring cash from one user to the other, arguments are {"clientId": Long, "transferSum": Long} in body section
  
    "/main-page/bank/{bankId}/deposit"  - increases the balance of the user by specified amount, argument is "depositSum" in Params section
  
    "/main-page/bank/{bankId}/withdraw" - decreases the balance of the user, argument is "withdrawSum" in Params section
  
    "/main-page/bank/{bankId}/loans"    - checks whether user's profile meets requirements for loan and increases user's balance, argument is "loansSum" in Params section
     
     
  User details: "/main-page/user/{userId}" (GET) no arguments
  
  Page where user can change password: "/main-page/change-password" (POST), arguments are {"userLogin": String, "oldUserPassword": String, "newUserPassword": String} in body section.  
  
  Page where user can search for a bank using its name: "/main-page/search" (GET) no arguments  
  
  Admin operations (POST):  
    
    "/main-page/admin-delete", argument is "clientLogin" in Params section  
      
    "/main-page/admin-change", arguments are "clientLogin" in Params section and {"userName": String, "email": String, "userLogin": String, "userPassword": String, "yearsCountry": int, "employed": int, "totalLoans": Long, "userBalance": Long, "isAdmin": int, "contactNumber": String} in body section    
      
    "/main-page/admin-create", argument is "clientLogin" in Params section  

## Testing
  
  I recommend using **Postman** which has many useful features for testing the logic behind websites, it can be downloaded here: https://www.postman.com/downloads/.
  
Instructions (**Postman**):  
  Firstly, type http://localhost:8080 (GET), this page will show a short message.  
    
  To access registration page just type /sign-up after http://localhost:8080 (POST). In the body section type {"userName": String, "email": String, "userLogin": String, "userPassword": String, "yearsCountry": int, "employed": int, "totalLoans": Long, "userBalance": Long, "isAdmin": int, "contactNumber": String}, contact number must consist of exactly 11 digits, password must contain at least 1 lowercase, 1 uppercase, 1 digit, 1 special symbol and be 8 characters long. To be able to use functionality of "/main-page/admin-create" or "change" or "delete" the value in isAdmin should be 1. It is recommended to go to login page afterwards so that user's id will be saved in cookies. 
    
  To access login page type /log-in (POST). In the body section type {"userLogin": String, "userPassword": String} according to the values made in registration.  
    
  Other pages can be accessed in the same way using details provided in section "**Features**".
