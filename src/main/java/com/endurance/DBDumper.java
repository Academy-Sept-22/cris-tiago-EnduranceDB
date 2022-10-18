package com.endurance;

import com.endurance.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDumper {

    public void dumpDatabase() throws Exception {

        try (Connection connection = DBConnection.createConnection("localhost", 3306,
                "endurance", "root", "password")) {

            dumpCountry(connection);

        }

    }

    private void dumpCountry(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = ("SELECT * FROM endurance.Country;");
        ResultSet result = statement.executeQuery(sql);
        System.out.println("Country Taxes:");
        while (result.next()) {
            String countryValue = result.getString("Name");
            System.out.println(countryValue);
            dumpTaxesFor(connection, countryValue);
        }
    }

    private void dumpTaxesFor(Connection connection, String country) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = ("SELECT * FROM endurance.Tax WHERE Tax_country_name = '" + country + "';");
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int taxValue = result.getInt("value");
            System.out.println("\t"+ taxValue);
        }
    }

    public static void main(String[] args) {

        // dump the database to the console

        DBDumper dumper = new DBDumper();
        try {
            dumper.dumpDatabase();
        } catch (Exception e) {
            System.err.println("Error dumping database!");
            e.printStackTrace();
        }

    }
}
