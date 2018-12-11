package com.example.adrienne.mobapde_app;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteHolder extends RecyclerView.ViewHolder{

    private TextView txtTitle;
    private TextView txtType;
    private TextView txtPreview;
    private LinearLayout linearLayout;

    private Note note;
    //private Note note;
    //private MainActivity activity;

    public NoteHolder(View view ) {
        super(view);
        //setActivity(activity);
        txtTitle = view.findViewById(R.id.txtNoteName);
        txtType = view.findViewById(R.id.txtNoteType);
        txtPreview = view.findViewById(R.id.txtNotePreview);
        linearLayout = view.findViewById(R.id.linearLayoutNote);

        //imgView = view.findViewById(R.id.imgView);
    }

    //set note
    //i=0 = Markdown
    //i=1 = Cornell
    //i=2 = split

     public void setNote(int i ){
        if(i==1){
            note = new PlainTextNote();

        }else if(i==2) {
            note = new CornellNote();
        }else
            note = new SplitNote();
     }

     public Note getNote(){
         return  note;
     }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    public TextView getTxtType() {
        return txtType;
    }

    public void setType(String type) {
        txtType.setText(type);
    }

    public TextView getTxtPreview() {
        return txtPreview;
    }

    public void setPreview( String preview) {
        txtPreview.setText(preview);
    }

    /*public void setActivity(MainActivity activity) {
        this.activity = activity;
    }*/

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }


}
