package com.endurance.db;

import com.endurance.Project;
import com.endurance.ProjectRepository;

import java.util.*;

public class InMemoryProjectRepository implements ProjectRepository {

    private final HashMap<Object,Project> projects = new HashMap<>();

    private final Random random = new Random();

    @Override
    public Project addProject(Project project) {
        int nextId = random.nextInt();
        Project newProject = new Project(nextId, project.getName(), project.getCountry());
        projects.put(newProject.getID(), newProject);
        return newProject;
    }

    @Override
    public Collection<Project> getAllProjects() {
        return projects.values();
    }

    @Override
    public Project getProjectByID(Object id) {
        return projects.get(id);
    }
}
