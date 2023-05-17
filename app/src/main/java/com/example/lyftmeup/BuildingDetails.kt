package com.example.lyftmeup

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class BuildingDetails : AppCompatActivity() {
    var buildingString: String? = "";
    private lateinit var accessibilityButton: Button;
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_details)

        buildingString = intent?.extras?.getString("building");


        if (savedInstanceState == null) {
            if(buildingString == null) {
                this.finish();
                return;
            }
        }
        else {
            buildingString = savedInstanceState.getString("building");
        }

        var building: JSONObject = JSONObject(buildingString);

        var buidingImage: ImageView = findViewById(R.id.buidingImage);
        var buidingName: TextView = findViewById(R.id.buildingName);
        var buildingDescription: TextView = findViewById(R.id.buildingDescription);


        buidingName.text = building.getString("name");
        buidingImage.setImageDrawable(getDrawableFromAsset(building));
        buildingDescription.text = building.getString("description");

        // Add links to icons
        var liftIcon: LinearLayout = findViewById(R.id.liftIcon);
        liftIcon.setOnClickListener {
            clickItem(building.getJSONObject("lifts"))
        }

        var restroomIcon: LinearLayout = findViewById(R.id.restroomIcon);
        restroomIcon.setOnClickListener {
            clickItem(building.getJSONObject("restrooms"))
        }

        var entranceIcon: LinearLayout = findViewById(R.id.entranceIcon);
        entranceIcon.setOnClickListener {
            clickItem(building.getJSONObject("entrances"))
        }

        accessibilityButton = findViewById(R.id.btnAccessibility)


        accessibilityButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(building.getString("accessibilityLink")))
            this.startActivity(intent)

        }

    }

    fun getDrawableFromAsset(item: JSONObject): Drawable? {
        var ims: InputStream = this.assets.open(item.getString("image"));
        var d: Drawable? = Drawable.createFromStream(ims, null);
        ims?.close();
        return d;
    }

    fun clickItem(item: JSONObject) {
        val  intent: Intent = Intent(this, MapView::class.java).apply{
            putExtra("list", item.toString())
        }
        this.startActivity(intent)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("building", buildingString!!)
    }
}