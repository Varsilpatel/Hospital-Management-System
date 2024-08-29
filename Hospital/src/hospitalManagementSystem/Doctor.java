package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
	private Connection con;
	//private Scanner scanner;
	
	public Doctor(Connection con, Scanner scanner) {
		this.con = con;
		//this.scanner = scanner;
	}
	
	
	
	public void viewDoctors() {
		String query = "select * from doctors";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			System.out.println("Doctor: ");
			System.out.println("+-------------+-------------+------------------------+");
			System.out.println("| Doctor ID  | Name         | Specialization     |");
			System.out.println("+-------------+-------------+------------------------+");
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("Name");
				String specialization = rs.getString("Specialization");
				System.out.printf("|%-13s|%-15s|%-20s|\n",id,name,specialization);
				System.out.println("+-------------+-------------+------------------------+");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorbyID(int id) {
		String query = "select * from doctors where id=?";
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
