package devpaul.business.piensarapido.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.detaildashboard.NewsGameActivity
import devpaul.business.piensarapido.activities.detaildashboard.game.LevelActivity
import devpaul.business.piensarapido.activities.detaildashboard.PerfilActivity
import devpaul.business.piensarapido.activities.detaildashboard.SettingsActivity

class MainActivity : AppCompatActivity() {

    var laySuma : CardView? = null
    var layResta : CardView? = null
    var layMultiplicar : CardView? = null

    var ibsettings : CardView? = null
    var cardViewPerfil : CardView? = null
    var carViewNews : CardView? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        cardViewPerfil = findViewById(R.id.cardview_perfil)
        cardViewPerfil?.setOnClickListener {
            val callInt = Intent(applicationContext, PerfilActivity::class.java)
            startActivity(callInt)
        }

        carViewNews = findViewById(R.id.cardview_noticias)
        carViewNews?.setOnClickListener {
            val callInt = Intent(applicationContext, NewsGameActivity::class.java)
            startActivity(callInt)
        }

        ibsettings = findViewById(R.id.cardview_ajustes)
        ibsettings?.setOnClickListener {
            val callInt = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(callInt)
        }

        laySuma = findViewById(R.id.cardview_suma)
        laySuma?.setOnClickListener {
            val callInt = Intent(applicationContext, LevelActivity::class.java)
            callInt.putExtra("sum","+")
            startActivity(callInt)
        }

        layResta = findViewById(R.id.cardview_resta)
        layResta?.setOnClickListener {
            val callInt = Intent(applicationContext, LevelActivity::class.java)
            callInt.putExtra("rest","-")
            startActivity(callInt)
        }


        layMultiplicar = findViewById(R.id.cardview_multiplicacion)
        layMultiplicar?.setOnClickListener {
            val callInt = Intent(applicationContext, LevelActivity::class.java)
            callInt.putExtra("mult","*")
            startActivity(callInt)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)

    }

}