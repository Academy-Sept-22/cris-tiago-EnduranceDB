package com.endurance.db;

import com.endurance.Project;
import com.endurance.ProjectRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collection;

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
        return null;
    }

    @Override
    public Project getProjectByID(int id) {
        return null;
    }
}
