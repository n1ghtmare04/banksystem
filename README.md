# banksystem
This is a simple API which simulates online bank operations including basic transactions and security done by hashing passwords. It uses cookies to store the user's Id to enable user's interaction with the operations.

# Features
  HomePage: "/"
  
  Login page: "/log-in"
  
  Registration page: "/sign-up"
  
  Page showing list of banks: "/main-page/list-banks"
  
  History of past loans: "/main-page/loans-history"
  
  Bank details: "/main-page/bank/{bankId}"

  
  Pages for cash operations:
  
  "/main-page/bank/{bankId}/transfer" - transferring cash from one user to the other
  
  "/main-page/bank/{bankId}/deposit"  - increases the balance of the user by specified amount
  
  "/main-page/bank/{bankId}/withdraw" - decreases the balance of the user
  
  "/main-page/bank/{bankId}/loans"    - checks whether user's profile meets requirements for loan and increases user's balance
  
  
  User details: "/main-page/user/{userId}"
  
  Page where user can change password: "/main-page/change-password"
  
  Page where user can search for a bank using its name: "/main-page/search"
  
  Admin operations: 
  "/main-page/admin-delete"
  "/main-page/admin-change"
  "/main-page/admin-create"
