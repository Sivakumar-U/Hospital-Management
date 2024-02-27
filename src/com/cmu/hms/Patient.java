package com.cmu.hms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection conn;
	Scanner sc = new Scanner(System.in);

	public Patient(Connection conn) {
		this.conn = conn;
	}

	public void addPatient() throws SQLException {
		System.out.println("Enter Patient name: ");
		String name = sc.next();

		System.out.println("Enter Patient age: ");
		int age = sc.nextInt();

		System.out.println("Enter Patient gender: ");
		String gender = sc.next();

		String query = "insert into patients(name, age, gender) values (?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);

			if (ps.executeUpdate() > 0) {
				System.out.println("Patients details added successfully.");

			} else {
				System.out.println("Failed to add patients details.");
			}

		}

	}

	public void viewPatients() throws SQLException {
		String query = "select * from patients";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			try (ResultSet rs = ps.executeQuery()) {
				System.out.println("Patients details: ");

				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					int age = rs.getInt("age");
					String gender = rs.getString("gender");

					System.out.println("Patient Id :" + id);
					System.out.println("Patient Name :" + name);
					System.out.println("Patient Age :" + age);
					System.out.println("Patient Gender :" + gender);

				}
			}
		}
	}

	public boolean getPatientById(int id) throws SQLException {
		String query = "select count(1) from patients where id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}

		return false;
	}

}
