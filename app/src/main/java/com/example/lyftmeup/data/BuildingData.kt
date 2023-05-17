package com.example.lyftmeup.data

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BuildingData(val context: Context) {

    fun loadingBuildings(): JSONArray {

        val jsonData = context.resources.openRawResource(
            context.resources.getIdentifier(
                "buildings",
                "raw",
                context.packageName
            )
        ).bufferedReader().use { it.readText() }
//
        val buildingsArray = JSONArray(jsonData);

        return buildingsArray;
    }
}