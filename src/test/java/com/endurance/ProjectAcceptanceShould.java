package com.endurance;

import com.endurance.controllers.ProjectController;
import com.endurance.db.test.InMemoryProjectRepository;
import com.endurance.entities.ComplexityFactor;
import com.endurance.entities.Project;
import com.endurance.entities.Task;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectAcceptanceShould {
    @Test
    public void create_and_list_projects() {

        ProjectController projectController =
                new ProjectController(new InMemoryProjectRepository());

        Project newProject = projectController.createProject("Project", "UK");

        Collection<Project> projects = projectController.getAllProjects();
        assertEquals(1, projects.size());

        Project project = projectController.getProjectByID(newProject.getID());
        assertEquals(newProject, project);
        assertEquals("Project", project.getName());
        assertEquals("UK", project.getCountry());

        project.addTask(new Task("Task1", 5, ComplexityFactor.MEDIUM));

        projectController.updateProject(project);

        Project projectSavedWithTasks = projectController.getProjectByID(newProject.getID());
        assertEquals(project, projectSavedWithTasks);
    }
}
