package com.endurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerShould {
    @Mock
    ProjectRepository projectRepository;
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        projectController = new ProjectController(projectRepository);
    }

    @Test
    public void create_a_new_project() {
        Project projectWithoutID = new Project("Project", "UK");
        Project newProjectWithID = new Project(1, "Project", "UK");

        given(projectRepository.addProject(projectWithoutID)).willReturn(newProjectWithID);

        Project newProject = projectController.createProject("Project", "UK");

        assertEquals("Project", newProject.getName());
        assertEquals("UK", newProject.getCountry());
    }

    @Test
    void get_all_projects() {
        ArrayList<Project> expectedProjects = new ArrayList<>();
        expectedProjects.add(new Project("Project", "UK"));
        expectedProjects.add(new Project("Project2", "Spain"));
        given(projectRepository.getAllProjects()).willReturn(expectedProjects);

        Collection<Project> projects = projectController.getAllProjects();

        assertEquals(expectedProjects, projects);
    }

    @Test
    void getProjectByID() {
        Project projectWithID = new Project(1,"Project", "UK");

        given(projectRepository.getProjectByID(projectWithID.getID())).willReturn(projectWithID);

        Project project = projectController.getProjectByID(1);

        assertEquals(projectWithID, project);
    }
}
