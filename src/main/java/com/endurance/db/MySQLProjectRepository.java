package com.endurance.db;

import com.endurance.Project;
import com.endurance.ProjectRepository;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class MySQLProjectRepository implements ProjectRepository {

    private static final String PROJECT_TABLE = "Project";
    public static final String PROJECT_ID = "Project_ID";
    public static final String PROJECT_NAME = "Name";
    public static final String PROJECT_COUNTRY_NAME = "Project_country_name";
    private final Connection connection;

    public static MySQLProjectRepository create(String host, int port,
                                           String database, String userName, String password)
            throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + database, userName, password);

        return new MySQLProjectRepository(connection);
    }

    public MySQLProjectRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Project addProject(Project project) {
        try {
            String sql = ("INSERT INTO " + PROJECT_TABLE + " (" + PROJECT_NAME + ", " + PROJECT_COUNTRY_NAME + ")" +
                    " VALUES (?, ?);");
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, project.getName());
            statement.setString(2, project.getCountry());
            statement.executeUpdate();

            ResultSet generatedKeysResult = statement.getGeneratedKeys();
            generatedKeysResult.next();
            int newKey = generatedKeysResult.getInt(1);

            return new Project(newKey, project.getName(), project.getCountry());
        } catch (SQLException sqlException) {
            throw new RuntimeException("Error adding project", sqlException);
        }
    }

    @Override
    public Collection<Project> getAllProjects() {
        try {
            Statement statement = connection.createStatement();
            String sql = ("SELECT * FROM " + PROJECT_TABLE + ";");
            ResultSet result = statement.executeQuery(sql);
            HashSet<Project> projects = new HashSet<>();
            while (result.next()) {
                int projectID = result.getInt(PROJECT_ID);
                String projectName = result.getString(PROJECT_NAME);
                String country = result.getString(PROJECT_COUNTRY_NAME);
                Project project = new Project(projectID, projectName, country);
                projects.add(project);
            }
            return projects;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Error adding project", sqlException);
        }
    }

    @Override
    public Project getProjectByID(int id) {
        try {
            String sql = ("SELECT * FROM " + PROJECT_TABLE + " WHERE " + PROJECT_ID + " = ?;");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next(); // expecting only one
            int projectID = result.getInt(PROJECT_ID);
            String projectName = result.getString(PROJECT_NAME);
            String country = result.getString(PROJECT_COUNTRY_NAME);
            return new Project(projectID, projectName, country);
        } catch (SQLException sqlException) {
            throw new RuntimeException("Error adding project", sqlException);
        }
    }
}
