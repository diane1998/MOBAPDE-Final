package com.example.adrienne.mobapde_app;

public class CornellNote extends Note {

    //private long noteId;
    private String summary;
    private String cueColumn;
    private String mainContent;

    /*
    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
    */
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCueColumn() {
        return cueColumn;
    }

    public void setCueColumn(String cueColumn) {
        this.cueColumn = cueColumn;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }
}
