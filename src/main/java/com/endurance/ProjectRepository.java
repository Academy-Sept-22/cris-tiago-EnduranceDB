package com.endurance;

import java.sql.SQLException;
import java.util.Collection;

public interface ProjectRepository {
    Project addProject(Project project);

    Collection<Project> getAllProjects();

    Project getProjectByID(int id);
}
