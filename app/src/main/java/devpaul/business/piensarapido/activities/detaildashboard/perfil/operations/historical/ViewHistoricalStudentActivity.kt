package devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.historical

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.detaildashboard.perfil.PerfilActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.laderboard.LaderboardActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.res.ResDetailActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.sum.SumDetailActivity
import devpaul.business.piensarapido.adapter.PointsDetailedAdapter
import devpaul.business.piensarapido.model.NewsGame
import devpaul.business.piensarapido.model.PointsDetailed
import java.util.*
import kotlin.collections.ArrayList

class ViewHistoricalStudentActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    var TAG = "ViewAllSection"
    var recyclerViewAll: RecyclerView? = null
    private lateinit var auth: FirebaseAuth

    //ViewAllSection
    var viewAllList = ArrayList<PointsDetailed>()
    private var viewAllAdapter: PointsDetailedAdapter? = null

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null

    var linearnoData: LinearLayout? = null
    var shimmerFrameLayout: ShimmerFrameLayout? = null
    var btnBack: Button ? = null

    var ladeboardData: Button ? = null

    @SuppressLint("SourceLockedOrientationActivity", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_historical_student)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        auth = Firebase.auth

        @Suppress("DEPRECATION")
        progressDialog = ProgressDialog(this)

        val idUser = intent.getStringExtra("userId")
        val type = intent.getStringExtra("type")

        btnBack = findViewById(R.id.btn_back)
        btnBack?.setOnClickListener {
            onBackPressed()
        }


        ladeboardData = findViewById(R.id.laderboard)
        ladeboardData?.setOnClickListener {
            if (type == "Suma"){
                val i = Intent(this, LaderboardActivity::class.java)
                i.putExtra("type", type)
                i.putExtra("userId", idUser)
                startActivity(i)
            }

            if (type == "Resta"){
                val i = Intent(this, LaderboardActivity::class.java)
                i.putExtra("type", type)
                i.putExtra("userId", idUser)
                startActivity(i)
            }

            if (type == "Multiplicacion"){
                val i = Intent(this, LaderboardActivity::class.java)
                i.putExtra("type", type)
                i.putExtra("userId", idUser)
                startActivity(i)
            }
        }

        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout)

        linearnoData = findViewById(R.id.linearlayout_nodata)

        recyclerViewAll = findViewById(R.id.recyclerView)
        recyclerViewAll?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewAllAdapter = PointsDetailedAdapter(this, viewAllList)
        recyclerViewAll?.adapter = viewAllAdapter

        if (type != null && type.equals("Suma", ignoreCase = true)) {
            progressDialog!!.show()
            progressDialog?.setContentView(R.layout.charge_dialog)
            Objects.requireNonNull(progressDialog!!.window)
                ?.setBackgroundDrawableResource(android.R.color.transparent)
            db.collection("AllResultsSum").whereEqualTo("userId", auth.currentUser!!.uid)
                .addSnapshotListener { value, error ->
                    if (value?.isEmpty!!) {
                        progressDialog?.dismiss()
                        linearnoData?.visibility = View.VISIBLE
                    } else {
                        for (doc in value.documentChanges) {
                            progressDialog?.dismiss()
                            linearnoData?.visibility = View.GONE
                            recyclerViewAll?.visibility = View.VISIBLE
                            val section = doc.document.toObject(PointsDetailed::class.java)
                            viewAllList.add(section)
                            viewAllAdapter?.notifyDataSetChanged()
                        }
                    }
                    if (error != null) {
                        linearnoData?.visibility = View.VISIBLE
                        Log.v(TAG, "Error $error")
                        progressDialog?.dismiss()
                        return@addSnapshotListener
                    }
                }

        }

        if (type != null && type.equals("Resta", ignoreCase = true)) {
            progressDialog!!.show()
            progressDialog?.setContentView(R.layout.charge_dialog)
            Objects.requireNonNull(progressDialog!!.window)
                ?.setBackgroundDrawableResource(android.R.color.transparent)
            db.collection("AllResultsRest").whereEqualTo("userId", auth.currentUser!!.uid)
                .addSnapshotListener { value, error ->
                    if (value?.isEmpty!!) {
                        progressDialog?.dismiss()
                        linearnoData?.visibility = View.VISIBLE
                    } else {
                        for (doc in value.documentChanges) {
                            progressDialog?.dismiss()
                            linearnoData?.visibility = View.GONE
                            recyclerViewAll?.visibility = View.VISIBLE
                            val section = doc.document.toObject(PointsDetailed::class.java)
                            viewAllList.add(section)
                            viewAllAdapter?.notifyDataSetChanged()
                        }
                    }
                    if (error != null) {
                        linearnoData?.visibility = View.VISIBLE
                        Log.v(TAG, "Error $error")
                        progressDialog?.dismiss()
                        return@addSnapshotListener
                    }
                }

        }

        if (type != null && type.equals("Multiplicacion", ignoreCase = true)) {
            progressDialog!!.show()
            progressDialog?.setContentView(R.layout.charge_dialog)
            Objects.requireNonNull(progressDialog!!.window)
                ?.setBackgroundDrawableResource(android.R.color.transparent)
            db.collection("AllResultsMul").whereEqualTo("userId", auth.currentUser!!.uid)
                .addSnapshotListener { value, error ->
                    if (value?.isEmpty!!) {
                        progressDialog?.dismiss()
                        linearnoData?.visibility = View.VISIBLE
                    } else {
                        for (doc in value.documentChanges) {
                            progressDialog?.dismiss()
                            linearnoData?.visibility = View.GONE
                            recyclerViewAll?.visibility = View.VISIBLE
                            val section = doc.document.toObject(PointsDetailed::class.java)
                            viewAllList.add(section)
                            viewAllAdapter?.notifyDataSetChanged()
                        }
                    }
                    if (error != null) {
                        linearnoData?.visibility = View.VISIBLE
                        Log.v(TAG, "Error $error")
                        progressDialog?.dismiss()
                        return@addSnapshotListener
                    }
                }

        }

    }



}