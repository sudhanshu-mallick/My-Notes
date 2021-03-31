package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add dummy data
        listNotes.add(
            Note(
                1,
                " meet professor",
                "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."
            )
        )
        listNotes.add(
            Note(
                2,
                " meet doctor",
                "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."
            )
        )
        listNotes.add(
            Note(
                3,
                " meet friend",
                "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."
            )
        )
        listNotes.add(
            Note(
                1,
                " meet professor",
                "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."
            )
        )
        listNotes.add(
            Note(
                2,
                " meet doctor",
                "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."
            )
        )
        listNotes.add(
            Note(
                3,
                " meet friend",
                "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."
            )
        )

        var myNotesAdapter = MyNotesAdpater(listNotes)
        lvNotes.adapter = myNotesAdapter

    }

    inner class MyNotesAdpater : BaseAdapter {
        var listNotesAdpater = ArrayList<Note>()

        constructor(listNotesAdpater: ArrayList<Note>) : super() {
            this.listNotesAdpater = listNotesAdpater
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

            return myView
        }
    }
}