package devpaul.business.piensarapido.activities.detaildashboard.game

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import devpaul.business.piensarapido.activities.detaildashboard.game.mult.MultActivity
import devpaul.business.piensarapido.activities.detaildashboard.game.rest.RestActivity
import devpaul.business.piensarapido.activities.detaildashboard.game.sum.SumActivity

class LevelActivity : AppCompatActivity() {


    var btnfacil : CardView? = null
    var btnintermedio : CardView? = null
    var btnavanzado : CardView? = null
    var btnexperto : CardView? = null
    var btnVolver : Button? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        btnVolver = findViewById(R.id.btn_volver_dashboard)
        btnVolver?.setOnClickListener {
            val intent = Intent(this@LevelActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btnfacil = findViewById(R.id.cardview_facil)
        btnfacil?.setOnClickListener {
            val suma= intent.getStringExtra("sum")
            if (suma == "+"){
                val intent = Intent(this@LevelActivity, SumActivity::class.java)
                intent.putExtra("level","facil")
                startActivity(intent)
            }

            val rest= intent.getStringExtra("rest")
            if (rest == "-"){
                val intent = Intent(this@LevelActivity, RestActivity::class.java)
                intent.putExtra("level","facil")
                startActivity(intent)
            }

            val multi= intent.getStringExtra("mult")
            if (multi == "*"){
                val intent = Intent(this@LevelActivity, MultActivity::class.java)
                intent.putExtra("level","facil")
                startActivity(intent)
            }
        }

        btnintermedio = findViewById(R.id.cardview_intermedio)
        btnintermedio?.setOnClickListener {
            val suma= intent.getStringExtra("sum")
            if (suma == "+"){
                val intent = Intent(this@LevelActivity, SumActivity::class.java)
                intent.putExtra("level","intermedio")
                startActivity(intent)
            }

            val rest= intent.getStringExtra("rest")
            if (rest == "-"){
                val intent = Intent(this@LevelActivity, RestActivity::class.java)
                intent.putExtra("level","intermedio")
                startActivity(intent)
            }

            val multi= intent.getStringExtra("mult")
            if (multi == "*"){
                val intent = Intent(this@LevelActivity, MultActivity::class.java)
                intent.putExtra("level","intermedio")
                startActivity(intent)
            }
        }


        btnavanzado = findViewById(R.id.cardview_avanzado)
        btnavanzado?.setOnClickListener {
            val suma= intent.getStringExtra("sum")
            if (suma == "+"){
                val intent = Intent(this@LevelActivity, SumActivity::class.java)
                intent.putExtra("level","avanzado")
                startActivity(intent)
            }

            val rest= intent.getStringExtra("rest")
            if (rest == "-"){
                val intent = Intent(this@LevelActivity, RestActivity::class.java)
                intent.putExtra("level","avanzado")
                startActivity(intent)
            }

            val multi= intent.getStringExtra("mult")
            if (multi == "*"){
                val intent = Intent(this@LevelActivity, MultActivity::class.java)
                intent.putExtra("level","avanzado")
                startActivity(intent)
            }
        }



        btnexperto = findViewById(R.id.cardview_experto)
        btnexperto?.setOnClickListener {
            val suma= intent.getStringExtra("sum")
            if (suma == "+"){
                val intent = Intent(this@LevelActivity, SumActivity::class.java)
                intent.putExtra("level","experto")
                startActivity(intent)
            }

            val rest= intent.getStringExtra("rest")
            if (rest == "-"){
                val intent = Intent(this@LevelActivity, RestActivity::class.java)
                intent.putExtra("level","experto")
                startActivity(intent)
            }

            val multi= intent.getStringExtra("mult")
            if (multi == "*"){
                val intent = Intent(this@LevelActivity, MultActivity::class.java)
                intent.putExtra("level","experto")
                startActivity(intent)
            }
        }
    }
}