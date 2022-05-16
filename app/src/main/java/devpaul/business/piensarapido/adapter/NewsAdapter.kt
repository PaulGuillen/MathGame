package devpaul.business.piensarapido.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.model.NewsGame


class NewsAdapter(
    val context: Activity,
    var viewAS: ArrayList<NewsGame>
) :
    RecyclerView.Adapter<NewsAdapter.ViewAllSectionViewHolder>() {

    private var TAG = "ViewAllSection"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllSectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewAllSectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewAllSectionViewHolder, position: Int) {

        val newsSection = viewAS[position]


        val options: RequestOptions = RequestOptions()
            .error(R.drawable.think)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        holder.imageView?.let {
            Glide.with(context).load(newsSection.imagen).apply(options).into(it)
        }

        holder.textViewTitulo?.text = newsSection.titulo
        holder.textViewDescription?.text = newsSection.descripcion
        holder.textViewFecha?.text = newsSection.fecha

    }

    override fun getItemCount(): Int {
        return viewAS.size
    }

    class ViewAllSectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var textViewTitulo: TextView? = null
        var textViewDescription: TextView? = null
        var textViewFecha: TextView? = null

        var imageView: ImageView? = null

        init {

            imageView = view.findViewById(R.id.view_img)

            textViewTitulo = view.findViewById(R.id.titulo_new)
            textViewDescription = view.findViewById(R.id.description_new)
            textViewFecha = view.findViewById(R.id.fecha_new)

        }
    }


}