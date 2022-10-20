package com.endurance.db;

import com.endurance.Project;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MySQLProjectRepositoryShould {

    @Test
    void add_project() throws Exception {

        // needs Country table to have UK

        MySQLProjectRepository mySQLProjectRepository =
                MySQLProjectRepository.create("localhost", 3306,
                        "Endurance_test", "root", "secret");

        Project projectCreated = mySQLProjectRepository.addProject(
                new Project("ProjectTest1", "UK"));

        assertNotEquals(0, projectCreated.getID());

        Project projectQueried = mySQLProjectRepository.getProjectByID(projectCreated.getID());

        assertEquals(projectCreated, projectQueried);

        Collection<Project> projects = mySQLProjectRepository.getAllProjects();

        assertTrue(projects.contains(projectCreated));
    }

}
