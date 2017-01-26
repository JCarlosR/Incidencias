package com.youtube.sorcjc.incidencias.model;

public class Incident {
/*
    "id": 1,
    "title": "Primera incidencia",
    "description": "Lo que ocurre es que se encontró un problema en la página y esta se cerró.",
    "severity": "N",
    "active": 1,
    "category_id": 2,
    "project_id": 1,
    "level_id": 1,
    "client_id": 2,
    "support_id": 3,
    "created_at": "2017-01-25 23:43:54",
    "updated_at": "2017-01-25 23:43:54",
    "state": "Asignado"
*/

    int id;
    String title;
    String description;
    String severity;
    int category_id;
    int project_id;
    int level_id;
    int client_id;
    int support_id;

    String state;
}
