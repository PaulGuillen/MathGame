package devpaul.business.piensarapido.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.model.Points

class PointsAdapter (private val context: Context, private val pointsList: MutableList<Points>) :
    RecyclerView.Adapter<PointsAdapter.MyViewHolder>() {

    var TAG = "OperationsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemVIew = LayoutInflater.from(context).inflate(R.layout.item_sum_detail, parent, false)
        return MyViewHolder(itemVIew)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val category = pointsList[position]
        holder.textNombre.text = pointsList[position].name
        holder.textMejorPuntaje.text = pointsList[position].bestPoints
        holder.textUltimoIntento.text = pointsList[position].lastTry
        holder.textultimoacceso.text = pointsList[position].lastTimePlayed

    }



    override fun getItemCount(): Int {
        return pointsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textNombre: TextView
        var textMejorPuntaje: TextView
        var textUltimoIntento: TextView
        var textultimoacceso: TextView


        init {
            textNombre = itemView.findViewById(R.id.text_name)
            textUltimoIntento = itemView.findViewById(R.id.text_lastTry)
            textMejorPuntaje = itemView.findViewById(R.id.text_bestPoints)
            textultimoacceso = itemView.findViewById(R.id.text_lastimeplayed)
        }
    }
}