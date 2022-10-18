package com.endurance;

import com.endurance.db.DBConnection;

import java.sql.Connection;

public class DBDumper {

    public void dumpDatabase() throws Exception {

        try (Connection connection = DBConnection.createConnection("localhost", 3306,
                "endurance", "root", "password")) {

            dumpCountry(connection);

        }

    }

    private void dumpCountry(Connection connection) {


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
