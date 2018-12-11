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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PopupNoteWindow extends AppCompatDialogFragment {
    private EditText num;
    private int noteType;
    private Spinner spinner;
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
        View view = inflater.inflate(R.layout.activity_popup_notewindow,null);
        builder.setView(view);

            //final EditText text = new EditText(this);

            //builder.setView(text);
            //builder.setTitle(R.string.start5);

            builder.setTitle("Enter Note name");
            //final EditText text = new EditText(this);

            //builder.setView(text);
            //builder.setTitle(R.string.start5);
            builder.setMessage("what is the message");




        //submit = findViewById(R.id.btnSubmit);
        //cancel = findViewById(R.id.btnCancel);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.d("LOGIN NOTIFICATION","Login");
                String answer = num.getText().toString();
                noteType=spinner.getSelectedItemPosition();
                Log.d("SELECTED_SPINNER", ""+spinner.getSelectedItem()+" "+spinner.getSelectedItemPosition());
                listener.applyTexts(answer,noteType);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.d("LOGIN NOTIFICATION","Cancel");
            }
        });
        num= view.findViewById(R.id.txtFolderName);
        spinner = view.findViewById(R.id.spinner);
        List<String> noteOptions = new ArrayList<>();
        noteOptions.add("Markdown");
        noteOptions.add("Cornell");
        noteOptions.add("Split");
        ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,noteOptions);
        aAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aAdapter);
        //added this


        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noteType=position;
                Log.d("NOTE-FUCKING-TYPE","NOTE TYPE" +noteType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        //        num= findViewById(R.id.txtNum);
//        submit = findViewById(R.id.btnSubmit);
//        cancel = findViewById(R.id.btnCancel);

        return builder.create();
    }

    public interface pWindowListener{
        void applyTexts(String num,int other);
    }

}
