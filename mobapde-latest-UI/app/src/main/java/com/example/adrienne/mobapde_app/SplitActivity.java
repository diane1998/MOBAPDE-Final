package com.example.adrienne.mobapde_app;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class SplitActivity extends AppCompatActivity {
    private TextView content;
    private TextView title;
    private ArrayList<TextView> tvs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);
        content = findViewById(R.id.SplitContent);
        title = findViewById(R.id.SplitHeader);
        tvs= new ArrayList<>();

        tvs.add(title);
        tvs.add(content);

        for (int i =0;i< tvs.size();i++){
            tvs.get(i).setFocusable(true);
            tvs.get(i).setEnabled(true);
            tvs.get(i).setClickable(true);
            tvs.get(i).setFocusableInTouchMode(true);
        }
        BottomNavigationView navigation = findViewById(R.id.sNavigation);
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.sIdeas:
                                setContent("SECONDARY");
                                //setToSummary("SUMMARY");
                                //titleArea.setText("Mr. Nugget");
                                return true;
                            case R.id.pIdeas:
                                setContent("PRIMARY");
                                //setToKeyword("KEYWORD");
                                //titleArea.setText("Mr. Moo-Moo-Saur");
                                return true;
                        }
                        return false;
                    }
                });

    }
    public void setContent(String s){
        content.setText(s);
    }

}
