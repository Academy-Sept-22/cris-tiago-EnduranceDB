package com.endurance.db;

import com.endurance.Project;
import com.endurance.ProjectRepository;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectRepositoryShould {
    protected void testRepository(ProjectRepository repository) {
        Project projectCreated = repository.addProject(
                new Project("ProjectTest1", "UK"));

        assertNotEquals(0, projectCreated.getID());

        Project projectQueried = repository.getProjectByID(projectCreated.getID());

        assertEquals(projectCreated, projectQueried);

        Collection<Project> projects = repository.getAllProjects();

        assertTrue(projects.contains(projectCreated));
    }
}
