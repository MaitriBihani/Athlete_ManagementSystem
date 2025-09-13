// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DBConnection {
//     private static final String URL = "jdbc:mysql://localhost:3306/athleteDB";
//     private static final String USER = "root";
//     private static final String PASSWORD = "root";

//     static {
//         try {
//             Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
//         } catch (ClassNotFoundException e) {
//             System.out.println("MySQL JDBC Driver not found!");
//             e.printStackTrace();
//         }
//     }

//     public static Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(URL, USER, PASSWORD);
//     }
// }
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/athleteDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}