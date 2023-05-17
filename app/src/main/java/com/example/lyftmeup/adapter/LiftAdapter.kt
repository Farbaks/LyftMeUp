package com.example.lyftmeup.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lyftmeup.BuildingDetails
import com.example.lyftmeup.MapView
import com.example.lyftmeup.R
import org.json.JSONArray
import org.json.JSONObject

class LiftAdapter(val context: Context, private val lifts: JSONArray): RecyclerView.Adapter<LiftAdapter.LiftViewHolder>() {

    class LiftViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
        val txtLiftName: TextView = view.findViewById(R.id.txtLiftName);
        val txtLiftLevel: TextView = view.findViewById(R.id.txtLiftLevel);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiftAdapter.LiftViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate((R.layout.list_view_lift), parent, false);

        return LiftAdapter.LiftViewHolder(adapterLayout);
    }

    override fun onBindViewHolder(holder: LiftAdapter.LiftViewHolder, position: Int) {

        val item = lifts.getJSONObject(position);

        holder.txtLiftName.text = item.getString("name");
        holder.txtLiftLevel.text = item.getString("level");

        // Add click listener
        holder.itemView.setOnClickListener {
            clickItem(item);

        }
    }

    fun clickItem(item: JSONObject) {
        val  intent: Intent = Intent(context, MapView::class.java).apply{
            putExtra("lift", item.toString())
        }
        context.startActivity(intent)
    }


    override fun getItemCount() = lifts.length()
}