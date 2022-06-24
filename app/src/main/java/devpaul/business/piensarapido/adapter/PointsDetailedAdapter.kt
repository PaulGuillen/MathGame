package devpaul.business.piensarapido.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.model.PointsDetailed

class PointsDetailedAdapter  (val context: Context, val operationsList: ArrayList<PointsDetailed>) :
    RecyclerView.Adapter<PointsDetailedAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemVIew =
            LayoutInflater.from(context).inflate(R.layout.item_user_detail, parent, false)
        return MyViewHolder(itemVIew)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.textNombre.text = operationsList[position].name
        holder.textApellido.text = operationsList[position].lastname
        holder.TextAcertado.text = operationsList[position].lastTry
        holder.textIncorrecto.text = operationsList[position].incorrectAnswers.toString() + "\r"+ "incorrectos"
        holder.textTotalQuestion.text = operationsList[position].numberofQuestions.toString()+ "\r" + "preguntas"
        holder.TextTimePlayed.text = operationsList[position].timePlayed.toString() + "\r" + "Segundos"
        holder.TextTipo.text = operationsList[position].type
        holder.TextLevel.text = operationsList[position].level

    }


    override fun getItemCount(): Int {
        return operationsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textNombre: TextView
        var textApellido: TextView
        var TextAcertado: TextView
        var textIncorrecto: TextView
        var textTotalQuestion: TextView
        var TextTimePlayed: TextView
        var TextTipo: TextView
        var TextLevel: TextView


        init {
            textNombre = itemView.findViewById(R.id.text_name)
            textApellido = itemView.findViewById(R.id.text_lastname)
            TextAcertado = itemView.findViewById(R.id.text_puntaje_acertado)
            textIncorrecto = itemView.findViewById(R.id.text_incorrect_answer)
            textTotalQuestion = itemView.findViewById(R.id.text_total_questions)
            TextTimePlayed = itemView.findViewById(R.id.text_time_played)
            TextTipo = itemView.findViewById(R.id.text_tipo)
            TextLevel = itemView.findViewById(R.id.text_level)
        }
    }
}