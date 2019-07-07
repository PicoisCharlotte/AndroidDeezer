package com.example.androiddeezer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.androiddeezer.fragments.ListAlbumsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val albumFragment = ListAlbumsFragment.newInstance()
        openFragment(albumFragment)
    }

    private fun openFragment(fragment: ListAlbumsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
