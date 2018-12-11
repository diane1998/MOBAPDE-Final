package com.example.adrienne.mobapde_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class PopupWindow extends AppCompatDialogFragment {
    private EditText num;
    private pWindowListener listener;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener= (pWindowListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+ "must throw");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //super.onCreateDialog(savedInstanceState);
       // setContentView(R.layout.activity_popup_window);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_popup_window,null);
        builder.setView(view);

            builder.setTitle("Enter Folder Name");
            //final EditText text = new EditText(this);

            //builder.setView(text);
            //builder.setTitle(R.string.start5);
            builder.setMessage("idk message");




        //submit = findViewById(R.id.btnSubmit);
        //cancel = findViewById(R.id.btnCancel);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.d("LOGIN NOTIFICATION","Login");
                String answer = num.getText().toString();
                listener.applyTexts(answer);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.d("LOGIN NOTIFICATION","Cancel");
            }
        });
        num= view.findViewById(R.id.txtFolderName);
//        num= findViewById(R.id.txtNum);
//        submit = findViewById(R.id.btnSubmit);
//        cancel = findViewById(R.id.btnCancel);

        return builder.create();
    }

    public interface pWindowListener{
        void applyTexts(String num);
    }

}
