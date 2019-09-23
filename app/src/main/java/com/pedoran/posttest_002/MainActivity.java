package com.pedoran.posttest_002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pedoran.posttest_002.adapter.NoteAdapter;
import com.pedoran.posttest_002.entity.Note;
import com.pedoran.posttest_002.entity.NoteDatabase;
import com.pedoran.posttest_002.ui.InputNote;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.UserActionListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Note> listNote;
    FloatingActionButton btnAdd;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("MyNotes");

        context = this;
        recyclerView = findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        setupRV();

        btnAdd = findViewById(R.id.fab_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(context, InputNote.class);
                move.putExtra(InputNote.EXTRA_ACT,"insert");
                context.startActivity(move);
            }
        });
    }

    public void setupRV(){
        NoteDatabase db = new NoteDatabase(context);
        listNote = db.selectUserData();
        NoteAdapter adapter = new NoteAdapter(context,listNote,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUserClick(final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Modify Notes : #"+note.getId())
                .setItems(new String[]{"Edit Note","Delete Note"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0 :
                                Intent move = new Intent(context, InputNote.class);
                                move.putExtra(InputNote.EXTRA_ACT,"update");
                                move.putExtra(InputNote.EXTRA_NOTE,note);
                                context.startActivity(move);
                                break;
                            case 1 :
                                //delete
                                NoteDatabase db = new NoteDatabase(context);
                                db.delete(note.getId());
                                setupRV();
                                break;
                            default:
                                Toast.makeText(context, "Unexpected Action!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AlertDialog userAct = builder.create();
        userAct.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_refresh){
            setupRV();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //nothing
    }
}
