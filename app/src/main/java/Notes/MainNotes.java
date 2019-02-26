package Notes;

import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.notes.ApplicationClass;
import com.example.notes.R;

import Todo.ShowTodo;

public class MainNotes extends AppCompatActivity implements NoteAdapter.ItemClicked{
    //Button btnAddNote;
    FloatingActionButton fab;
    //variables to work with recyclerview
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    // code for when returning from shownote in case any note was updated
    int showNoteCode = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);
        initViews();
        initObjects();
        new MainNotes.getData().execute();
    }

    public void initViews(){
        //btnAddNote = findViewById(R.id.btnAddNote);
        fab = findViewById(R.id.fab);
        rv = findViewById(R.id.list);
    }

    public void initObjects(){
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
    }

    /*
    Methods for when Floating Action Button is clicked (adds note)
     */
    public void addNoteFAB(View v){
        //creates intent that sends user to AddNote activity
        Intent intent = new Intent(MainNotes.this, AddNote.class);
        startActivity(intent);

        //in addnote, will add note to db and now get note from db and show
        new MainNotes.getData().execute();
    }

    /*
    Accesses database and retrieves an ArrayList of all notes in the database table
     */
    public void loadNotes(){
        try {
            NotesDB db = new NotesDB(this);
            db.open();
            ApplicationClass.notes = db.getAllNotes();
            db.close();
        }
        catch(SQLException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //reverseNotes(notes);
    }

    /*
    Fetches all notes from SQLite in the background (calls loadNotes() in the background), then
    sets re
     */
    public class getData extends AsyncTask<Void, Void, Void> {
        /*
        Actually loads the notes from the database
         */
        @Override
        protected Void doInBackground(Void... voids) {
            loadNotes();
            return null;
        }

        /*
        Notifies recyclerView adapter of a change to update recyclerView
         */
        @Override
        protected void onPostExecute(Void aVoid){
            // reverse the order of ApplicationClass.notes
            //ApplicationClass.notes = reverseNotes(ApplicationClass.notes);

            //set recyclerview to notes list if the list is >0
            if (ApplicationClass.notes.size() != 0){
                myAdapter = new NoteAdapter(MainNotes.this, ApplicationClass.notes);
                rv.setAdapter(myAdapter);
            }
        }
    }

    /*
    Sends user to ShowNote when an item in the list was clicked
     */
    @Override
    public void onItemClicked(int index) {
        Intent i = new Intent(MainNotes.this, ShowNote.class);
        // have to subtract from size because reversed, so last item is first
        i.putExtra("itemID", ApplicationClass.notes.get(index).getID());
        startActivityForResult(i, showNoteCode);
    }

    /*
    Updates the ApplicationClass.notes list and the recyclerview (if there's any changes to the
    notes)
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == showNoteCode){
            // will update recyclerview no matter what result code
            new MainNotes.getData().execute();
        }
    }
}