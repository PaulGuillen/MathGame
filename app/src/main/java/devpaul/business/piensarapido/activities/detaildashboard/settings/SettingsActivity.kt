package devpaul.business.piensarapido.activities.detaildashboard.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import devpaul.business.piensarapido.BuildConfig
import devpaul.business.piensarapido.R

class SettingsActivity : AppCompatActivity() {

    private var cTerms: ConstraintLayout? = null
    var cPolicy: ConstraintLayout? = null
    private var cShare: ConstraintLayout? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        cTerms = findViewById(R.id.clinearlayout_terms)
        cPolicy = findViewById(R.id.clinearlayout_policy)
        cShare = findViewById(R.id.clinearlayout_share)

        cShare?.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Piensa Rápido está disponible en: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)

        }

        cTerms?.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data =
                Uri.parse("https://paulguillen.github.io/Think-Terms-Conditions/")
            startActivity(openURL)
        }

        cPolicy?.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data =
                Uri.parse("https://paulguillen.github.io/Think-Privacy/")
            startActivity(openURL)
        }



    }
}