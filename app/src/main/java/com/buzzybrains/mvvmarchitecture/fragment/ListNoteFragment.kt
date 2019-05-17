package com.buzzybrains.mvvmarchitecture.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buzzybrains.mvvmarchitecture.R
import com.buzzybrains.mvvmarchitecture.adapter.NoteAdapter

import com.buzzybrains.mvvmarchitecture.model.Note
import com.buzzybrains.mvvmarchitecture.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_list_note.*


class ListNoteFragment : Fragment() {

    private var noteViewModel: NoteViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true);

        val toolbar = (activity as AppCompatActivity).my_toolbar

        toolbar.toolbar_title.setText("Note List")
        toolbar.close_icon.visibility = View.INVISIBLE

        return inflater.inflate(R.layout.fragment_list_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        activity?.let {
            /**
             *  create view model in activity scope
             */
            noteViewModel = ViewModelProviders.of(it).get(NoteViewModel::class.java)
        }

        val noteAdapter = NoteAdapter()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setAdapter(noteAdapter)

        noteViewModel?.allNotes?.observe(this, Observer<List<Note>> { notes ->
            Log.i("akshay", "on changed is called " + notes.toString())

            noteAdapter.submitList(notes)

        })

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(0, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                noteViewModel?.delete(noteAdapter.getNoteAtPosition(viewHolder.adapterPosition))

                Toast.makeText(activity, "Note deleted!!", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)


        noteAdapter.setonItemClickListener { note ->

            noteViewModel?.noteMutableLiveData?.value = note

            noteViewModel?.isNoteClicked?.postValue(true)

        }

    }

}