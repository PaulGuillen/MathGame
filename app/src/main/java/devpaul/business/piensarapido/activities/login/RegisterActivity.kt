package devpaul.business.piensarapido.activities.login

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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import devpaul.business.piensarapido.model.User
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var TAG = "RegisterActivity"

    //Vistas Necesarias
    var edtName: EditText? = null
    var edtLastname: EditText? = null
    var edtEmail: EditText? = null
    var edtPassword: EditText? = null
    var edtConfirmPassword: EditText? = null
    var btnRegistrar: Button? = null
    var viewMain: Button? = null

    //Firebase extensiones
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        @Suppress("DEPRECATION")
        progressDialog = ProgressDialog(this)

        auth = Firebase.auth
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtLastname = findViewById(R.id.edt_lastname)
        edtPassword = findViewById(R.id.edt_password)
        edtConfirmPassword = findViewById(R.id.edt_confirm_password)
        btnRegistrar = findViewById(R.id.btn_registrar)
        viewMain = findViewById(R.id.imgb_back)

        btnRegistrar?.setOnClickListener {
            registerUser()
        }

        viewMain?.setOnClickListener {
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(i)

        }
    }

    private fun registerUser() {

        val name = edtName?.text.toString()
        val lastname = edtLastname?.text.toString()
        val email = edtEmail?.text.toString()
        val password = edtPassword?.text.toString()
        val confirmPassword = edtConfirmPassword?.text.toString()
        val rol = "alumno"

        if (isValidForm(
                name = name,
                lastname = lastname,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        ) {

            val user = User(
                userId = "",
                name = name,
                lastname = lastname,
                rol = rol,
                email = email,
                password = password
            )

            progressDialog!!.show()
            progressDialog?.setContentView(R.layout.charge_dialog)
            Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawableResource(android.R.color.transparent)

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        auth.uid?.let { uid ->
                            db.collection(Constants.PATH_USERS)
                                .document(uid)
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(baseContext, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    val usesRef = db.collection("Users").document(auth.uid!!)
                                    usesRef
                                        .update("userId", auth.uid)
                                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
                                    progressDialog?.dismiss()
                                    goToPrincipalView()
                                }
                                .addOnFailureListener { e ->
                                    progressDialog?.dismiss()
                                    Log.w(TAG, "Error adding document", e)
                                }

                        }

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        progressDialog?.dismiss()
                        Toast.makeText(baseContext, "Error en el registro.", Toast.LENGTH_SHORT).show()

                    }
                }

        }

    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    private fun isValidForm(
        name: String,
        lastname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (name.isBlank()) {
            Toast.makeText(this, "Debes ingresar el nombre", Toast.LENGTH_SHORT).show()
            return false
        }

        if (lastname.isBlank()) {
            Toast.makeText(this, "Debes ingresar el apellido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isBlank()) {
            Toast.makeText(this, "Debes ingresar el email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isBlank()) {
            Toast.makeText(this, "Debes ingresar el contraseña", Toast.LENGTH_SHORT).show()
            return false
        }

        if (confirmPassword.isBlank()) {
            Toast.makeText(this, "Debes ingresar el la confirmacion de contraseña", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!email.isEmailValid()) {
            Toast.makeText(this, "El email no es valido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun goToPrincipalView() {
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Eliminar el historial de pantallas
        startActivity(i)
    }

}
