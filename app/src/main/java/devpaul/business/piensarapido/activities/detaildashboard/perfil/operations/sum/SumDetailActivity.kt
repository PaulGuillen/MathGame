package devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.sum

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
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

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null

    var textfullname : TextView? = null
    var textnumberQuestion : TextView? = null
    var TextIncorrect : TextView? = null
    var textCorrect : TextView? = null
    var textTimePlayed : TextView? = null
    var textTipoOperacion : TextView? = null
    var textbestPoints : TextView? = null

    var pieChart : PieChart? = null

    private val database = Firebase.database
    private val myref = database.getReference("Users")

    var linearnoData: LinearLayout? = null
    var linearData : LinearLayout? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sum_detail)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        linearnoData = findViewById(R.id.linearlayout_nodata)

        linearData = findViewById(R.id.linear_data_exists)

        progressDialog = ProgressDialog(this)

        textfullname = findViewById(R.id.text_fullname)
        textnumberQuestion = findViewById(R.id.text_number_questions)
        TextIncorrect = findViewById(R.id.text_incorrect_general)
        textCorrect = findViewById(R.id.text_correct_general)
        textTimePlayed = findViewById(R.id.text_time_played)
        textTipoOperacion = findViewById(R.id.text_tipo_operacion)
        textbestPoints = findViewById(R.id.text_best_point)

        pieChart = findViewById(R.id.pieChart)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        txtName = findViewById(R.id.text_name)
        txtLastTry = findViewById(R.id.text_lastTry)
        txtBestPoints = findViewById(R.id.text_bestPoints)
        textlastTimePlayed = findViewById(R.id.text_lastimeplayed)

        sumData()

        getUserInformation()

        initPieChart()

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

                    val name = document.getString("name")
                    val lastname = document.getString("lastname")
                    val bestPoints = document.getString("bestPoints")
                    val lastTry = document.getString("lastTry")
                    val lastTimePlayed = document.getString("lastTimePlayed")

                    txtName?.text = name + "\r" + lastname
                    txtBestPoints?.text = bestPoints
                    txtLastTry?.text = lastTry
                    textlastTimePlayed?.text = lastTimePlayed

                    progressDialog?.dismiss()

                } else {
                    txtName?.text = "Nombre"
                    txtBestPoints?.text = "0"
                    txtLastTry?.text = "0"
                    textlastTimePlayed?.text = "0000/00/00"
                    progressDialog?.dismiss()
                }
            } else {
                Log.d(TAG, "get failed with ", task.exception)
                progressDialog?.dismiss()
            }
        }

    }

    private fun getUserInformation(){

        val idUser = intent.getStringExtra("userId")
        val type = intent.getStringExtra("type")


        if (type != null && type.equals("suma", ignoreCase = true)) {
            progressDialog!!.show()
            progressDialog?.setContentView(R.layout.charge_dialog)
            Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawableResource(android.R.color.transparent)
            myref.child("AllResultsSum").orderByChild("userId").equalTo(idUser.toString())
                .addListenerForSingleValueEvent(object  :
                ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    var sumQuestions = 0
                    var correctAnswers = 0
                    var incorrectAnswers = 0
                    var timeplayed = 0

                    if (dataSnapshot.exists()){

                        for (data in dataSnapshot.children) {
                            progressDialog?.dismiss()

                            val name = data .child("name").getValue(String::class.java)!!
                            val lastname = data .child("lastname").getValue(String::class.java)!!
                            val type = data .child("type").getValue(String::class.java)!!
                            val bestPoints = data .child("bestPoints").getValue(String::class.java)!!

                            sumQuestions += data.child("numberofQuestions").getValue(Int::class.java)!!
                            correctAnswers += data.child("correctAnswers").getValue(Int::class.java)!!
                            incorrectAnswers += data.child("incorrectAnswers").getValue(Int::class.java)!!
                            timeplayed += data.child("timePlayed").getValue(Int::class.java)!!

                            textTipoOperacion?.text = type
                            textfullname?.text = name +"\r" + lastname
                            textnumberQuestion?.text = sumQuestions.toString()
                            TextIncorrect?.text = incorrectAnswers.toString()
                            textCorrect?.text = correctAnswers.toString()
                            textTimePlayed?.text = "$timeplayed\rSegundos"
                            textbestPoints?.text = bestPoints


                            pieChart?.setUsePercentValues(true)
                            val dataEntries = ArrayList<PieEntry>()
                            dataEntries.add(PieEntry(textCorrect?.text.toString().toFloat(), "Correctas"))
                            dataEntries.add(PieEntry(TextIncorrect?.text.toString().toFloat(), "Incorrectas"))

                            val colors: ArrayList<Int> = ArrayList()
                            colors.add(Color.parseColor("#4DD0E1"))
                            colors.add(Color.parseColor("#FFF176"))

                            val dataSet = PieDataSet(dataEntries, "")
                            val datachart = PieData(dataSet)
                            datachart.setValueTextSize(15f)

                            // In Percentage
                            datachart.setValueFormatter(PercentFormatter())
                            dataSet.sliceSpace = 3f
                            dataSet.colors = colors
                            pieChart?.data = datachart
                            datachart.setValueTextSize(15f)
                            pieChart?.setExtraOffsets(5f, 10f, 5f, 5f)
                            pieChart?.animateY(1400, Easing.EaseInOutQuad)

                            //create hole in center
                            pieChart?.holeRadius = 58f
                            pieChart?.transparentCircleRadius = 61f
                            pieChart?.isDrawHoleEnabled = true
                            pieChart?.setHoleColor(Color.WHITE)


                            //add text in cente
                            pieChart?.setDrawCenterText(true)
                            pieChart?.centerText = "Estadística"
                            pieChart?.setCenterTextSize(15f)
                            pieChart?.invalidate()

                        }
                    }else{
                        progressDialog?.dismiss()
                        linearnoData?.visibility = View.VISIBLE
                        linearData?.visibility = View.GONE
                        Log.v(TAG,"No data found: $sumQuestions")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    linearnoData?.visibility = View.VISIBLE
                    linearData?.visibility = View.GONE
                    progressDialog?.dismiss()
                    Log.v(TAG,"No data found $error")
                }

            })
        }



    }

    private fun initPieChart() {
        pieChart?.setUsePercentValues(true)
        pieChart?.description?.text = ""
        //hollow pie chart
        pieChart?.isDrawHoleEnabled = false
        pieChart?.setTouchEnabled(false)
        pieChart?.setDrawEntryLabels(false)
        //adding padding
        pieChart?.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart?.setUsePercentValues(true)
        pieChart?.isRotationEnabled = false
        pieChart?.setDrawEntryLabels(false)
        pieChart?.legend?.orientation = Legend.LegendOrientation.VERTICAL
        pieChart?.legend?.isWordWrapEnabled = true

    }


}