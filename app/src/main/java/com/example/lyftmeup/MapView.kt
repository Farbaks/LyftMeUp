package com.example.lyftmeup

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.util.Log.DEBUG
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import org.json.JSONObject
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MapView : AppCompatActivity() {
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null
    var currentLatitude: String = "";
    var currentLongitude: String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)

        increaseBrightness()

        var mapInfo = intent?.extras?.getString("list");
        var map: JSONObject = JSONObject(mapInfo);
        Log.i("----ITEM NAME:----", map.getString("name"));

        supportActionBar!!.title = map.getString("name");

        var imgLiftView:ImageView = findViewById(R.id.imgMapView);
        imgLiftView.setImageDrawable(getDrawableFromAsset(map));
    }

    fun getDrawableFromAsset(item: JSONObject): Drawable? {
        var ims: InputStream = this.assets.open(item.getString("image"));
        var d: Drawable? = Drawable.createFromStream(ims, null);
        ims?.close();
        return d;
    }

    fun increaseBrightness() {
        if(hasWritePermisson(this)) {
            changeBrightness(this, 255)
        }
        else {
            changeWritePermission()
        }
    }

    fun hasWritePermisson(context: Context): Boolean {
        return Settings.System.canWrite(context)
    }

    fun changeBrightness(context: Context, i:Int) {
        Settings.System.putInt(
            this.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        Settings.System.putInt(
            this.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            i
        )
    }

    fun changeWritePermission() {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        startActivity(intent);

        changeBrightness(this, 255)
    }

}

