package com.example.game;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    TextView textView;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    ListView listView;
    int score = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
                //   this.deleteDatabase(DatabaseContract.DATABASE_NAME);
                textView = findViewById(R.id.textViewScore);
        Intent intent = getIntent();
        score = intent.getIntExtra("SCORE", 0);
        textView.setText(R.string.your_score+ score);

        databaseHelper = new DatabaseHelper(this);
        cursor = databaseHelper.getScores(databaseHelper.getReadableDatabase());

        ListAdapter listAdapter = new ListAdapter(this, cursor, false);
        listView = findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        final Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.editText);
                if (score >= 0) {
                    String user = editText.getText().toString();
                    databaseHelper.insertScore(databaseHelper.getWritableDatabase(), user, score);
                    cursor = databaseHelper.getScores(databaseHelper.getReadableDatabase());
                    ListAdapter listAdapter = new ListAdapter(getApplicationContext(), cursor, false);
                    listView.setAdapter(listAdapter);
                    score = -1;
                }
                editText.setEnabled(false);
                editText.setText("Wynik zapisany");

            }
        });

        final Button newGameButton = findViewById(R.id.newGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });
    }


}
