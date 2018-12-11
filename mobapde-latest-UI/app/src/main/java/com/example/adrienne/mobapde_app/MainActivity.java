package com.example.adrienne.mobapde_app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText folderNameField;
    private Button add_folder_btn;
    private Button load_folderDB_btn;
    private Button npage;
    private TextView textView2;

    private Button delete_folder_btn;
    private MyDBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        folderNameField = findViewById(R.id.folderNameField);
        add_folder_btn = findViewById(R.id.add_folder_btn);
        load_folderDB_btn = findViewById(R.id.load_folderDB_btn);
        textView2 = findViewById(R.id.textView2);
        npage = findViewById(R.id.btn_nextpage);

        delete_folder_btn = findViewById(R.id.delete_folder_btn);


        handler = new MyDBHandler(this, null, null, 1);

        add_folder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFolder(v);
            }
        });

        load_folderDB_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFolders(v);
            }
        });

        delete_folder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteFolder(v);

            }
        });


    }

    public void addFolder(View view) {
        //MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String name = folderNameField.getText().toString();
        Folder folder = new Folder();
        folder.setName(name);
        long id = handler.createFolder(folder);
        folder.setFolderId(id);
        folderNameField.setText("");

    }

    public void loadFolders(View view) {
        //MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        List<Folder> folders = handler.loadFolders();
        String result = "";

        Log.d("MAIN ACT LOAD", "loadFolders: Folders has a size of" + folders.size());
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getName().toString() != null) {
                result = result + folders.get(i).getName().toString() + "\n";

                Log.d("FOLDERS", "loadFolders: " + folders.get(i).getName() + " " + i);
            }

            for (Folder folder : folders) {
                Log.d("FOLDER NAME", folder.getName());
            }

        }
        textView2.setText(result);
        folderNameField.setText("");
    }
/*
    public void deleteFolder(View view) {
        String name = folderNameField.getText().toString();
        //Folder deleteFolder = handler.findFolder(name);

        handler.deleteFolder(name);
    }
    */

    public void gotoFolderPage(View view) {
        startActivity(new Intent(MainActivity.this, FolderActivity.class));
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.mainmenu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//
//        switch (item.getItemId()){
//            case R.id.folderItem:
//                addFolder(getWindow().findViewById(android.R.id.content));
//                loadFolders(getWindow().findViewById(android.R.id.content));
//        }
//        return true;
//
//    }

}