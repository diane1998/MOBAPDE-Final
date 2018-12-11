package com.example.adrienne.mobapde_app;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FolderHolder  extends RecyclerView.ViewHolder{

    private TextView txtMessage;
    private LinearLayout linearLayout;
    private long id;
    //private MainActivity activity;

    public FolderHolder(View view ) {
        super(view);
        //setActivity(activity);
        txtMessage = view.findViewById(R.id.txtMsg);
        linearLayout = view.findViewById(R.id.linearLayoutFolder);
        //imgView = view.findViewById(R.id.imgView);

    }

    public void setMessage(String message) {
        txtMessage.setText(message);
    }

    /*public void setActivity(MainActivity activity) {
        this.activity = activity;
    }*/

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    //public void setLinearLayout(LinearLayout linearLayout) {
        //this.linearLayout = linearLayout;
   // }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
