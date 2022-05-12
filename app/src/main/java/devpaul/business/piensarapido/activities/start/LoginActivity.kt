package devpaul.business.piensarapido.activities.start


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import java.util.*

class LoginActivity : AppCompatActivity() {

    var TAG = "LoginActivity"

    //Vistas necesarias
    private var viewRegister: Button? = null
    var edtEmail: EditText? = null
    var edtPassword: EditText? = null
    private var btnIniciarsesion: Button? = null

    private lateinit var auth: FirebaseAuth

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        progressDialog = ProgressDialog(this)

        auth = Firebase.auth
        viewRegister = findViewById(R.id.imgb_next)
        edtEmail = findViewById(R.id.edt_correo)
        edtPassword = findViewById(R.id.edt_password)
        btnIniciarsesion = findViewById(R.id.btn_ingresar)

        btnIniciarsesion?.setOnClickListener {
            goToMainView()
        }

        viewRegister?.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }

    }

    private fun goToMainView() {
        val email = edtEmail?.text.toString()
        val password = edtPassword?.text.toString()

        if (isValidForm(email = email, password = password)) {
            progressDialog!!.show()
            progressDialog?.setContentView(R.layout.charge_dialog)
            Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawableResource(android.R.color.transparent)
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Usuario: $email\nContraseña: $password")
                        Toast.makeText(this@LoginActivity, "Usuario autentificado.", Toast.LENGTH_SHORT).show()
                        progressDialog?.dismiss()
                        goToHomeActivity()

                    } else {
                        Toast.makeText(this@LoginActivity, "Usuario no encontrado.", Toast.LENGTH_SHORT).show()
                        progressDialog?.dismiss()
                        Log.w(TAG, "Error: Usuario no encontrado", task.exception)
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            goToHomeActivity()
            finish()
        }
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun isValidForm(email: String, password: String): Boolean {

        if (email.isBlank()) {
            Toast.makeText(this@LoginActivity, "Correo vacío", Toast.LENGTH_LONG).show()
            return false
        }

        if (password.isBlank()) {
            Toast.makeText(this@LoginActivity, "Contraseña vacía", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!email.isEmailValid()) {
            Toast.makeText(this@LoginActivity, "Email incorrecto", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun goToHomeActivity() {
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Eliminar el historial de pantallas
        startActivity(i)
    }
}