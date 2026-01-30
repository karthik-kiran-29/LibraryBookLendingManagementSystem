package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.bean.Book;
import com.library.util.DBUtil;
import com.library.util.ValidationException;

public class BookDAO {
	Connection con = DBUtil.getConnection();
	
	public Book findBook(String bookID) {
		if(!bookID.isBlank()) {
			try {
				PreparedStatement ps = con.prepareStatement("Select * from book_tbl where Book_id= ?");
				
				ps.setString(1, bookID);
				
				ResultSet rs = ps.executeQuery();
				
				rs.next();
				
				Book book = new Book();
				
				book.setBookID(bookID);
				book.setTitle(rs.getString("Title"));
				book.setAuthor(rs.getString("Author"));
				book.setTotalCopies(rs.getInt("Total_Copies"));
				book.setAvailableCopies(rs.getInt("Available_Copies"));
				
				System.out.println(book.toString());
				
				return book;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} return null;
	}
	
	public List<Book> viewAllBooks(){
		try {
		Statement smt = con.createStatement();
		
		String query = "SELECT * FROM book_tbl";
		
		ResultSet rs = smt.executeQuery(query);
		
		List<Book> books = new ArrayList<>();
		
		while(rs.next()) {
			Book book = new Book();
			
			book.setBookID(rs.getString("book_Id"));
			book.setTitle(rs.getString("Title"));
			book.setAuthor(rs.getString("Author"));
			book.setTotalCopies(rs.getInt("Total_Copies"));
			book.setTotalCopies(rs.getInt("Available_Copies"));
			
			
			books.add(book);
			
		}
		
		if(books.isEmpty()) {
			return null;
		}
		
		return books;
		}catch(Exception e) {
			e.getStackTrace();
			return null;
		}
	}
	
	public boolean insertBook(Book book) {
		try {
			if(book.getBookID().isBlank() || book.getAuthor().isBlank() || book.getTitle().isBlank()) {
				throw new ValidationException();
			}
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO book_tbl  VALUE(?,?,?,?,?)");
			
			ps.setString(1, book.getBookID());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getAuthor());
			ps.setInt(4, book.getAvailableCopies());
			ps.setInt(5, book.getTotalCopies());
			
			if(ps.executeUpdate()>0) {
				return true;
			}
			
			return false;
		}catch(Exception e) {
			e.getStackTrace();
			return false;
		}
	}
	
	public boolean updateAvailableCopies(String bookID,int newCount) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE book_tbl SET Available_copies = ? WHERE book_ID = ?");
			
			ps.setInt(1, newCount);
			ps.setString(2, bookID);
			
			if(ps.executeUpdate() > 0) {
				return true;
			}
			
			return false;
		}catch(Exception e) {
			e.getStackTrace();
			return false;
		}
	}
	
	public boolean deleteBook(String bookID) {
		try {
			PreparedStatement ps = con.prepareStatement("DELETE FROM book_tbl WHERE book_Id= ?");
			
			ps.setString(0, bookID);
			
			if(ps.executeUpdate()>0) {
				return true;
			}
			
			return false;
			
		}catch(Exception e) {
			e.getStackTrace();
			return false;
		}
	}



}
