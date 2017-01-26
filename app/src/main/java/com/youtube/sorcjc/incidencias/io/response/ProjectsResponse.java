package com.youtube.sorcjc.incidencias.io.response;

import com.youtube.sorcjc.incidencias.model.Project;

import java.util.ArrayList;

public class ProjectsResponse {

    private ArrayList<Project> projects;

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
}
