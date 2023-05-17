package com.example.lyftmeup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lyftmeup.adapter.LiftAdapter
import org.json.JSONArray
import org.json.JSONObject

class AccessibilityDetails : AppCompatActivity() {
    var accessibilityString: String? = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessibility_details);

        accessibilityString = intent?.extras?.getString("list");

        if (savedInstanceState == null) {
            if(accessibilityString == null) {
                this.finish();
                return;
            }
        }
        else {
            accessibilityString = savedInstanceState.getString("list");
        }

        val actionBar = supportActionBar;
        actionBar!!.title = intent?.extras?.getString("title");

        var accessibilityList: JSONArray = JSONArray(accessibilityString);

        // Pass to recycler if lifts are available
        if(accessibilityList.length() > 0) {
            val recyclerView = findViewById<RecyclerView>(R.id.listLifts);
            recyclerView.adapter = LiftAdapter(this, accessibilityList);
        }
    }
}