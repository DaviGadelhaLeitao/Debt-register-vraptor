package br.com.davileitao.debtregistervraptor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/3WJavaWeb?verifyServerCertificate=false&useSSL=true", "root", "1234");
		} catch (SQLException e) {
			System.out.println("Erro enquanto tentava abrir conexão com o banco. ");
			throw new RuntimeException(e);
		}
	}
}
