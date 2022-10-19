package com.endurance;

import com.endurance.db.DBConnection;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class DBDumper {

    public void dumpDatabase() throws Exception {

        Properties properties = new Properties();
        properties.load(new FileInputStream("AccessData.properties"));

        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String database = properties.getProperty("database");
        String userName = properties.getProperty("user_name");
        String userPassword = properties.getProperty("user_password");

        try (Connection connection = DBConnection.createConnection(
                host, port, database, userName, userPassword)) {

            dumpCountry(connection);
            dumpProjects(connection);

            //addProject(connection, "Project New", "Spain");

        }

    }

    private void addProject(Connection connection, String projectName, String projectCountry) throws SQLException {
        String sql = ("INSERT INTO Project (Name, Project_country_name) VALUES (?, ?);" );
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, projectName);
        statement.setString(2, projectCountry);
        statement.executeUpdate();

        ResultSet generatedKeysResult = statement.getGeneratedKeys();
        generatedKeysResult.next();
        int newKey = generatedKeysResult.getInt(1);
        System.out.println("New project created with key=" + newKey);
    }

    private void dumpProjects(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = ("SELECT * FROM Project;");
        ResultSet result = statement.executeQuery(sql);
        System.out.println("Projects:");
        while (result.next()) {
            String projectName = result.getString("Name");
            System.out.println(projectName);
            String projectID = result.getString("Project_ID");
            dumpTasksFor(connection, projectID);
        }
    }

    private void dumpTasksFor(Connection connection, String projectID) throws SQLException {
        String sql = ("SELECT * FROM Task WHERE Task_project_ID = ?" );
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, projectID);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String taskName = result.getString("Name");
            System.out.println("\t"+ taskName);
        }
    }

    private void dumpCountry(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = ("SELECT * FROM Country;");
        ResultSet result = statement.executeQuery(sql);
        System.out.println("Country Taxes:");
        while (result.next()) {
            String countryValue = result.getString("Name");
            System.out.println(countryValue);
            dumpTaxesFor(connection, countryValue);
        }
    }

    private void dumpTaxesFor(Connection connection, String country) throws SQLException {
        String sql = ("SELECT * FROM Tax WHERE Tax_country_name = ?" );
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, country);
        ResultSet result = statement.executeQuery();
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
