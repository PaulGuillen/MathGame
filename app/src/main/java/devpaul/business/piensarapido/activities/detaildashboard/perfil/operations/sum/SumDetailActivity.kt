package devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.sum

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R

class SumDetailActivity : AppCompatActivity() {

    var TAG = "SumDetail"

    private lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    private val db = Firebase.firestore

    var txtName : TextView? = null
    var txtLastTry : TextView? = null
    var txtBestPoints : TextView? = null
    var textlastTimePlayed : TextView? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sum_detail)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        txtName = findViewById(R.id.text_name)
        txtLastTry = findViewById(R.id.text_lastTry)
        txtBestPoints = findViewById(R.id.text_bestPoints)
        textlastTimePlayed = findViewById(R.id.text_lastimeplayed)

        sumData()
    }

    @SuppressLint("SetTextI18n")
    private fun sumData(){

        val uiduser = auth.currentUser?.uid

        val docRef = db.collection(Constants.PATH_POINTS_SUM).document(uiduser.toString())

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

                } else {
                    txtName?.text = "Nombre"
                    txtBestPoints?.text = "0"
                    txtLastTry?.text = "0"
                    textlastTimePlayed?.text = "0000/00/00"
                }
            } else {
                Log.d(TAG, "get failed with ", task.exception)
            }
        }

    }

}