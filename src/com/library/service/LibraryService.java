package com.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.library.bean.Book;
import com.library.bean.Issue;
import com.library.dao.BookDAO;
import com.library.dao.IssueDAO;
import com.library.util.BookUnavailableException;
import com.library.util.DBUtil;
import com.library.util.ValidationException;

public class LibraryService {
	
	Connection con = DBUtil.getConnection();
	
	BookDAO bookdao = new BookDAO();
	
	IssueDAO issuedao = new IssueDAO();
	
	public Book viewBookDetails(String bookID) {
		return bookdao.findBook(bookID);
	}
	
	public List<Book> viewAllBooks(){
		return bookdao.viewAllBooks();
	}
	
	public boolean addNewBook(Book book) {
		return bookdao.insertBook(book);
	}
	
	public boolean removeBook(String BookId) {
		if(BookId.isBlank()) {
			return false;
		}
		
		if(issuedao.checkActiveIssue(BookId)) {
			return false;
		}
		
		if(bookdao.deleteBook(BookId)) {
			return true;
		}
		
		return false;
	}
	
	public boolean issueBook(String bookID,String studentID,String studentName) {
		try {
		if(bookID.isBlank() || studentID.isBlank() || studentName.isBlank()) {
			throw new ValidationException();
		}
		
		Book book = bookdao.findBook(bookID);
		
		if(book==null) return false;
		
		System.out.println(book.toString());
		
		if(book.getAvailableCopies()<=0){
			throw new BookUnavailableException();
		}
		
		 con.setAutoCommit(false);
		 
		 int updatedCopies = book.getAvailableCopies() - 1;
		 
		 bookdao.updateAvailableCopies(bookID, updatedCopies);
		 
		 int newIssueId = issuedao.generateIssueID();
		 
		 Issue issue = new Issue();
		 
		 issue.setIssueID(newIssueId);
		 issue.setBookID(bookID);
		 issue.setStudentID(studentID);
		 issue.setStudentName(studentName);
		 
		 issuedao.recordIssue(issue);
		 
		 con.commit();
		 
		 return true;
		}catch(Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean returnBook(int issueID) {
		if(issueID < 0) {
			return false;
		}
		
		try {
			con.setAutoCommit(false);
			
			String bookId = issuedao.fetchAccountNumber(issueID);
			
			Book book = bookdao.findBook(bookId);
			
			bookdao.updateAvailableCopies(bookId, book.getAvailableCopies() + 1);
			
			issuedao.closeIssue(issueID);
			
			con.commit();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return false;
		}
		
		
		
		
		
		
	}

	


}
