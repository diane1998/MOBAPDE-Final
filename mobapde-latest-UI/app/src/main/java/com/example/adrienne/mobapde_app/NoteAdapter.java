package com.example.adrienne.mobapde_app;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteHolder>  {

    //private MainActivity activity;
    private ArrayList<Note> nList;
    private int noteType;

    public NoteAdapter() {
        //this.activity = activity;
        nList = new ArrayList<>();
        //Context context=  getBaseContext();

    }

    void addSetItem(int i) {

        //cList.add(new Folder(types.get(i), message));
        //notifyItemInserted(cList.size() - 1);
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //noteType=0;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.note_row, viewGroup, false);

        return new NoteHolder(view);
    }


    @Override
    public void onBindViewHolder(NoteHolder nHolder, final int i) {
        //fHolder.setImgView(cList.get(i).getName());
        //==========================//////////////
        //nHolder.getNote().setDateCreated();
        //nHolder.getNote().setFolderId();
        //nHolder.getNote().setLastTimeSaved();
        //nHolder.getNote().setNoteId();
        //nHolder.getNote().setTitle();*/

     /*   private TextView txtTitle;
        private TextView txtType;
        private TextView txtPreview;
        private LinearLayout linearLayout;*/
        nHolder.setTitle(nList.get(i).getTitle());

        //nHolder.setMessage(nList.get(i).getName());
        //nHolder.setId(nList.get(i).getFolderId());

        // HERE THE CLICKED FOLDER NAME IS PASSED
        nHolder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getBaseContext(),"you clicked" +cList.get(i).getName(),Toast.LENGTH_LONG).show();
                Log.d("CLICKED_THE_MENU", "onClick: "+nList.get(i).getTitle());
                //Go to PlainActivity
                //Intent intent =new Intent(v.getContext(), PlainTextActivity.class);
                //Go to Cornell Activity
                //Intent intent =new Intent(v.getContext(), CornellActivity.class);
                //Go to Split Activity
                if(noteType==0){
                    Intent intent =new Intent(v.getContext(),PlainTextActivity.class);
                    intent.putExtra("TITLE",nList.get(i).getTitle());
                    v.getContext().startActivity(intent);
                }else if(noteType==1){
                    Intent intent =new Intent(v.getContext(),CornellActivity.class);
                    intent.putExtra("TITLE",nList.get(i).getTitle());
                    v.getContext().startActivity(intent);
                }else{
                    Intent intent =new Intent(v.getContext(),SplitActivity.class);
                    intent.putExtra("TITLE",nList.get(i).getTitle());
                    v.getContext().startActivity(intent);

                }



                //Intent intent = new Intent(v.getContext(), ItemActivity.class);

                //intent.putExtra("id", i);


                //v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nList.size();
    }

    void addItem(String title,long i, ArrayList<String> content,int noteType ) {
        //Note note= new Note ();
        /*folder.setFolderId(i);
        folder.setName(title);*/
        this.noteType= noteType;
        Note n= new PlainTextNote();
        n.setTitle(title);
        n.setNoteId(i);
        n.setLastTimeSaved("lasttime");
        n.setDateCreated("today");

        nList.add(n);
      /*  if(content.size()==1){
                PlainTextNote plain = new  PlainTextNote();
                plain.setMainContent(content.get(0));
                plain.setTitle(title);
                // format for date created
                //plain.setDateCreated();
                //plain.setNoteId();;
                nList.add(plain);
            }else if(content.size()==3) {
                CornellNote cornell = new CornellNote();
                cornell.setCueColumn(content.get(0));
                cornell.setMainContent(content.get(1));
                cornell.setSummary(content.get(2));
                nList.add(cornell);
            }else if(content.size()==2){
                SplitNote sNote = new SplitNote();
                sNote.setMainIdeas(content.get(0));
                sNote.setSupportingIdeas(content.get(1));
                nList.add(sNote);
        }*/



       // notifyItemInserted(cList.size() - 1);
    }
}
