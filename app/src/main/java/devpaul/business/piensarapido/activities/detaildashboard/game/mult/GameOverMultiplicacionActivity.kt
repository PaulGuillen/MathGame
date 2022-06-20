package devpaul.business.piensarapido.activities.detaildashboard.game.mult

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import devpaul.business.piensarapido.activities.detaildashboard.game.LevelActivity
import devpaul.business.piensarapido.model.Points
import java.text.SimpleDateFormat
import java.util.*

class GameOverMultiplicacionActivity : AppCompatActivity() {


    var TAG = "GameOverMultip"

    var tvPoints: TextView? = null
    var sharedPreferences: SharedPreferences? = null
    var ivHighScore: ImageView? = null
    var tvHighScore: TextView? = null

    var tvName : TextView? = null
    var tvLastname : TextView? = null

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    private val database = Firebase.database
    private val myref = database.getReference("Users")

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over_multiplicacion)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        auth = Firebase.auth

        ivHighScore = findViewById(R.id.ivHighScore)
        tvHighScore = findViewById(R.id.tvHighScore)
        val points = intent.extras!!.getInt("points")
        tvPoints = findViewById(R.id.tvPoints)

        tvName = findViewById(R.id.nombre)
        tvLastname = findViewById(R.id.apellido)

        sharedPreferences = getSharedPreferences("prefmult", 0)
        var pointsSP = sharedPreferences?.getInt("pointsSP", 0)
        val editor = sharedPreferences?.edit()
        if (points > pointsSP!!) {
            pointsSP = points
            editor?.putInt("pointsSP", pointsSP)
            editor?.apply()
            ivHighScore?.visibility = View.VISIBLE
        }
        tvPoints?.text = "" + points
        tvHighScore?.text = "" + pointsSP

        getUserData()

    }



    private fun getUserData() : Boolean {
        val user = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid
        val usersRef = user.collection(Constants.PATH_USERS)
        usersRef.document(uid!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val name = document.getString("name")
                    val lastname = document.getString("lastname")
                    tvName?.text = name
                    tvLastname?.text = lastname
                    sendData()

                }

            }
        }

        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendData() {

        FirebaseAuth.getInstance().currentUser?.metadata?.apply {

            val uiduser = auth.currentUser?.uid
            val bestPoints = tvHighScore?.text.toString() + "\r" + "puntos"
            val lastTry = tvPoints?.text.toString() + "\r" + "puntos"
            val name = tvName?.text.toString()
            val lastname = tvLastname?.text.toString()

            // LasTimeJoinTotheApp
            val lastSignInDate = Date(lastSignInTimestamp)
            val lastTimeAccess = SimpleDateFormat("yyyy/MM/dd").format(lastSignInDate)

            // LastTimePLayed
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy/MM/dd")

            val randomUUID = UUID.randomUUID().toString()

            val timePlayed = 30
            val type = "Multiplicacion"

            val level = intent.getStringExtra("level")

            val incorrectAnswers = intent.extras!!.getInt("incorrectAnswers")
            val correctAnswers = intent.extras!!.getInt("points")
            val numberofQuestions = intent.extras!!.getInt("numberQuestions")

            val dataPoints = Points(uiduser.toString(), name, lastname, bestPoints, lastTry, dateInString, lastTimeAccess, incorrectAnswers
                ,numberofQuestions, correctAnswers,timePlayed, type, level)

            db.collection(Constants.PATH_POINTS_MUL).document(uiduser.toString())
                .set(dataPoints)
                .addOnSuccessListener {
                    db.collection("AllResultsMul").document(randomUUID).set(dataPoints);
                    myref.child("AllResultsMul").child(randomUUID).setValue(dataPoints)
                    Log.v(TAG, "Success : $it")
                }
                .addOnFailureListener { e ->
                    Log.v(TAG, "Error : $e")
                }

        }

    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    fun restart(view: View?) {
        val callInt = Intent(applicationContext, LevelActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Eliminar el historial de pantallas
        callInt.putExtra("mult","*")
        startActivity(callInt)
        finish()
    }

    fun exit(view: View?) {
        val intent = Intent(this@GameOverMultiplicacionActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Eliminar el historial de pantallas
        startActivity(intent)
        finish()
    }

}