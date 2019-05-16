package com.buzzybrains.mvvmarchitecture.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buzzybrains.mvvmarchitecture.R;
import com.buzzybrains.mvvmarchitecture.fragment.AddEditNoteFragment;
import com.buzzybrains.mvvmarchitecture.fragment.ListNoteFragment;
import com.buzzybrains.mvvmarchitecture.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        final TextView titleToolbar = (TextView) mTopToolbar.findViewById(R.id.toolbar_title);

        final ImageView closeIcon = (ImageView) mTopToolbar.findViewById(R.id.close_icon);


        //show default list note fragment
        getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment, new ListNoteFragment()).commit();

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateAddEditFragment();

            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.startSync();

        noteViewModel.isNoteClicked.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNoteClicked) {

                if (isNoteClicked) {
                    navigateAddEditFragment();
                }


            }
        });


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                int count = getSupportFragmentManager().getBackStackEntryCount();

                if (count == 0) {
                    titleToolbar.setText("Note List");
                    closeIcon.setVisibility(View.INVISIBLE);
                }

                if (count > 0) {

                    String topOnStack = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();


                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deletAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        hideKeyboard(this);


        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count > 0) {

            getSupportFragmentManager().popBackStack();


        }

    }

    private void navigateAddEditFragment() {

        //navigate to add edit note fragment
        getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment, new AddEditNoteFragment()).addToBackStack("AddEditNoteFragment").commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
