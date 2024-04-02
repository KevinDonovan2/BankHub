
# Table: account

CREATE TABLE account (
  account_number SERIAL PRIMARY KEY,
  customer_name VARCHAR(255) NOT NULL,
  customer_birthdate DATE NOT NULL,
  net_monthly_salary DOUBLE PRECISION NOT NULL,
  main_balance DOUBLE PRECISION NOT NULL,
  loans DOUBLE PRECISION,
  interest_on_loans DOUBLE PRECISION,
  decouvert_autorise BOOLEAN,
  credit_authorized DOUBLE PRECISION NULL)
);
ALTER TABLE account
DROP COLUMN credit_authorized,
DROP COLUMN interest_rate_7d,
DROP COLUMN interest_rate_after_7d;

ALTER TABLE account
ADD COLUMN loans DOUBLE PRECISION NOT NULL DEFAULT 0,
ADD COLUMN interest_on_loans DOUBLE PRECISION NOT NULL DEFAULT 0,
ADD CONSTRAINT check_age CHECK (DATE_PART('year', AGE(customer_birthdate)) >= 21);

#------------------------------------------------------------
# Table: operation

CREATE TABLE operation (
  id_operation SERIAL PRIMARY KEY,
  operation_type VARCHAR(255) NOT NULL,
  amount DOUBLE PRECISION NOT NULL,
  apply_date TIMESTAMP NOT NULL,
  register_date TIMESTAMP NOT NULL,
  account_number INTEGER NOT NULL,
  FOREIGN KEY (account_number) REFERENCES account(account_number)
);

#------------------------------------------------------------
# Table: category

CREATE TABLE category (
  id_category SERIAL PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL,
  descriptions TEXT NOT NULL
);

#------------------------------------------------------------
# Table: transactions

CREATE TABLE transactions (
  id_transaction SERIAL PRIMARY KEY,
  descriptions TEXT NOT NULL,
  id_operation INTEGER NOT NULL,
  id_category INTEGER NOT NULL,
  FOREIGN KEY (id_operation) REFERENCES operation(id_operation),
  FOREIGN KEY (id_category) REFERENCES category(id_category)
);

#------------------------------------------------------------
# Table: transfer

CREATE TABLE transfer (
  id_transfer SERIAL PRIMARY KEY,
  amount DOUBLE PRECISION NOT NULL,
  apply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  register_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  reason VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  account_number INTEGER NOT NULL,
  destinataire_account_number INTEGER NOT NULL,
  FOREIGN KEY (account_number) REFERENCES account(account_number),
  FOREIGN KEY (destinataire_account_number) REFERENCES account(account_number)
);

ALTER TABLE transfert RENAME TO transfer;
ALTER TABLE transfer RENAME COLUMN id_transfert TO id_transfer;
ALTER TABLE transfer ADD COLUMN destinataire_account_number INTEGER;
ALTER TABLE transfer ADD CONSTRAINT fk_transfer_destinataire_account
FOREIGN KEY (destinataire_account_number) REFERENCES account(account_number);

#------------------------------------------------------------
# Table: account_statement

CREATE TABLE account_statement (
  id_statement SERIAL PRIMARY KEY,
  date TIMESTAMP NOT NULL,
  reason VARCHAR(255) NOT NULL,
  debit_balance DOUBLE PRECISION NOT NULL,
  credit_balance DOUBLE PRECISION NOT NULL,
  account_number INTEGER NOT NULL,
  FOREIGN KEY (account_number) REFERENCES account(account_number)
);
#------------------------------------------------------------
CREATE TABLE interest_rates (
  id SERIAL PRIMARY KEY,
  interest_rate_7d DOUBLE PRECISION NOT NULL,
  interest_rate_after_7d DOUBLE PRECISION NOT NULL
);




