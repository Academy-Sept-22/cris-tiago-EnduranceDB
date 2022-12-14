package com.endurance.controllers;

import com.endurance.db.repos.ProjectRepository;
import com.endurance.entities.Project;

import java.util.Collection;

public class ProjectController {
    private ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String name, String country) {
        Project project = new Project(name, country);
        Project newProject = projectRepository.addProject(project);
        return newProject;
    }

    public Collection<Project> getAllProjects() {
       Collection<Project> allProjects = projectRepository.getAllProjects();
       return allProjects;
    }

    public Project getProjectByID(Object id) {
        Project project = projectRepository.getProjectByID(id);
        return project;
    }

    public void updateProject(Project project) {
       projectRepository.updateProject(project);
    }
}
