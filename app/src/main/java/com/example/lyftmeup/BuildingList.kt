package com.example.lyftmeup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.example.lyftmeup.adapter.BuildingAdapter
import com.example.lyftmeup.data.BuildingData

class BuildingList : AppCompatActivity() {
    private val SHARED_PREFERENCE_FULLNAME = "full_name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_list)

        getUserInfo()

        // Get building array from local json file
        val buildings = BuildingData(this).loadingBuildings();

        // Pass to recycler
        val recyclerView = findViewById<RecyclerView>(R.id.listBuidings);
        recyclerView.adapter = BuildingAdapter(this, buildings);
    }

    fun getUserInfo() {
        val sharedPreference by lazy {
            getSharedPreferences(
                "${BuildConfig.APPLICATION_ID}_SHARED_PREFERENCES",
                Context.MODE_PRIVATE
            )
        }

        var name = sharedPreference.getString(SHARED_PREFERENCE_FULLNAME, "")

        //  Skip step and go to building list if data is already stored
        if (name != null && name.isNotEmpty()) {
            supportActionBar!!.title = "Welcome, "+name;
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.useLightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else if(item.itemId == R.id.useDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        return super.onOptionsItemSelected(item)
    }
}