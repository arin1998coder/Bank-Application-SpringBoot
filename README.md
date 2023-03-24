# Bank-Application-SpringBoot

Bank Application

This is a simple Bank Application built using Java, Spring Boot and JPA. The application allows multiple users to create a joint bank account and perform transactions such as deposit and withdrawal.

Project Structure
The Bank Application consists of three Entity classes - User, Account, and Transaction. One account can have multiple users, and one account can have multiple transactions. Similarly, one user can perform multiple transactions.

The application has a UserController class that enables the creation of multiple users and generates a joint bank account for them. It also has a TransactionController that provides APIs to enable users to perform debit and credit operations on the account.

TransactionController APIs
The TransactionController has the following APIs:

Debit and Credit API - This API enables multiple users to perform debit and credit operations on a single account simultaneously. Users pass their response as a RequestDTO containing userId, amount, and transactionType, which can be DEBIT or CREDIT. The API returns a ResponseDTO containing txnId, userId, account ID, account balance, amount, and message.

Deposit API - This API enables a user to perform a deposit operation on their bank account. Users pass their response as a RequestDTO containing userId, amount. The API returns a ResponseDTO containing txnId, userId, account ID, account balance, amount, and message.

Withdraw API - This API enables a user to perform a withdrawal operation on their bank account. Users pass their response as a RequestDTO containing userId, amount.The API returns a ResponseDTO containing txnId, userId, account ID, account balance, amount, and message.

To handle the case of multiple users performing debit and credit operations simultaneously, the application uses a List<RequestDTO> requestDTOList. The user requests are processed in a FIFO manner, and only after the completion of a transaction, the next transaction can proceed.

Entity Classes
The Transaction entity class keeps records of all successful and failed transactions, the timestamp, the amount involved in the transaction, the user and the account involved. Users can easily view the state of their transactions (debit/credit).

The Account entity class keeps records of all account numbers, and the balance is dynamically updated as transactions occur. Multiple users can have the same account number.

Conclusion
This Bank Application provides a simple and efficient way for users to create a joint bank account and perform various transactions. It has been built using Java, Spring Boot, and JPA, making it easy to understand and modify.
