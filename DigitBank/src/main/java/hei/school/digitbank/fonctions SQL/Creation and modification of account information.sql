-- F1
-- Creation of an account
CREATE TABLE Accounts (
    AccountNumber INT PRIMARY KEY,
    CustomerName VARCHAR(255),
    CustomerBirthdate DATE,
    NetMonthlySalary DECIMAL(10, 2),
    MainBalance DECIMAL(10, 2),
    Loans DECIMAL(10, 2),
    InterestOnLoans DECIMAL(10, 2),
    DecouvertAutorise BOOLEAN,
    CreditAuthorized DECIMAL(10, 2)
);

-- Modification of account information
ALTER TABLE Accounts
ADD COLUMN DecouvertAutorise BOOLEAN,
ADD COLUMN CreditAuthorized DECIMAL(10, 2);
