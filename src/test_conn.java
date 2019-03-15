import java.sql.*;


public class test_conn {
    // Atributos de la clase
    private String bd = "empresa";
    private String login = "root";
    private String pwd = "r00t";
    private String url = "jdbc:mysql://localhost:3307/" + bd
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection conexion;

    // Constructor que crea la conexión
    public test_conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, login, pwd);
            System.out.println(" - Conexión con MySQL establecida -");

        } catch (Exception e) {
            System.out.println(" – Error de Conexión con MySQL -");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test_conn prueba = new test_conn();
        prueba.Inserta2(("INSERT INTO `empresa`.`sales`\r\n" +"(`amount`)VALUES(?);"),100);

    }

    public void Inserta2(String query,int amount) {
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, amount);
            int rset = pstmt.executeUpdate();
            System.out.println();
            System.out.println(rset + " registro insertado");
            pstmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void consulta(String query) {

        try {
            Statement stmt = conexion.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            int columns = rset.getMetaData().getColumnCount();
            while (rset.next()) {
                for (int i = 1; i < columns + 1; i++) {
                    System.out.print(rset.getInt(i) + "|");

                }
                System.out.println();

            }
            rset.close();
            stmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    public void Consulta3(int id) {
        String procedimiento = "{call apellido_jugador(?)}";
        try {
            CallableStatement ctmt = conexion.prepareCall(procedimiento);
            ctmt.setInt(1, id);
            ctmt.execute();
            ResultSet rset = ctmt.getResultSet();
            rset.next();
            System.out.println(rset.getString(1));
            rset.close();
            ctmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
