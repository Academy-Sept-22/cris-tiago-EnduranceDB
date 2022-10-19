package com.endurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectAcceptanceShould {
    @Test
    public void create_and_list_projects() {
        ProjectController projectController = new ProjectController(new ProjectRepository());
        Project newProject = projectController.createProject("Project", "UK");
        Collection<Project> projects = projectController.getAllProjects();

        Project project = projectController.getProjectByID(newProject.getID());

        assertEquals(1, projects.size());
        assertEquals(newProject, project);
        assertEquals("Project", project.getName());
        assertEquals("UK", project.getCountry());
    }
}