package javaex.sql.nations;

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

    private final static String queryPlus1 =  """
        select c.name as country,
        c.country_id,
        r.name as region_name,
        c2.name as continent_name
        from countries c
        inner join regions r on c.region_id = r.region_id
        inner join continents c2 on c2.continent_id = r.continent_id
        where c.name like "%""";

    private final static String queryPlus2 =  """
        %"
        order by c.name asc""";

    private final static String query1 = """
            select l.`language` from languages l
            inner join country_languages cl ON l.language_id = cl.language_id
            where cl.country_id = """;

    private final static String query2 = """
                select * from country_stats cs
                        where cs.country_id  = """;
    private final static String query3 = """
 
                        order by year desc
                        limit 1
            """;

    private final static String url = "jdbc:mysql://localhost:3306/db-nations";
    private final static String user = "root";
    private final static String password = "root";

    public static void main(String[] args) throws SQLException {

        Scanner scan = new Scanner(System.in);

        try (Connection con = DriverManager.getConnection(url, user, password)) {

            System.out.println("Collegamento effettuato.");

            System.out.print("Inserisci il nome intero o una sua parte: ");
            String result = scan.nextLine();

            String query = queryPlus1+result+queryPlus2;

            try (PreparedStatement ps = con.prepareStatement(query)) {

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {




                            String country = rs.getString(1);
                            int countryId = rs.getInt(2);
                            String region = rs.getString(3);
                            String continent = rs.getString(4);
                            System.out.println("" + country + " " + countryId + " " + region + " " + continent + ".");


                    }
                }

            }

            System.out.print("Inserisci l'ID di un paese: ");
            String id = scan.nextLine();

            result = query1+id;

            try (PreparedStatement ps = con.prepareStatement(result)) {

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        String language = rs.getString(1);
                        System.out.println(language);

                    }

                }

            }

            result = query2+id+query3;

            try (PreparedStatement ps = con.prepareStatement(result)) {

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        String year = rs.getString(2);
                        String population = rs.getString(3);
                        String gdp = rs.getString(4);
                        System.out.println("YEAR "+year+"; POPULATION "+population+"; GDP "+gdp+";");

                    }

                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}