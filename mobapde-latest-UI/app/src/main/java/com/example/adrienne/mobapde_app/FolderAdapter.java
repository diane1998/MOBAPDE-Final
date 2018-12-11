package com.example.adrienne.mobapde_app;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderHolder>  {

    //private MainActivity activity;
    private ArrayList<Folder> cList;
    //private FolderActivity folderActivity;

    public FolderAdapter() {
        //this.folderActivity=folderActivity;
        //this.activity = activity;
        cList = new ArrayList<>();
        //Context context=  getBaseContext();
    }

    void addSetItem(int i) {

        //cList.add(new Folder(types.get(i), message));
        //notifyItemInserted(cList.size() - 1);
    }

    @Override
    public FolderHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.folder_row, viewGroup, false);

        return new FolderHolder(view);
    }

    @Override
    public void onBindViewHolder(final FolderHolder fHolder, final int i) {
        //fHolder.setImgView(cList.get(i).getName());

        fHolder.setMessage(cList.get(i).getName());
        fHolder.setId(cList.get(i).getFolderId());

        //AD edits
        final Folder folder = new Folder();
        folder.setName(cList.get(i).getName());
        folder.setFolderId(cList.get(i).getFolderId());

        fHolder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SHIITY_LIFE","CLICKED THE SHIT");

                Intent intent =new Intent(v.getContext(), NoteActivityFinal.class);

                //AD edits
                intent.putExtra("FOLDER NAME", folder.getName());
                intent.putExtra("FOLDER ID", folder.getFolderId());

                //startActivity(folderActivity.getBaseContext(),intent);
                //intent.putExtra("TITLE",cList.get(i).getName());
                v.getContext().startActivity(intent);
                // Toast.makeText(getBaseContext(),"you clicked" +cList.get(i).getName(),Toast.LENGTH_LONG).show();
                Log.d("CLICKED_THE_MENU", "onClick: "+cList.get(i).getName());

                //Intent intent = new Intent(v.getContext(), ItemActivity.class);

                //intent.putExtra("id", i);


                //v.getContext().startActivity(intent);
            }
        });
        // HERE THE CLICKED FOLDER NAME IS PASSED

    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    void addItem(String title, long folderId) {
        Folder folder= new Folder ();

        folder.setName(title);
        folder.setFolderId(folderId);
        cList.add(folder);
       // notifyItemInserted(cList.size() - 1);
    }
}
