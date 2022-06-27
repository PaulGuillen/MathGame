package devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.sum

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.PerfilActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.historical.ViewHistoricalStudentActivity
import devpaul.business.piensarapido.adapter.PointsAdapter
import devpaul.business.piensarapido.model.Points
import java.lang.Exception
import java.util.*

class SumDetailActivity : AppCompatActivity() {

    var TAG = "SumDetail"

    private lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    private val db = Firebase.firestore

    var txtName : TextView? = null
    var txtLastTry : TextView? = null
    var txtBestPoints : TextView? = null
    var textlastTimePlayed : TextView? = null
    var btnBack: Button ? = null

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null

    var linearnoData: LinearLayout? = null
    var cardViewNodata: CardView? = null
    var cardviewData: CardView? = null

    private var recyclerViewAll: RecyclerView? = null
    var shimmerFrameLayout : ShimmerFrameLayout? = null
    var btnHistorical: Button? = null

    //ViewAllSection
    var viewAllList = ArrayList<Points>()
    private var viewAllAdapter: PointsAdapter? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sum_detail)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        linearnoData = findViewById(R.id.linearlayout_nodata)
        cardViewNodata = findViewById(R.id.cardview_no_data)
        cardviewData = findViewById(R.id.cardview_top)

        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout)

        progressDialog = ProgressDialog(this)

        btnHistorical = findViewById(R.id.view_history)
        btnHistorical?.setOnClickListener {
            val i = Intent(this@SumDetailActivity, ViewHistoricalStudentActivity::class.java)
            i.putExtra("type", "Suma")
            startActivity(i)

        }

        btnBack = findViewById(R.id.btn_back)
        btnBack?.setOnClickListener {
            val intent = Intent(this@SumDetailActivity, PerfilActivity::class.java)
            startActivity(intent)
            finish()
        }

        recyclerViewAll = findViewById(R.id.recyclerView)
        recyclerViewAll?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewAll?.setHasFixedSize(true)
        viewAllAdapter = PointsAdapter(this, viewAllList)
        recyclerViewAll?.adapter = viewAllAdapter

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        txtName = findViewById(R.id.text_name)
        txtLastTry = findViewById(R.id.text_lastTry)
        txtBestPoints = findViewById(R.id.text_bestPoints)
        textlastTimePlayed = findViewById(R.id.text_lastimeplayed)


        if (isOnline()){
            sumData()
            getDatSumStudents()
        }else{
            getConnectionValidation()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun sumData(){

        val uiduser = auth.currentUser?.uid

        val docRef = db.collection(Constants.PATH_POINTS_SUM).document(uiduser.toString())

        progressDialog!!.show()
        progressDialog?.setContentView(R.layout.charge_dialog)
        Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawableResource(android.R.color.transparent)

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists()) {

                    progressDialog?.dismiss()
                    val name = document.getString("name")
                    val lastname = document.getString("lastname")
                    val bestPoints = document.getLong("bestPoints")?.toInt()
                    val lastTry = document.getString("lastTry")
                    val lastTimePlayed = document.getString("lastTimePlayed")

                    txtName?.text = name
                    txtBestPoints?.text = bestPoints.toString() + "\r" + "puntos"
                    txtLastTry?.text = lastTry
                    textlastTimePlayed?.text = lastTimePlayed

                } else {
                    progressDialog?.dismiss()
                    cardViewNodata?.visibility = View.VISIBLE
                    cardviewData?.visibility = View.GONE

                }
            } else {
                Log.d(TAG, "get failed with ", task.exception)

            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDatSumStudents() {
        //PUNTAJE DE MAYOR A MENOR, CAMPO = MEJOR PUNTAJE
        db.collection(Constants.PATH_POINTS_SUM).orderBy("bestPoints", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    for (document in documents) {
                        linearnoData?.visibility = View.GONE
                        shimmerFrameLayout?.visibility = View.GONE
                        recyclerViewAll?.visibility = View.VISIBLE
                        val section = document.toObject(Points::class.java)
                        viewAllList.add(section)
                        viewAllAdapter?.notifyDataSetChanged()
                    }
                }else{
                    linearnoData?.visibility = View.VISIBLE
                    shimmerFrameLayout?.visibility = View.GONE
                    recyclerViewAll?.visibility = View.GONE
                }

            }
            .addOnFailureListener { exception ->
                shimmerFrameLayout?.visibility = View.GONE
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

    private fun getConnectionValidation() {
        try {
            val customDialog = Dialog(this)
            customDialog.setContentView(R.layout.connection_dialog)
            customDialog.show()
            val mylamda = Thread {
                for (x in 0..10) {
                    Thread.sleep(3500)
                    customDialog.dismiss()
                }
            }
            startThread(mylamda)
        } catch (e: Exception) {
            Log.v(TAG, "Error: $e");
        }
    }

    private fun startThread(mylamda: Thread) {
        mylamda.start()
    }

    @Suppress("DEPRECATION")
    private fun isOnline(): Boolean {
        val conMgr = this.applicationContext
            .getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        if (netInfo == null || !netInfo.isConnected || !netInfo.isAvailable) {
            Log.v(TAG, "Error: $netInfo");
            /*     Toast.makeText(this@ViewAllSectionActivity, "Sin conexion a internet!", Toast.LENGTH_LONG).show()*/
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout?.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout?.stopShimmerAnimation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SumDetailActivity, PerfilActivity::class.java)
        startActivity(intent)
        finish()
    }

}