package com.example.adrienne.mobapde_app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteActivityFinal extends AppCompatActivity implements PopupNoteWindow.pWindowListener {

    private EditText txtNoteSearch;
    private Button btnNoteSearch;
    private TextView txtHeader;
    private int i;
    private NoteAdapter adapter;
    private TextView createNote;

    //AD edits
    private String folderName;
    private long folderId;
    private Folder folder;
    private List<Note> notesList;
    private MyDBHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_final);
        setContentView(R.layout.activity_note_final);
        i=0; //<-- what is this for?
        txtNoteSearch = findViewById(R.id.txt_noteSearch);
        btnNoteSearch = findViewById(R.id.btn_noteSearch);
        txtHeader = findViewById(R.id.txtNoteHeader);
        createNote = findViewById(R.id.txtCreateNote);
        RecyclerView view = findViewById(R.id.noteContainer);

        i=0;//<-- what is this for?
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        view.setLayoutManager(manager);

        adapter = new NoteAdapter();
        view.setAdapter(adapter);

        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDialog();
            }
        });

        btnNoteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!txtSearch.getText().toString().trim().equals("")) {
                    adapter.addItem(txtSearch.getText().toString(),i);
                    txtSearch.setText("");
                    i++;*/

            }
        });

        //AD edits
        handler = new MyDBHandler(this, null, null, 1);

        Intent getIntent = getIntent();
        folderName = getIntent.getStringExtra("FOLDER NAME");
        folderId = getIntent.getLongExtra("FOLDER ID",0);

        folder = new Folder();
        folder.setName(folderName);
        folder.setFolderId(folderId);

        txtHeader.setText(folderName);


    }

    public void loadNotes(){
        notesList = handler.loadNotesFromFolder(folder);

        Log.d("LOAD NOTE ACT", "loadFolders: Folders has a size of" + notesList.size());
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getTitle().toString() != null) {
                String title = notesList.get(i).getTitle();
                long noteId = notesList.get(i).getNoteId();


                //FIX THIS
                //adapter.addItem();

                Log.d("NOTE ACT", "loadFolders: " + notesList.get(i).getTitle() + " " + i);
            }

            for (Note note : notesList) {
                Log.d("Note NAME", note.getTitle());
            }

        }
    }


    @Override
    public void applyTexts(String folderName, int type) {
        if (!folderName.isEmpty()){
            Log.d("SENT-TYPE-OF-NOTE", "applyTexts: "+type );
            ArrayList<String> content = new ArrayList<String>();
            for(int i=0;i<type;i++){
                content.add("");
            }
            Log.d("SENT_TYPE OF NOTE", "applyTexts: "+type);
            adapter.addItem(folderName,i,content,type);

            // add items in the interface
            // adapter.addItem(folderName,,i);
            //txtSearch.setText("");
            i++;
        }

    }

    private void popUpDialog() {
        PopupNoteWindow owindow = new PopupNoteWindow();
        owindow.show(getSupportFragmentManager(), "WINDOW");
    }
}
