import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TwoTableJoin {
	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;

	private static Connection conn2;
	private static Statement stmt2;
	private static ResultSet rs2;

	public static void ConnectTest() {
		String dbUrl = "jdbc:mysql://localhost:3306/five";
		String id = "root";
		String pwd = "1111";

		try {
			Connection con = null;
			con = DriverManager.getConnection(dbUrl, id, pwd);
			stmt = con.createStatement();
			System.out.println("mariadb DB 연결");
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}

		String dbUrl2 = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id2 = "system";
		String pwd2 = "1111";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn2 = DriverManager.getConnection(dbUrl2, id2, pwd2);
			stmt2 = conn2.createStatement();
			System.out.println("oracle DB 연결");
			// stat.close();
			// conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void Table() throws SQLException {
		rs = stmt.executeQuery("select * from sstudent");
		if (stmt.execute("select * from sstudent")) {
			rs = stmt.getResultSet();
		}
		while (rs.next()) {
				String name = rs.getString("name");
				String num = rs.getString("num");
				System.out.println("name : " + name + " num : " + num);
			}
		
		rs2 = stmt2.executeQuery("SELECT * FROM sstudent");
		if (stmt2.execute("SELECT * FROM sstudent")) {
			rs2 = stmt2.getResultSet();
		}
		while (rs2.next()) {
				String name = rs2.getString("name");
				String num = rs2.getString("num");
				System.out.println("name : " + name + " num : " + num);
			}
	}

	public static void main(String[] args) throws SQLException {
		ConnectTest();
		Table();
		// rs.close();
		stmt.close();
		stmt2.close();
		// conn.close();
		conn2.close();
	}
}