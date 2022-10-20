package com.endurance.db.mongo;

import com.endurance.entities.ComplexityFactor;
import com.endurance.entities.Project;
import com.endurance.db.repos.ProjectRepository;
import com.endurance.entities.Task;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoProjectRepository implements ProjectRepository {
    private final MongoCollection<Document> collection;

    public MongoProjectRepository(MongoDatabase database) {
        this.collection = database.getCollection("Projects");
    }

    public static MongoProjectRepository create(String host, int port,
                                                String databaseName, String userName, String password) {
        MongoClient mongoClient = MongoClients.create("mongodb://"+ userName + ":" + password + "@" + host + ":" + port + "/");
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        return new MongoProjectRepository(database);
    }
    @Override
    public Project addProject(Project project) {
        Document document = convertProjectToDocument(project);
        collection.insertOne(document);

        ObjectId id = document.getObjectId("_id");
        Project newProject = new Project(id, project.getName(), project.getCountry());
        for (Task task: project.getTasks()) {
            newProject.addTask(new Task(task));
        }

        return newProject;
    }

    @Override
    public Collection<Project> getAllProjects() {
        FindIterable<Document> projectDocuments = collection.find();

        HashSet<Project> projects = new HashSet<>();
        for (Document projectDocument : projectDocuments) {
            Project project = convertDocumentToProject(projectDocument);
            projects.add(project);
        }

        return projects;
    }

    @Override
    public Project getProjectByID(Object id) {
        Document projectDocument = collection.find(eq("_id", id)).first();
        if (projectDocument != null) {
            return convertDocumentToProject(projectDocument);
        }
        return null;
    }

    @Override
    public void updateProject(Project project) {
        Document document = convertProjectToDocument(project);
        collection.replaceOne(Filters.eq("_id", project.getID()), document);
    }

    private static Document convertProjectToDocument(Project project) {

        ArrayList<Document> taskDocuments = new ArrayList<>();
        for (Task task: project.getTasks()) {
            Document taskDocument = new Document("name", task.getName())
                    .append("estimatedCost", task.getEstimatedCost())
                    .append("complexityFactor", task.getComplexityFactor());
            taskDocuments.add(taskDocument);
        }

        return new Document("name", project.getName())
                .append("country", project.getCountry())
                .append("tasks", taskDocuments);
    }

    private static Project convertDocumentToProject(Document projectDocument) {
        Object documentId = projectDocument.getObjectId("_id");
        String projectName = projectDocument.getString("name");
        String country = projectDocument.getString("country");
        Project project = new Project(documentId, projectName, country);

        List<Document> taskDocuments = projectDocument.getList("tasks", Document.class);
        if (taskDocuments != null) {
            for (Document taskDocument : taskDocuments) {
                String taskName = taskDocument.getString("name");
                Integer estimatedCost = taskDocument.getInteger("estimatedCost");
                ComplexityFactor complexityFactor = ComplexityFactor.parseString(
                        taskDocument.getString("complexityFactor"));
                Task task = new Task(taskName, estimatedCost, complexityFactor);
                project.addTask(task);
            }
        }

        return project;
    }
}
