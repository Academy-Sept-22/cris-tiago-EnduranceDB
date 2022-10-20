package com.endurance.entities;

import java.util.Objects;

public class Project {

    private final String name;
    private final String country;
    private Object id = 0;

    public Project(String name, String country) {
        this.name = name;
        this.country = country;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!Objects.equals(name, project.name)) return false;
        if (!Objects.equals(country, project.country)) return false;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
