package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManagmentSystem {
	private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital";
	private static final String username = "root";
	private static final String password = "varsil@101103";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(url,username,password);
			Patient patient = new Patient(con,sc);
			Doctor doctor = new Doctor(con,sc);
			while(true) {
				System.out.println("Hospital Managment System");
				System.out.println("1,  Add Patient");
				System.out.println("2,  View Patient");
				System.out.println("3,  View Doctor");
				System.out.println("4,  Book Appoiment");
				System.out.println("5,  Exit");
				System.out.println("Enter your choice");
				int choice = sc.nextInt();
				
				switch(choice) {
				case 1:
					//add patient
					patient.addPatient();
					System.out.println();
				case 2:
					//view patient
					patient.viewPatient();
					System.out.println();
				case 3:
					//view doctor
					doctor.viewDoctors();
					System.out.println();
				case 4:
					//book appoi
					bookAppo(patient, doctor, con, sc);
					System.out.println();
				case 5:
					//exit
					return;
				default:
					System.out.println("Enter valid choice");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void bookAppo(Patient patient,Doctor doctor,Connection con, Scanner sc) {
		System.out.print("Enter patient id");
		int patientID = sc.nextInt();
		System.out.print("Enter doctor id");
		int doctorID = sc.nextInt();
		System.out.print("Enter appoinment date(YYYY-MM-DD)");
		String date = sc.next();
		
		if(patient.getPatientbyID(patientID) && doctor.getDoctorbyID(doctorID)) {
			if(checkDoctorAvaibility(doctorID, date, con)) {
				String appoiQuery = "insert into appoiments(PatientID, DoctorID, Appoiment_Date) values(?,?,?)";
				try {
					PreparedStatement ps = con.prepareStatement(appoiQuery);
					ps.setInt(1, patientID);
					ps.setInt(2, doctorID);
					ps.setString(3, date);
					int affrows = ps.executeUpdate();
					
					if(affrows>0) {
						System.out.print("Appoinment Booked");
					}
					else {
						System.out.print("Failed to book appoinment");
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.print("doctor not available on this date");
			}
		}
		else {
			System.out.print("Either doctor or patient doesn't exist");
		}
	}
	
	public static boolean checkDoctorAvaibility(int doctorID, String date, Connection con) {
		String query = "select count(*) from appoiments where (DoctorID=? and Appoinment_Date=?)";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,doctorID);
			ps.setString(2,date);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count==0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}