package com.endurance.db;

import com.endurance.Project;
import com.endurance.ProjectRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MongoProjectRepositoryShould extends ProjectRepositoryShould {
    @Test
    void add_project() throws Exception {
        ProjectRepository repository = createProjectRepository();

        testRepository(repository);
    }

    protected ProjectRepository createProjectRepository() throws Exception {
        ProjectRepository repository = MongoProjectRepository.create("localhost", 27017, "Endurance_test", "root", "password");
        return repository;
    }
}
