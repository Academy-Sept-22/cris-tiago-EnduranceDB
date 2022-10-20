package com.endurance.db.mysql;

import com.endurance.entities.Project;
import com.endurance.db.repos.ProjectRepository;
import com.endurance.entities.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class MySQLProjectRepository implements ProjectRepository {

    private static final String PROJECT_TABLE = "Project";
    public static final String PROJECT_ID = "Project_ID";
    public static final String PROJECT_NAME = "Name";
    public static final String PROJECT_COUNTRY_NAME = "Project_country_name";
    private static final String TASK_TABLE = "Task";
    private static final String TASK_NAME = "Name";
    private static final String TASK_ESTIMATED_COST = "Estimated_cost";
    private static final String TASK_PROJECT_ID = "Task_project_ID";
    private static final String TASK_ID = "Task_ID";
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
    public Project getProjectByID(Object id) {
        try {
            connection.setAutoCommit(false);

            Project project = null;

            String sql = ("SELECT * FROM " + PROJECT_TABLE + " WHERE " + PROJECT_ID + " = ?;");
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, id);
                ResultSet result = statement.executeQuery();
                result.next(); // expecting only one
                int projectID = result.getInt(PROJECT_ID);
                String projectName = result.getString(PROJECT_NAME);
                String country = result.getString(PROJECT_COUNTRY_NAME);
                project = new Project(projectID, projectName, country);
            }

            sql = ("SELECT * FROM " + TASK_TABLE + " WHERE " + TASK_PROJECT_ID + " = ?;");
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, id);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    int taskId = result.getInt(TASK_ID);
                    String taskName = result.getString(TASK_NAME);
                    int estimatedCost = result.getInt(TASK_ESTIMATED_COST);
                    Task task = new Task(taskId, taskName, estimatedCost, null);
                    project.addTask(task);
                }
            }

            connection.commit();

            return project;

        } catch (SQLException sqlException) {

            try {
                connection.rollback();
            } catch (SQLException sqlException2) {
                System.err.println("Error rolling back: " + sqlException2.getMessage());
            }

            throw new RuntimeException("Error getting project", sqlException);

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException sqlException) {
                System.err.println("Error resetting auto commit: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public Project updateProject(Project project) {

        try {
            connection.setAutoCommit(false);

            String sql = ("UPDATE " + PROJECT_TABLE +
                    " SET " + PROJECT_NAME + " = ?, " + PROJECT_COUNTRY_NAME + " = ?" +
                    " WHERE " + PROJECT_ID + " = ?");
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, project.getName());
                statement.setString(2, project.getCountry());
                statement.setObject(3, project.getID());
                statement.executeUpdate();
            }

            sql = ("INSERT INTO " + TASK_TABLE + " (" + TASK_NAME + ", " + TASK_ESTIMATED_COST + ", " + TASK_PROJECT_ID + ")" +
                    " VALUES (?, ?, ?);");
            ArrayList<Task> tasksWithId = new ArrayList<>();
            for (Task task : project.getTasks()) {

                try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, task.getName());
                    statement.setInt(2, task.getEstimatedCost());
//                    statement.setString(3, task.getComplexityFactor().toString().toLowerCase());
                    statement.setObject(3, project.getID());
                    int affectedRows = statement.executeUpdate();
                    if (affectedRows != 1) {
                        throw new RuntimeException("Error inserting row " + task.getName());
                    }

                    ResultSet generatedKeysResult = statement.getGeneratedKeys();
                    generatedKeysResult.next();
                    Integer newKey = generatedKeysResult.getInt(1);
                    tasksWithId.add(new Task(newKey, task));
                }
            }

            connection.commit();

            return new Project(project.getName(), project.getCountry(), tasksWithId);

        } catch (SQLException sqlException) {

            try {
                connection.rollback();
            } catch (SQLException sqlException2) {
                System.err.println("Error rolling back: " + sqlException2.getMessage());
            }

            throw new RuntimeException("Error updating project", sqlException);

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException sqlException) {
                System.err.println("Error resetting auto commit: " + sqlException.getMessage());
            }
        }
    }
}
