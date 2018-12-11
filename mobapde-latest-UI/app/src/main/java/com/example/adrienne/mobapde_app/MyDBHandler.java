package com.example.adrienne.mobapde_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDBHandler extends SQLiteOpenHelper {

    //Logcat tag
    private static final String LOG = "DatabaseHelper";

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final  String DATABASE_NAME = "notesDB";

    //Table Names
    private static final String TABLE_FOLDER = "Folder";
    private static final String TABLE_NOTES =  "Notes";
    private static final String TABLE_PLAIN_TEXT = "PlainText";
    private static final String TABLE_CORNELL = "Cornell";
    private static final String TABLE_SPLIT = "Split";

    //Common column names
    private static final String NOTE_ID = "NoteId";
    private static final String FOLDER_ID = "FolderId";

    //Folder table - column names
    private static final String FOLDER_NAME = "FolderName";

    //NOTES table - column names
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_LAST_TIME_SAVED = "lastTimeSaved";
    private static final String NOTE_DATE_CREATED = "dateCreated";

    //PLAIN TEXT table - column names
    private static final String PLAIN_CONTENT = "mainContent";

    //SPLIT table - column names
    private static final String SPLIT_MAIN = "mainIdeas";
    private static final String SPLIT_SUPPORTING = "supportingIdeas";

    //CORNELL table - column names
    private static final String CORNELL_SUMMARY = "summary";
    private static final String CORNELL_COLUMN = "cueColumn";
    private static final String CORNELL_MAIN = "mainContent";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NOTE_TITLE + " TEXT," + NOTE_DATE_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP," + NOTE_LAST_TIME_SAVED + "DATETIME, " + FOLDER_ID + " INTEGER)";
    private static final String CREATE_TABLE_PLAIN = "CREATE TABLE " + TABLE_PLAIN_TEXT + " (" + NOTE_ID + " INTEGER, " + PLAIN_CONTENT + " TEXT)";
    private static final String CREATE_TABLE_SPLIT = "CREATE TABLE " + TABLE_SPLIT + " (" + NOTE_ID + " INTEGER,"
            + SPLIT_MAIN + "TEXT, " + SPLIT_SUPPORTING + " TEXT)";
    private static final String CREATE_TABLE_CORNELL = "CREATE TABLE " + TABLE_CORNELL + " (" + NOTE_ID + " INTEGER,"
            + CORNELL_SUMMARY + " TEXT," + CORNELL_MAIN + " TEXT," + CORNELL_COLUMN + " TEXT)";
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB_TEST", "onCreate called");
        String CREATE_TABLE = " CREATE TABLE " + TABLE_FOLDER + " (" + FOLDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FOLDER_NAME + " TEXT )";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_PLAIN);
        db.execSQL(CREATE_TABLE_SPLIT);
        db.execSQL(CREATE_TABLE_CORNELL);
        Log.d("COLUMN NAME", "onCreate: " + CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAIN_TEXT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPLIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CORNELL);

        //create new tables
        onCreate(db);
    }

    public String loadFolderName(){
        String result = "";
        String query = "Select * FROM " + TABLE_FOLDER + " ORDER BY " + FOLDER_NAME + " COLLATE NOCASE ASC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            //int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += /*String.valueOf(result_0) + */" " + result_1;
            System.getProperty("line.separator");
        }

        cursor.close();
        db.close();
        return result;
    }

    public List<Folder> loadFolders(){
        List<Folder> foldersResult = new ArrayList<>();

        String result = "";
        String query = "Select * FROM " + TABLE_FOLDER + " ORDER BY " + FOLDER_NAME + " COLLATE NOCASE ASC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //if (cursor.moveToFirst()){
            //long folderId = cursor.getColumnIndex(FOLDER_ID);

            //Folder folder = getFolder(folderId);
        while (cursor.moveToNext()){
            Folder folder = new Folder();
            folder.setFolderId(cursor.getInt(cursor.getColumnIndex(FOLDER_ID)));
            folder.setName(cursor.getString(cursor.getColumnIndex(FOLDER_NAME)));
            foldersResult.add(folder);
            Log.d("LOADING FOLDER", "loadFolders: folder " + folder.getName() + " loaded");
        }


       // } while (cursor.moveToNext());

        db.close();

        return foldersResult;
    }

    public List<Note> loadNotesFromFolder(Folder folder){
         List<Note> notesResult = new ArrayList<>();

        String query = "Select * FROM " + TABLE_NOTES + " ORDER BY " + NOTE_DATE_CREATED + " DESC" + " WHERE " + FOLDER_ID + " = " + folder.getFolderId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()){
            long noteId = cursor.getColumnIndex(NOTE_ID);

            if(getPlainTextNote(noteId) != null){
                Note note = getPlainTextNote(noteId);
                notesResult.add(note);
            }else if(getSplitNote(noteId) != null){
                Note note = getSplitNote(noteId);
                notesResult.add(note);
            }else if (getCornellNote(noteId) != null) {
                Note note = getCornellNote(noteId);
                notesResult.add(note);
            }
        }

        db.close();
        return  notesResult;
    }

    public List<Note> loadNotesNoFolder(){
        List<Note> notesResult = new ArrayList<>();

        String query = "Select * FROM " + TABLE_NOTES + " ORDER BY " + NOTE_DATE_CREATED + " DESC " + "WHERE " + FOLDER_ID + " = " + null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()){
            long noteId = cursor.getColumnIndex(NOTE_ID);

            if(getPlainTextNote(noteId) != null){
                Note note = getPlainTextNote(noteId);
                notesResult.add(note);
            }else if(getSplitNote(noteId) != null){
                Note note = getSplitNote(noteId);
                notesResult.add(note);
            }else if (getCornellNote(noteId) != null) {
                Note note = getCornellNote(noteId);
                notesResult.add(note);
            }
        }

        db.close();
        return  notesResult;
    }

    //add a new folder
    public long createFolder(Folder folder){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MyDBHandler.FOLDER_NAME, folder.getName());

        long id = db.insert(TABLE_FOLDER, null, values);
        //values.put(MyDBHandler.FOLDER_ID, id);
        //folder.setFolderId(id);


        if(id >= 0){
            Log.d("CREATED FOLDER", "createFolder: folder has been added and inserted");
        }
        db.close();

        return id;

    }

    //find folder by the folder's name
    public Folder findFolder(String folderName){
        String query = "Select * FROM " + TABLE_FOLDER + " WHERE " + FOLDER_NAME + "= '" + folderName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Folder folder = new Folder();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            folder.setFolderId(Integer.parseInt(cursor.getString(0)));
            folder.setName(cursor.getString(1));
            cursor.close();
        } else {
            folder = null;
        }
        db.close();
        return folder;
    }

    //find folder by FolderId
    public Folder getFolder(long folderId){
        String query = "Select * FROM " + TABLE_FOLDER + " WHERE " + FOLDER_ID + " = " + folderId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Folder folder = new Folder();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            folder.setFolderId(Integer.parseInt(cursor.getString(0)));
            folder.setName(cursor.getString(1));
            cursor.close();
        } else {
            folder = null;
        }
        db.close();
        return folder;
    }

    //deleting a folder
    //REMEMBER TO ADD A FUNCTION THAT WILL DELETE A NOTE THAT'S IN A FOLDER
    public boolean deleteFolder(String folderName){
        boolean result = false;
        String query = "Select * FROM " + TABLE_FOLDER + " WHERE " + FOLDER_NAME + "= '" + String.valueOf(folderName) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Folder folder = new Folder();
        if(cursor.moveToFirst()){
            folder.setName(cursor.getString(1));
            db.delete(TABLE_FOLDER, FOLDER_NAME + "=?", new String[]{
                    String.valueOf(folder.getName())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //updating a folder
    public boolean updateFolder(Folder folder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        //args.put(FOLDER_ID, folderId);
        args.put(FOLDER_NAME, folder.getName());
        return  db.update(TABLE_FOLDER, args, FOLDER_ID + "=" + folder.getFolderId(), null) > 0;
    }

    public long createNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getTitle());
        //values.put(NOTE_DATE_CREATED, note.getDateCreated());
        values.put(NOTE_LAST_TIME_SAVED, note.getLastTimeSaved());
        values.put(FOLDER_ID, note.getFolderId());

        //insert row
        long notesId = db.insert(TABLE_NOTES, null, values);
        note.setNoteId(notesId);
        return notesId;
    }

    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOTE_TITLE, note.getTitle());
        //values.put(NOTE_DATE_CREATED, note.getDateCreated());
        values.put(NOTE_LAST_TIME_SAVED, getDateTime());
        values.put(FOLDER_ID, note.getFolderId());

        return db.update(TABLE_NOTES, values, NOTE_ID + " = ?", new String[] {String.valueOf(note.getNoteId())});

    }

    public void deleteNote(long noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, NOTE_ID + " = ?", new String[] {String.valueOf(noteId)});
    }

    public void createPlainTextNote (PlainTextNote plainTextNote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAIN_CONTENT, plainTextNote.getMainContent());

        long notesId = createNote(plainTextNote);
        values.put(NOTE_ID, notesId);
        db.insert(TABLE_PLAIN_TEXT, null, values);
    }

    public PlainTextNote getPlainTextNote (long notesId){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PLAIN_TEXT + "pt" + " INNER JOIN " + TABLE_NOTES + " ON pt." + NOTE_ID + " = n." + NOTE_ID + " WHERE pt." + NOTE_ID + " = " + notesId;

        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        PlainTextNote plainTextNote = new PlainTextNote();

        plainTextNote.setNoteId(notesId);
        plainTextNote.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
        plainTextNote.setFolderId(cursor.getInt(cursor.getColumnIndex(FOLDER_ID)));
        plainTextNote.setDateCreated(cursor.getString(cursor.getColumnIndex(NOTE_DATE_CREATED)));
        plainTextNote.setLastTimeSaved(cursor.getString(cursor.getColumnIndex(NOTE_LAST_TIME_SAVED)));
        plainTextNote.setMainContent(cursor.getString(cursor.getColumnIndex(PLAIN_CONTENT)));

        return plainTextNote;
    }

    //update plain text note
    public int updatePlainTextNote(PlainTextNote plaintext){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLAIN_CONTENT, plaintext.getMainContent());

        updateNote(plaintext);

        return db.update(TABLE_PLAIN_TEXT, values, NOTE_ID + " = ?", new String [] {String.valueOf(plaintext.getNoteId())});

    }

    public void deletePlainTextNote(long noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAIN_TEXT, NOTE_ID + " = ?", new String[]{String.valueOf(noteId)});

        deleteNote(noteId);
    }


    public void createSplit(SplitNote splitNote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long notesId = createNote(splitNote);
        values.put(NOTE_ID, notesId);

        values.put(SPLIT_MAIN, splitNote.getMainIdeas());
        values.put(SPLIT_SUPPORTING, splitNote.getSupportingIdeas());

        db.insert(TABLE_SPLIT, null, values);
    }

    public SplitNote getSplitNote(long notesId){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SPLIT + "s" + " INNER JOIN " + TABLE_NOTES + " ON s." + NOTE_ID + " = n." + NOTE_ID + " WHERE s." + NOTE_ID + " = " + notesId;

        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        SplitNote note = new SplitNote();

        note.setNoteId(notesId);
        note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
        note.setFolderId(cursor.getInt(cursor.getColumnIndex(FOLDER_ID)));
        note.setDateCreated(cursor.getString(cursor.getColumnIndex(NOTE_DATE_CREATED)));
        note.setLastTimeSaved(cursor.getString(cursor.getColumnIndex(NOTE_LAST_TIME_SAVED)));
        note.setMainIdeas(cursor.getColumnName(cursor.getColumnIndex(SPLIT_MAIN)));
        note.setSupportingIdeas(cursor.getColumnName(cursor.getColumnIndex(SPLIT_SUPPORTING)));

        return note;
    }

    public int updateSplitNote(SplitNote split){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPLIT_MAIN, split.getMainIdeas());
        values.put(SPLIT_SUPPORTING, split.getSupportingIdeas());

        updateNote(split);

        return db.update(TABLE_SPLIT, values, NOTE_ID + " = ?", new String [] {String.valueOf(split.getNoteId())});

    }

    public void deleteSplitNote(long noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SPLIT, NOTE_ID + " = ?", new String[]{String.valueOf(noteId)});

        deleteNote(noteId);
    }

    public void createCornell(CornellNote cornellNote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long notesId = createNote(cornellNote);
        values.put(NOTE_ID, notesId);

        values.put(NOTE_ID, notesId);
        values.put(CORNELL_SUMMARY, cornellNote.getSummary());
        values.put(CORNELL_MAIN, cornellNote.getMainContent());
        values.put(CORNELL_COLUMN, cornellNote.getCueColumn());

        db.insert(TABLE_CORNELL, null, values);
    }

    public CornellNote getCornellNote(long noteId){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CORNELL + "c" + " INNER JOIN " + TABLE_NOTES + " ON c." + NOTE_ID + " = n." + NOTE_ID + " WHERE c." + NOTE_ID + " = " + noteId;

        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        CornellNote note = new CornellNote();

        note.setNoteId(noteId);
        note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
        note.setFolderId(cursor.getInt(cursor.getColumnIndex(FOLDER_ID)));
        note.setDateCreated(cursor.getString(cursor.getColumnIndex(NOTE_DATE_CREATED)));
        note.setLastTimeSaved(cursor.getString(cursor.getColumnIndex(NOTE_LAST_TIME_SAVED)));

        note.setSummary(cursor.getString(cursor.getColumnIndex(CORNELL_SUMMARY)));
        note.setMainContent(cursor.getString(cursor.getColumnIndex(CORNELL_MAIN)));
        note.setCueColumn(cursor.getString(cursor.getColumnIndex(CORNELL_COLUMN)));

        return note;
    }

    public int updateCornellNote(CornellNote cornell){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CORNELL_SUMMARY, cornell.getSummary());
        values.put(CORNELL_MAIN, cornell.getMainContent());
        values.put(CORNELL_COLUMN, cornell.getCueColumn());

        updateNote(cornell);

        return db.update(TABLE_CORNELL, values, NOTE_ID + " = ?", new String [] {String.valueOf(cornell.getNoteId())});
    }

    public void deleteCornellNote(long noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CORNELL, NOTE_ID + " = ?", new String[]{String.valueOf(noteId)});

        deleteNote(noteId);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
