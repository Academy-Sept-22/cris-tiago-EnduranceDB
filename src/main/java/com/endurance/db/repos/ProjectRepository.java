package com.endurance.db.repos;

import com.endurance.entities.Project;

import java.util.Collection;

public interface ProjectRepository {
    Project addProject(Project project);

    Collection<Project> getAllProjects();

    Project getProjectByID(Object id);

    Project updateProject(Project project);
}
