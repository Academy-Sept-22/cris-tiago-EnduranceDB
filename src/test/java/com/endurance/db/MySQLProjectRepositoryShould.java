package com.endurance.db;

import com.endurance.Project;
import com.endurance.ProjectRepository;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MySQLProjectRepositoryShould extends ProjectRepositoryShould {

    @Test
    void add_project() throws Exception {

        // needs Country table to have UK

        ProjectRepository repository = createProjectRepository();

        testRepository(repository);
    }

    protected ProjectRepository createProjectRepository() throws Exception {
        ProjectRepository repository =
                MySQLProjectRepository.create("localhost", 3306,
                        "Endurance_test", "root", "secret");
        return repository;
    }

}
