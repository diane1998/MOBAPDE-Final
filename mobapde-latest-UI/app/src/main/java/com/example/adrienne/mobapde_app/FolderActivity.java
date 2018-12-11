package com.example.adrienne.mobapde_app;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class FolderActivity extends AppCompatActivity implements PopupWindow.pWindowListener {
    private FolderAdapter adapter;
    private EditText txtSearch;
    private Button btnSearch;
    private TextView txtFolder;
    private long i;
    private Context context;

    //AD edits
    private MyDBHandler handler;
    private List<Folder> folderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        handler = new MyDBHandler(this, null, null, 1);

        txtSearch = findViewById(R.id.txt_noteSearch);
        btnSearch = findViewById(R.id.btn_noteSearch);
        context = getBaseContext();
        txtFolder = findViewById(R.id.txtNewFolder);
        RecyclerView view = findViewById(R.id.noteContainer);
        i=0;
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        view.setLayoutManager(manager);

        adapter = new FolderAdapter();
        view.setAdapter(adapter);

        txtFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    popUpDialog();
                Log.d("POPUP", "popup has been clicked");
                }
            });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadFolders();
                /*if (!txtSearch.getText().toString().trim().equals("")) {
                    adapter.addItem(txtSearch.getText().toString(),i);
                    txtSearch.setText("");
                    i++;*/

            }
        });


    loadFolders();

    }

    //AD edits
    public void loadFolders(){
        folderList = handler.loadFolders();

       // folderList.addAll(handler.loadFolders());
        /*
        for (Folder folder: folderList){
            if(folder.getName() != null)
                applyTexts(folder.getName());
        }
    */
        Log.d("LOAD FOLDER ACT", "loadFolders: Folders has a size of" + folderList.size());
        for (int i = 0; i < folderList.size(); i++) {
            if (folderList.get(i).getName().toString() != null) {
                adapter.addItem(folderList.get(i).getName(), folderList.get(i).getFolderId());

                Log.d("FOLDERS ACT", "loadFolders: " + folderList.get(i).getName() + " " + i);
            }

            for (Folder folder : folderList) {
                Log.d("FOLDER NAME", folder.getName());
            }

        }
    }

    // This gets the name of the folder
    @Override
    public void applyTexts(String folderName) {
        if (!folderName.isEmpty()){

            //AD edits
            Folder folder = new Folder();
            folder.setName(folderName);
            long id = handler.createFolder(folder);

            folderList.add(handler.getFolder(id));

            // add items in the interface
            adapter.addItem(folderName, id);
            txtSearch.setText("");
            i++;

            //AD edits

        }

    }

    private void popUpDialog() {
        PopupWindow pwindow = new PopupWindow();
        pwindow.show(getSupportFragmentManager(), "WINDOW");

    }
}
