package com.endurance;

import com.endurance.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBDumper {

    public void dumpDatabase() throws Exception {

        try (Connection connection = DBConnection.createConnection("localhost", 3306,
                "endurance", "root", "secret")) {

            dumpCountry(connection);

        }

    }

    private void dumpCountry(Connection connection) throws Exception{
        Statement statement = connection.createStatement();
        String sql = ("SELECT * FROM endurance.Country;");
        ResultSet result = statement.executeQuery(sql);
        System.out.println("Country Table:");
        System.out.println("Name:");
        while (result.next()) {
            String countryValue = result.getString("Name");
            System.out.println(countryValue);
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
