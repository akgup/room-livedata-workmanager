package com.buzzybrains.mvvmarchitecture.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.buzzybrains.mvvmarchitecture.R
import com.buzzybrains.mvvmarchitecture.model.Note
import com.buzzybrains.mvvmarchitecture.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.note_layout.*

class AddEditNoteFragment : Fragment() {

    private var noteViewModel: NoteViewModel? = null
    var note: Note? = null

    var isEdit: Boolean = false

    var toolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true);

        toolbar = (activity as AppCompatActivity).my_toolbar
        toolbar?.close_icon?.visibility = View.VISIBLE

        toolbar?.close_icon?.setOnClickListener(View.OnClickListener {

            activity?.onBackPressed()
        })


        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        activity?.let {
            /**
             *  create view model in activity scope
             */
            noteViewModel = ViewModelProviders.of(it).get(NoteViewModel::class.java)
        }

        number_picker_priority.setMinValue(1)
        number_picker_priority.setMaxValue(10)

        isEdit = noteViewModel?.isNoteClicked?.value!!

        if (isEdit) {

            noteViewModel?.isNoteClicked!!.setValue(false)

            note = noteViewModel?.noteMutableLiveData?.value


            toolbar?.toolbar_title?.setText("Edit Note")
            edit_text_title.setText(note?.title)
            edit_text_description.setText(note?.description)
            number_picker_priority.setValue(note?.priority!!)
        } else {

            toolbar?.toolbar_title?.setText("Add Note")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.add_note_menu, menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {

        val title = edit_text_title.getText().toString();

        var updatedNote = Note(title, edit_text_description.getText().toString(), number_picker_priority.value, false)

        if (isEdit) {
            updatedNote.id = note!!.id

            noteViewModel?.update(updatedNote)

        } else {
            noteViewModel?.insert(updatedNote)
        }

        activity?.onBackPressed()
    }
}