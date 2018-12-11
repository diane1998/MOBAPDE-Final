package com.example.adrienne.mobapde_app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class PlainTextActivity extends AppCompatActivity {
    private TextView content;
    private TextView title;
    private ArrayList<TextView> tvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_text);
        title = findViewById(R.id.MDownTitle);
        content = findViewById(R.id.MdownContent);
        tvs= new ArrayList<>();

        tvs.add(title);
        tvs.add(content);

        for (int i =0;i< tvs.size();i++){
            tvs.get(i).setFocusable(true);
            tvs.get(i).setEnabled(true);
            tvs.get(i).setClickable(true);
            tvs.get(i).setFocusableInTouchMode(true);
        }
        Intent intent= getIntent();
        title.setText(intent.getStringExtra("TITLE"));
    }
}
