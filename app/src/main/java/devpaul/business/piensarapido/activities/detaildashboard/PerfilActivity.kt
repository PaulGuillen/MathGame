package devpaul.business.piensarapido.activities.detaildashboard

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import devpaul.business.piensarapido.activities.start.LoginActivity
import java.util.*

class PerfilActivity : AppCompatActivity() {

    var TAG = "PerfilActivity"

    var btnLogout : Button? = null
    private lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    var textfullname : TextView? = null
    var textemail : TextView? = null
    var textpassword : TextView? = null

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        progressDialog = ProgressDialog(this)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        textfullname = findViewById(R.id.text_full_name)
        textemail = findViewById(R.id.text_email)
        textpassword = findViewById(R.id.text_password)

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

        getUserInformation()

    }


    @SuppressLint("SetTextI18n")
    private fun getUserInformation(){
        progressDialog!!.show()
        progressDialog?.setContentView(R.layout.charge_dialog)
        Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawableResource(android.R.color.transparent)
        firestore.collection(Constants.PATH_USERS)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val name = it.documents[0].data?.get("name")
                    val lastname = it.documents[0].data?.get("lastname")
                    val email = it.documents[0].data?.get("email")
                    val password = it.documents[0].data?.get("password")
                    textfullname?.text = name.toString() +"\r"+lastname.toString()
                    textemail?.text = email.toString()
                    textpassword?.text = password.toString()

                    progressDialog?.dismiss()
                }

            }
            .addOnFailureListener{
                it.printStackTrace()
                progressDialog?.dismiss()
                Log.w(TAG, "Error: Usuario no encontrado")
            }
    }





}