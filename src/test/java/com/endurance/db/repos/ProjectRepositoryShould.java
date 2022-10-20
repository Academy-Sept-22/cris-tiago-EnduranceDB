package com.endurance.db.repos;

import com.endurance.entities.ComplexityFactor;
import com.endurance.entities.Project;
import com.endurance.entities.Task;

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

        projectQueried.addTask(new Task("Task1", 5, ComplexityFactor.MINIMUM));

        repository.updateProject(projectQueried);

        Project projectUpdatedWithTasks = repository.getProjectByID(projectCreated.getID());

        assertEquals(projectQueried, projectUpdatedWithTasks);

        Project newProjectWithTasks = new Project("ProjectTest1", "UK");
        newProjectWithTasks.addTask(new Task("Task1", 5, ComplexityFactor.MINIMUM));

        Project projectCreatedWithTasks = repository.addProject(newProjectWithTasks);

        Project projectCreatedWithTasksInDB = repository.getProjectByID(projectCreatedWithTasks.getID());

        assertEquals(projectCreatedWithTasks, projectCreatedWithTasksInDB);

    }
}
