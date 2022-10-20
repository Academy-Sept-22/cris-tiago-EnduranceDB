package com.endurance.entities;

import java.util.Objects;

public class Task {

    private String name;

    private Integer estimatedCost;

    private ComplexityFactor complexityFactor;

    public Task(String taskName, int estimatedCost, ComplexityFactor medium) {
        name = taskName;
        this.estimatedCost = estimatedCost;
        complexityFactor = medium;
    }

    public Task(Task task) {
        this.name = task.name;
        this.estimatedCost = task.estimatedCost;
        this.complexityFactor = task.complexityFactor;
    }

    public Integer getEstimatedCost() {
        return estimatedCost;
    }

    public String getName() {
        return name;
    }

    public ComplexityFactor getComplexityFactor() {
        return complexityFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!Objects.equals(name, task.name)) return false;
        if (!Objects.equals(estimatedCost, task.estimatedCost))
            return false;
        return complexityFactor == task.complexityFactor;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (estimatedCost != null ? estimatedCost.hashCode() : 0);
        result = 31 * result + (complexityFactor != null ? complexityFactor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", estimatedCost=" + estimatedCost +
                ", complexityFactor=" + complexityFactor +
                '}';
    }
}
