package com.endurance.db;


import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class MongoTutorial {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://root:password@localhost:27017/");
        MongoDatabase database = mongoClient.getDatabase("myMongoDb");

        MongoCollection<Document> collection = database.getCollection("movies");
        Document document = new Document("title", "Dangal").append("rating", "Not Rated");
        collection.insertOne(document);

        Document doc = collection.find(eq("title", "Dangal")).first();
        System.out.println(doc.toJson());

        collection.updateOne(
                Filters.eq("title", "Dangal"),
                Updates.set("rating", 9.5));

        doc = collection.find(eq("title", "Dangal")).first();
        System.out.println(doc.toJson());

        FindIterable<Document> movies = collection.find();
        for (Document movie : movies) {
            System.out.println(movie.toJson());
        }
    }
}
