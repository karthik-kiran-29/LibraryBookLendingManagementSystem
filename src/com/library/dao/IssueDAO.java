package com.library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.library.bean.Issue;
import com.library.util.ActiveIssueException;
import com.library.util.DBUtil;

public class IssueDAO {
	
	private Connection con = DBUtil.getConnection();
	
	public String fetchAccountNumber(int issueId) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT Book_id from issue_tbl where Issue_id = ?");
			
			ps.setInt(1, issueId);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			return rs.getString(1);
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public int generateIssueID() {
		try {
			Statement smt =  con.createStatement();
			
			String query ="SELECT MAX(Issue_id) from issue_tbl";
			
			ResultSet rs = smt.executeQuery(query);
			
			rs.next();
			
			return rs.getInt(1) + 1;
			
		}catch(Exception e) {
			e.getStackTrace();
			return -1;
		}
	}
	
	public boolean recordIssue(Issue issue) {
		try {
			
			if(issue.getBookID().isBlank() || issue.getStudentID().isBlank()) {
				return false;
			}
			PreparedStatement ps = con.prepareStatement("INSERT INTO issue_tbl VALUE(?,?,?,?,?,?)");
			
			ps.setInt(1, issue.getIssueID());
			ps.setString(2, issue.getBookID());
			ps.setString(3, issue.getStudentID());
			ps.setString(4, issue.getStudentName());
			ps.setDate(5,issue.getIssueDate()==null ? null: new Date(issue.getIssueDate().getTime()));
			ps.setDate(6, issue.getReturnDate()==null?null:new Date (issue.getReturnDate().getTime()));
			
			if(ps.executeUpdate()>0) {
				return true;
			}
			
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean closeIssue(int issueID) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE issue_tbl SET Return_Date = CURDATE() WHERE Issue_id = ?");
			
			ps.setInt(1,issueID);
			
			if(ps.executeUpdate() > 0) {
				return true;
			}
			
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkActiveIssue(String bookId) {
		try {
			Statement smt = con.createStatement();
			
			String query = "SELECT IssueId from Issue_tbl where Book_id = " + bookId;
			
			ResultSet rs = smt.executeQuery(query);
			
			if(rs.next()) {
				return true;
			}
			
			throw new ActiveIssueException();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
