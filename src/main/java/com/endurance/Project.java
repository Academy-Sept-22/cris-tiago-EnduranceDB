package com.endurance;

public class Project {

    private final String name;
    private final String country;
    private int id = 0;

    public Project(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Project(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getID() {
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

        if (id != project.id) return false;
        if (!name.equals(project.name)) return false;
        return country.equals(project.country);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + id;
        return result;
    }
}
