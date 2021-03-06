package Todo;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.notes.MainActivity;
import com.example.notes.R;

public class AddTodo extends AppCompatActivity {
    EditText etTodo, etDescr;
    RadioGroup rgPriority;
    int priority;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        initViews();

        /*
        Listener for radio group to listen for changes in which radio button is checked
         */
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button was checked
                switch (checkedId){
                    case R.id.rbOne:
                        priority = 1;
                        return;
                    case R.id.rbTwo:
                        priority = 2;
                        return;
                    case R.id.rbThree:
                        priority = 3;
                        return;
                    case R.id.rbFour:
                        priority = 4;
                        return;
                    case R.id.rbFive:
                        priority = 5;
                        return;
                    default:
                        priority = 5;
                }
            }
        });
    }

    public void initViews(){
        // get passed in todo title if using note to todo conversion
        Intent i = getIntent();
        String todoTitle = i.getStringExtra("todoTitle");

        etTodo = findViewById(R.id.etTodo);
        // sets text of todo to text of todo
        etTodo.setText(todoTitle);
        etDescr = findViewById(R.id.etDescr);
        rgPriority = findViewById(R.id.rgPriority);

        // adding toolbar as the actionbar for the activity, allowing tap to navdrawer
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /*
    Adds todo and priority to database when add button is clicked
     */
    public void finishTodoClick(View v){
        // if user enters nothing into editText, prompt user to enter a task to accomplish
        if (etTodo.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please enter a task!", Toast.LENGTH_SHORT).show();
        }
        else if (rgPriority.getCheckedRadioButtonId() == -1){
            // if enter, then no priority was chosen, so prompt the user to choose a priority
            Toast.makeText(this, "Please select a priority", Toast.LENGTH_SHORT).show();
        }
        else {
            enterTodoToDB();

            // return back to maintodo with the priority of the new todo
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("priority", priority);
            setResult(RESULT_OK, i);
            AddTodo.this.finish();
        }
    }

    /*
    Method to actually add the new Todo object into the database
     */
    public void enterTodoToDB(){
        try {
            Todo newTodo = new Todo(etTodo.getText().toString().trim(),
                    etDescr.getText().toString().trim(), priority);
            TodoDB db = new TodoDB(this);
            db.open();
            db.addTodo(newTodo);
            db.close();
        }
        catch (SQLException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
