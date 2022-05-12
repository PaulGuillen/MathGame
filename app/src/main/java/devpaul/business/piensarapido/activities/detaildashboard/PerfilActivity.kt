package devpaul.business.piensarapido.activities.detaildashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import devpaul.business.piensarapido.activities.start.LoginActivity

class PerfilActivity : AppCompatActivity() {

    var btnLogout : Button? = null
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        auth = Firebase.auth
        btnLogout = findViewById(R.id.btn_salir)
        btnLogout?.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Deseas salir?")
                .setCancelText("Cancelar")
                .setConfirmText("Si")
                .setConfirmClickListener {
                    auth.signOut()
                    Toast.makeText(this, "Gracias por visitarnos", Toast.LENGTH_LONG).show()
                    val i = Intent(this, LoginActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Eliminar el historial de pantallas
                    startActivity(i)
                    it.dismiss()
                }
                .showCancelButton(true)
                .setCancelClickListener { sDialog ->
                    sDialog.cancel()
                }.show()

        }

    }
}