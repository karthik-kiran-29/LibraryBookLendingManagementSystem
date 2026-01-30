
CREATE DATABASE library;

USE library;

CREATE TABLE book_tbl (
    Book_id varchar(10) PRIMARY KEY,
    Title varchar(120),
    Author Varchar(80),
    Total_Copies int,
    Available_Copies int
);

INSERT INTO book_tbl VALUES("BK101","Introduction to Algorithms","Thomas H.Cormen",10,4),
("BK102","Effective Java","Joshua Bloush",5,0),
("BK103","Clean Code","Robert C Martin",7,2);

CREATE TABLE issue_tbl (
    Issue_id int PRIMARY KEY,
    Book_id Varchar(10) ,
    Student_id Varchar(12),
    Student_Name Varchar(100),
    Issue_Date Date,
    Return_Date Date,

    CONSTRAINT fk_book
        FOREIGN KEY (Book_id)
        REFERENCES book_tbl (Book_id)
);

INSERT INTO issue_tbl VALUES
(30001,"BK101","ST2001","Anita Sharma","2025-01-10",NULL),
(30002,"BK103","ST2003","Rahul Mehta","2025-01-15","2025-01-20");
