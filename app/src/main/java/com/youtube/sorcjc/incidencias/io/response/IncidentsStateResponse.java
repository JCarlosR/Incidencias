package com.youtube.sorcjc.incidencias.io.response;

public class IncidentsStateResponse {

    private int pending;
    private int solved;
    private int assigned;

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getAssigned() {
        return assigned;
    }

    public void setAssigned(int assigned) {
        this.assigned = assigned;
    }
}
