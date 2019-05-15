package com.buzzybrains.mvvmarchitecture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.buzzybrains.mvvmarchitecture.R;
import com.buzzybrains.mvvmarchitecture.model.Note;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE =
            "com.buzzybrains.architectureexample.EXTRA_NOTE";
    public static final String EXTRA_ID =
            "com.buzzybrains.architectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.buzzybrains.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.buzzybrains.architectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.buzzybrains.architectureexample.EXTRA_PRIORITY";
    Note note;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra(EXTRA_NOTE);
        if (note != null) {
            setTitle("Edit Note");
            editTextTitle.setText(note.getTitle());
            editTextDescription.setText(note.getDescription());
            numberPickerPriority.setValue(note.getPriority());
        } else {
            setTitle("Add Note");

        }

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        if (note != null) {
            data.putExtra(EXTRA_ID, note.getId());
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
