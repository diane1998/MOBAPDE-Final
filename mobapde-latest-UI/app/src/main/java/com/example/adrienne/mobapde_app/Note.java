package com.example.adrienne.mobapde_app;

public abstract class Note {

    private long noteId;
    private String title;
    private String lastTimeSaved;
    private String dateCreated;
    private long folderId;


    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastTimeSaved() {
        return lastTimeSaved;
    }

    public void setLastTimeSaved(String lastTimeSaved) {
        this.lastTimeSaved = lastTimeSaved;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }
}
