package com.example.mynotes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {
    var listNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myNotesAdapter = MyNotesAdpater(this, listNotes)
        lvNotes.adapter = myNotesAdapter

        LoadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    fun LoadQuery(title: String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections, "Title like ?", selectionArgs, "Title")

        listNotes.clear()

        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(Note(ID, Title, Description))

            } while (cursor.moveToNext())
        }

        var myNotesAdapter = MyNotesAdpater(this, listNotes)
        lvNotes.adapter = myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        sv.setSearchableInfo(sm.getSearchableInfo(componentName))

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                LoadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNote -> {
                var intent = Intent(this, AddNotes::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdpater : BaseAdapter {
        var listNotesAdpater = ArrayList<Note>()
        var context: Context? = null

        constructor(context: Context, listNotesAdpater: ArrayList<Note>) : super() {
            this.listNotesAdpater = listNotesAdpater
            this.context = context
        }

        override fun getCount(): Int {
            return listNotesAdpater.size
        }

        override fun getItem(position: Int): Any {
            return listNotesAdpater[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var mynote = listNotesAdpater[position]
            myView.tvTitle.text = mynote.noteName
            myView.tvDes.text = mynote.noteDes

            myView.ivDelete.setOnClickListener {
                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(mynote.noteID.toString())

                dbManager.Delete("ID=?", selectionArgs)
                LoadQuery("%")
            }

            myView.ivEdit.setOnClickListener {
                GoToUpdate(mynote)
            }

            return myView
        }
    }

    fun GoToUpdate(note: Note) {
        var intent = Intent(this, AddNotes::class.java)

        intent.putExtra("ID", note.noteID)
        intent.putExtra("name", note.noteName)
        intent.putExtra("des", note.noteDes)
        startActivity(intent)
    }
}