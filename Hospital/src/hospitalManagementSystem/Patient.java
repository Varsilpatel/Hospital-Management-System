package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
	private Connection con;
	private Scanner scanner;
	
	public Patient(Connection con, Scanner scanner) {
		this.con = con;
		this.scanner = scanner;
	}
	
	public void addPatient() {
		System.out.print("Enter your name: ");
		String Name = scanner.next();
		
		System.out.print("Enter Patient age: ");
		int Age = scanner.nextInt();
		
		System.out.print("Enter Patient Gender: ");
		String Gender = scanner.next();
		
		try {
			String query = "INSERT INTO patient(Name, Age, Gender) VALUES(?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, Name);
			ps.setInt(2, Age);
			ps.setString(3, Gender);
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				System.out.println("Patient add");
			}
			else {
				System.out.println("Not found");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void viewPatient() {
		String query = "select * from patient";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			System.out.println("Patient: ");
			System.out.println("+-------------+-------------+-------------+-------------+");
			System.out.println("| Patient ID | Name         | Age         | Gender      |");
			System.out.println("+-------------+-------------+-------------+-------------+");
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("Name");
				int age = rs.getInt("Age");
				String gender = rs.getString("Gender");
				System.out.printf("|%-13s|%-15s|%-14s|%-13s|\n",id,name,age,gender);
				System.out.println("+-------------+-------------+-------------+-------------+");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientbyID(int id) {
		String query = "select * from patient where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
