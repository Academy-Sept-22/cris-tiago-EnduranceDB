package com.endurance.db.mysql;

import com.endurance.db.repos.ProjectRepositoryShould;
import com.endurance.db.repos.ProjectRepository;
import org.junit.jupiter.api.Test;

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
