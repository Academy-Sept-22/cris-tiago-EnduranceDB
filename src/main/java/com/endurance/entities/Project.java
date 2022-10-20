package com.endurance.entities;

import java.util.*;

public class Project {

    private String name;
    private String country;
    private Object id = 0;

    private ArrayList<Task> tasks = new ArrayList<>();

    public Project(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Project(String name, String country, List<Task> tasks) {
        this.name = name;
        this.country = country;
        this.tasks.addAll(tasks);
    }

    public Project(Object id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Object getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Collection<Task> getTasks() {
        return Collections.unmodifiableCollection(tasks);
    }

    public void copyFrom(Project project) {
        this.name = project.name;
        this.country = project.country;
        this.tasks = new ArrayList<>(project.getTasks());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!Objects.equals(name, project.name)) return false;
        if (!Objects.equals(country, project.country)) return false;
        if (!Objects.equals(id, project.id)) return false;
        return Objects.equals(tasks, project.tasks);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", id=" + id +
                ", tasks=" + tasks +
                '}';
    }
}
