package com.example.notes;

import java.util.Date;

public class Note {
    private int id;
    private String title, note;
    //private Date date;

    public Note(){

    }

    // ctor to set all instance variables
    public Note( int noteId, String noteTitle, String noteBody){
        id = noteId;
        title = noteTitle;
        note = noteBody;
        //this.date = date;
    }

    //ctor to just set title and note
    public Note( String noteTitle, String noteBody ){
        title = noteTitle;
        note = noteBody;
        //this.date = date;
    }

    //getters
    public int getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getNote(){
        return note;
    }

    //public Date getDate(){ return date; }

    //setters
    public void setID( int noteId ){
        id = noteId;
    }

    public void setTitle( String noteTitle ){
        title = noteTitle;
    }

    public void setNote( String noteBody ){
        note = noteBody;
    }

    //public void setDate( Date noteDate ) { date = noteDate; }
}