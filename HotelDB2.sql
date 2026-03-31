show databases;

create database vhoteldb_task2;

use vhoteldb_task2;

CREATE TABLE rooms_table (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_number INT NOT NULL UNIQUE,
    room_type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE customers_table (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    address TEXT
);

CREATE TABLE bookings_table (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    room_id INT,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    
    FOREIGN KEY (customer_id) REFERENCES customers_table(customer_id)
        ON DELETE CASCADE,
        
    FOREIGN KEY (room_id) REFERENCES rooms_table(room_id)
        ON DELETE CASCADE
);

drop table bookings_table;

CREATE TABLE staffs_table (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    contact VARCHAR(15),
    salary DECIMAL(10,2) NOT NULL
);


INSERT INTO rooms_table (room_id, room_number, room_type, price, status) VALUES
(1, '101', 'Single', 1500, 'Available'),
(2, '102', 'Double', 2500, 'Available'),
(3, '201', 'Deluxe', 4000, 'Available'),
(4, '301', 'Suite', 7000, 'Available'),
(5, '302', 'Suite', 7500, 'Available');

INSERT INTO customers_table (customer_id, name, email, phone, address) VALUES
(1, 'Rahul Patil', 'rahul@gmail.com', '9876543210', 'Kolhapur'),
(2, 'Sneha Jadhav', 'sneha@gmail.com', '9123456780', 'Pune'),
(3, 'Amit Kulkarni', 'amit@gmail.com', '9988776655', 'Mumbai'),
(4, 'Priya Deshmukh', 'priya@gmail.com', '9090909090', 'Satara'),
(5, 'Rohan Shinde', 'rohan@gmail.com', '9012345678', 'Sangli');


INSERT INTO bookings_table (booking_id, customer_id, room_id, check_in, check_out, total_amount, status) VALUES
(1, 1, 1, '2026-02-20', '2026-02-22', 3000, 'CONFIRMED'),
(2, 2, 2, '2026-02-25', '2026-02-28', 7500, 'PENDING'),
(3, 3, 3, '2026-03-01', '2026-03-04', 12000, 'CANCELLED');

INSERT INTO staffs_table(staff_id, name, role, contact, salary) VALUES
(1, 'Anil Sharma', 'Manager', '9876500000', 50000),
(2, 'Kavita More', 'Receptionist', '9876511111', 20000),
(3, 'Suresh Patil', 'Housekeeping', '9876522222', 18000);

select *from rooms_table;

SET FOREIGN_KEY_CHECKS = 0;

truncate rooms_table;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE rooms_table AUTO_INCREMENT = 1;

select *from customers_table;

SET FOREIGN_KEY_CHECKS = 0;

truncate customers_table;

SET FOREIGN_KEY_CHECKS = 1;

select *from users;

select *from bookings_table;

SET FOREIGN_KEY_CHECKS = 0;

truncate bookings_table;

SET FOREIGN_KEY_CHECKS = 1;

select *from staffs_table;

show tables;

create table Users
(
	id int AUTO_INCREMENT PRIMARY KEY,
    username varchar(50),
    password varchar(100),
    role varchar(40)
);

ALTER TABLE users ADD CONSTRAINT unique_username UNIQUE (username);

select *from Users;




DELETE FROM users WHERE id = 1;

drop table users;

SELECT username, role FROM users;

select *from rooms_table;
    
select booking_id, status, total_amount from bookings_table;

SELECT SUM(total_amount) 
FROM bookings_table
WHERE check_in = '2026-04-10'
AND status = 'CONFIRMED';

show tables;

select *from Invoice;

desc invoice;

select *from bookings_table;

select *from users;

select *from customers_table;

truncate users;

select *from booking;

select * from rooms_table;

DELETE FROM booking WHERE room_id = 3;

SET FOREIGN_KEY_CHECKS = 0;

truncate rooms_table;

SET FOREIGN_KEY_CHECKS = 1;

select *from customers_table;

select *from bookings_table;

select *from staffs_table;

select *from users;

select *from booking;

SELECT username, role FROM users WHERE username = 'ADMIN';

SELECT username, role FROM users WHERE username = 'MANAGER';

SELECT username, role FROM users WHERE username = 'ADMIN';