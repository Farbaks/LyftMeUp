package com.example.lyftmeup.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lyftmeup.BuildingDetails
import com.example.lyftmeup.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class BuildingAdapter(val context: Context, private val buildings: JSONArray): RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder>() {

    class BuildingViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
        val txtBuildingItem: TextView = view.findViewById(R.id.txtBuildingItem);
        val imgBuildingItem: ImageView = view.findViewById(R.id.imgBuildingItem);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate((R.layout.list_view_building), parent, false);

        return BuildingViewHolder(adapterLayout);
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {

        val item = buildings.getJSONObject(position);

        holder.txtBuildingItem.text = item.getString("name");
        holder.imgBuildingItem.setImageDrawable(getDrawableFromAsset(item));

        // Add click listener
        holder.itemView.setOnClickListener {
            clickItem(item);

        }
    }

    fun getDrawableFromAsset(item: JSONObject): Drawable? {
        var ims:InputStream = context.assets.open(item.getString("image"));
        var d:Drawable? = Drawable.createFromStream(ims, null);

        return d;
    }

    fun clickItem(item: JSONObject) {
        val  intent: Intent = Intent(context, BuildingDetails::class.java).apply{
            putExtra("building", item.toString())
        }
        context.startActivity(intent)
    }


    override fun getItemCount() = buildings.length()

}