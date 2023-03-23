import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private final static String query = """
            select c.name as country,
            c.country_id,
            r.name as region_name,
            c2.name as continent_name
            from countries c
            inner join regions r on c.region_id = r.region_id
            inner join continents c2 on c2.continent_id = r.continent_id
            order by c.name asc
            """;
    private final static String url = "jdbc:mysql://localhost:3306/db-nations";
    private final static String user = "root";
    private final static String password = "root";

    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {

            System.out.println("Collegamento effettuato.");

            try (PreparedStatement ps = con.prepareStatement(query)) {

                try (ResultSet rs = ps.executeQuery()) {

                    Scanner scan = new Scanner(System.in);
                    System.out.print("Inserisci il nome intero o una sua parte: ");
                    String result = scan.nextLine();

                    while (rs.next()) {
                        String country = rs.getString(1).toLowerCase(Locale.ROOT);

                        if (country.contains(result)) {

                            country = rs.getString(1);
                            int countryId = rs.getInt(2);
                            String region = rs.getString(3);
                            String continent = rs.getString(4);
                            System.out.println("" + country + " " + countryId + " " + region + " " + continent + ".");
                        }

                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}