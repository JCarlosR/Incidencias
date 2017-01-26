package com.youtube.sorcjc.incidencias.io.response;

import com.youtube.sorcjc.incidencias.model.IncidentsByProject;
import com.youtube.sorcjc.incidencias.model.IncidentsBySupport;

import java.util.ArrayList;

public class IncidentsCountBySupport {

    private ArrayList<IncidentsBySupport> incidents_by_support;

    public ArrayList<IncidentsBySupport> getIncidentsBySupport() {
        return incidents_by_support;
    }

}
