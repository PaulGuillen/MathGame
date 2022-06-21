package devpaul.business.piensarapido.activities.detaildashboard.news


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.adapter.NewsAdapter
import devpaul.business.piensarapido.model.NewsGame
import java.util.*
import kotlin.collections.ArrayList

class NewsGameActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    var TAG = "ViewAllSection"
    var recyclerViewAll: RecyclerView ? = null

    //ViewAllSection
    var viewAllList = ArrayList<NewsGame>()
    private var viewAllAdapter: NewsAdapter? = null

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null

    var linearnoData: LinearLayout? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_game)

        @Suppress("DEPRECATION")
        progressDialog = ProgressDialog(this)
        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        linearnoData = findViewById(R.id.linearlayout_nodata)

        recyclerViewAll = findViewById(R.id.recyclerview_view_all)
        recyclerViewAll?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewAllAdapter = NewsAdapter(this, viewAllList)
        recyclerViewAll?.adapter = viewAllAdapter

        loadingdata()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadingdata (){
        progressDialog!!.show()
        progressDialog?.setContentView(R.layout.charge_dialog)
        Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawableResource(android.R.color.transparent)
        db.collection(Constants.PATH_NEWS).orderBy("fecha", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (value?.isEmpty!!){
                    progressDialog?.dismiss()
                    linearnoData?.visibility = View.VISIBLE
                }else{
                    for (doc in value.documentChanges) {
                        linearnoData?.visibility = View.GONE
                        recyclerViewAll?.visibility = View.VISIBLE
                        val section = doc.document.toObject(NewsGame::class.java)
                        viewAllList.add(section)
                        viewAllAdapter?.notifyDataSetChanged()
                        progressDialog?.dismiss()
                    }
                }
                if (error != null) {
                    linearnoData?.visibility = View.VISIBLE
                    Log.v(TAG,"Error $error")
                    progressDialog?.dismiss()
                    return@addSnapshotListener
                }
            }
    }
}