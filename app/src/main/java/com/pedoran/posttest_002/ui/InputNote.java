package com.pedoran.posttest_002.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pedoran.posttest_002.MainActivity;
import com.pedoran.posttest_002.R;
import com.pedoran.posttest_002.SplashScreen;
import com.pedoran.posttest_002.entity.Note;
import com.pedoran.posttest_002.entity.NoteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputNote extends AppCompatActivity {
    public static String EXTRA_ACT = "act";
    public static String EXTRA_NOTE = "note";
    String act;
    int idItem;
    Context context;
    Note daNote;
    EditText edtTitle,edtContent;
    Button btnInput;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note);
        context = this;
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);

        btnInput = findViewById(R.id.btnInput);

        act = getIntent().getStringExtra(EXTRA_ACT);
        if(act.equals("update")){
            ab.setTitle("Edit Note");
            btnInput.setText("UPDATE");
            daNote = extractingResource();
        }else{
            ab.setTitle("Create Note");
            btnInput.setText("SAVE");
        }
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDatabase db = new NoteDatabase(context);
                Note newNote = new Note();
                String title = edtTitle.getText().toString().trim();
                String content = edtContent.getText().toString();
                Date now = Calendar.getInstance().getTime();
                String timestamp = sdf.format(now);
                switch (act){
                    case "update" :
                        newNote.setId(idItem);
                        newNote.setTimestamp(timestamp);
                        newNote.setTitle(title);
                        newNote.setContent(content);
                        db.update(newNote);
//                        Toast.makeText(context, "UPDATE!", Toast.LENGTH_SHORT).show();
                        break;
                    case "insert":
                        newNote.setTimestamp(timestamp);
                        newNote.setTitle(title);
                        newNote.setContent(content);
                        db.insert(newNote);
//                        Toast.makeText(context, "SUCCESS!", Toast.LENGTH_SHORT).show();
                        break;
                }
                goHome();
            }
        });

    }

    public Note extractingResource(){
        Note note = getIntent().getParcelableExtra(EXTRA_NOTE);
        edtTitle.setText(note.getTitle());
        edtContent.setText(note.getContent());
        idItem = note.getId();
        return note;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(act.equals("update")){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_input,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                super.onBackPressed();
                break;
            case R.id.action_delete:
                NoteDatabase db = new NoteDatabase(context);
                db.delete(daNote.getId());
                goHome();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goHome(){
        Intent homie = new Intent(context, MainActivity.class);
        startActivity(homie);
    }
}
