
DROP TABLE IF EXISTS Loan CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Address CASCADE;
DROP TABLE IF EXISTS Account CASCADE;
DROP TABLE IF EXISTS Role CASCADE;

CREATE TABLE Role (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);
insert into role (role_name) values ('Customer'), ('Manager');

CREATE TABLE Account (
    account_id SERIAL PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(20) NOT null,
    role_id INT REFERENCES role (role_id) ON DELETE SET NULL
);

CREATE TABLE Address (
    address_id SERIAL PRIMARY KEY,
    country VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(30) NOT NULL,
    street_num INT NOT NULL,
    zip VARCHAR (6) NOT NULL
);

CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    email VARCHAR(50) UNIQUE,
    phone_number VARCHAR (11),
    address_id INT REFERENCES Address (address_id) ON DELETE SET null,
    account_id INT references Account (account_id) on delete set NULL
);

CREATE TABLE Loan (
    loan_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    amount_requested DECIMAL NOT NULL,
    loan_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NULL,
    applied_date DATE DEFAULT CURRENT_DATE,
    approved_by INT REFERENCES Users(user_id) ON DELETE SET NULL,
    approved_date DATE,
    rejection_reason VARCHAR(100)
);


