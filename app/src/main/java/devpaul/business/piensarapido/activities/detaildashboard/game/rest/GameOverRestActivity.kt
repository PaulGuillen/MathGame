package devpaul.business.piensarapido.activities.detaildashboard.game.rest

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class GameOverRestActivity : AppCompatActivity() {

    var TAG = "GameOverResta"

    var tvPoints: TextView? = null
    var sharedPreferences: SharedPreferences? = null
    var ivHighScore: ImageView? = null
    var tvHighScore: TextView? = null
    var tvName: TextView? = null
    var tvLastname: TextView? = null
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over_rest)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        auth = Firebase.auth

        ivHighScore = findViewById(R.id.ivHighScore)
        tvHighScore = findViewById(R.id.tvHighScore)
        tvPoints = findViewById(R.id.tvPoints)

        tvName = findViewById(R.id.nombre)
        tvLastname = findViewById(R.id.apellido)

        sharedPreferences = getSharedPreferences("prefresta", 0)
        val points = intent.extras!!.getInt("points")
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

    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    fun restart(view: View?) {
        val intent = Intent(this@GameOverRestActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun exit(view: View?) {
        finish()
    }

}