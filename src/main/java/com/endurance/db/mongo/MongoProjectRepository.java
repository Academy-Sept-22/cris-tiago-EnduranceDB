package com.endurance.db.mongo;

import com.endurance.entities.Project;
import com.endurance.db.repos.ProjectRepository;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.HashSet;

import static com.mongodb.client.model.Filters.eq;

public class MongoProjectRepository implements ProjectRepository {
    private MongoDatabase database;

    public MongoProjectRepository(MongoDatabase database) {
        this.database = database;
    }

    public static MongoProjectRepository create(String host, int port,
                                                String databaseName, String userName, String password) {
        MongoClient mongoClient = MongoClients.create("mongodb://"+ userName + ":" + password + "@" + host + ":" + port + "/");
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoProjectRepository mongoProjectRepository = new MongoProjectRepository(database);
        return mongoProjectRepository;
    }
    @Override
    public Project addProject(Project project) {
        MongoCollection<Document> collection = database.getCollection("Projects");
        Document document = new Document("name", project.getName()).append("country", project.getCountry());
        collection.insertOne(document);

        ObjectId id = document.getObjectId("_id");

        return new Project(id, project.getName(), project.getCountry());
    }

    @Override
    public Collection<Project> getAllProjects() {
        MongoCollection<Document> collection = database.getCollection("Projects");
        FindIterable<Document> projectDocuments = collection.find();
        HashSet<Project> projects = new HashSet<>();
        for (Document projectDocument : projectDocuments) {
            Object id = projectDocument.getObjectId("_id");
            String projectName = projectDocument.getString("name");
            String country = projectDocument.getString("country");
            Project project = new Project(id, projectName, country);
            projects.add(project);
        }
        return projects;
    }

    @Override
    public Project getProjectByID(Object id) {
        MongoCollection<Document> collection = database.getCollection("Projects");
        Document projectDocument = collection.find(eq("_id", id)).first();
        Object documentId = projectDocument.getObjectId("_id");
        String projectName = projectDocument.getString("name");
        String country = projectDocument.getString("country");

        return new Project(documentId, projectName, country);
    }
}
