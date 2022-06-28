package devpaul.business.piensarapido.activities.detaildashboard.perfil

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import devpaul.business.piensarapido.Constants
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.activities.MainActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.mul.MulDetailActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.res.ResDetailActivity
import devpaul.business.piensarapido.activities.detaildashboard.perfil.operations.sum.SumDetailActivity
import devpaul.business.piensarapido.activities.login.LoginActivity
import devpaul.business.piensarapido.model.User
import java.util.*

class PerfilActivity : AppCompatActivity() {

    var TAG = "PerfilActivity"

    var btnLogout : Button? = null

    private lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    private val db = Firebase.firestore

    var textfullname : TextView? = null
    var textemail : TextView? = null
    var textpassword : TextView? = null
    var textuserId : TextView? = null

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null

    var btnSumar : Button ? = null
    var btnRestar : Button ? = null
    var btnMultiplicar : Button ? = null

    var textBestPoints : TextView ? = null
    var textLastTry : TextView ? = null
    var textlastTimePlayed : TextView ? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        progressDialog = ProgressDialog(this)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        btnSumar = findViewById(R.id.sumar_data)
        btnSumar?.setOnClickListener{
            sumView()
        }
        btnRestar = findViewById(R.id.restar_data)
        btnRestar?.setOnClickListener{
            resView()
        }
        btnMultiplicar = findViewById(R.id.multiplicar_data)
        btnMultiplicar?.setOnClickListener{
            mulView()
        }


        textBestPoints = findViewById(R.id.text_best_points)
        textLastTry = findViewById(R.id.text_last_points)
        textlastTimePlayed = findViewById(R.id.textview_lasttime_played)

        textfullname = findViewById(R.id.text_full_name)
        textemail = findViewById(R.id.text_email)
        textpassword = findViewById(R.id.text_password)
        textuserId = findViewById(R.id.userId)

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

        val uiduser = auth.currentUser?.uid

        progressDialog!!.show()
        progressDialog?.setContentView(R.layout.charge_dialog)
        Objects.requireNonNull(progressDialog!!.window)?.setBackgroundDrawableResource(android.R.color.transparent)

        val docRef =   db.collection(Constants.PATH_USERS).document(uiduser.toString())

          docRef.get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {

                        val name = document.getString("name")
                        val lastname = document.getString("lastname")
                        val email = document.getString("email")
                        val password = document.getString("password")
                        val userId = document.getString("userId")
                        val created = document.getString("created")

                        textfullname?.text = name.toString() +"\r"+lastname.toString()
                        textemail?.text = email.toString()
                        textpassword?.text = password.toString()
                        textuserId?.text = userId.toString()
                        textlastTimePlayed?.text = created.toString()
                        progressDialog?.dismiss()


                    } else {
                        textBestPoints?.text = "0"
                        textLastTry?.text = "0"
                        textlastTimePlayed?.text = "0000/00/00"
                        progressDialog?.dismiss()
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.exception)
                    progressDialog?.dismiss()
                }

            }
            .addOnFailureListener{
                it.printStackTrace()
                progressDialog?.dismiss()
                Log.w(TAG, "Error: Usuario no encontrado")
            }
    }

    private fun sumView(){
        val i = Intent(this@PerfilActivity, SumDetailActivity::class.java)
        i.putExtra("type", "Suma")
        i.putExtra("userId", textuserId?.text)
        Log.v(TAG,"Data: ${textuserId?.text}")
        startActivity(i)
    }

    private fun resView(){
        val i = Intent(this@PerfilActivity, ResDetailActivity::class.java)
        i.putExtra("type", "Resta")
        i.putExtra("userId", textuserId?.text)
        Log.v(TAG,"Data: ${textuserId?.text}")
        startActivity(i)
    }

    private fun mulView(){
        val i = Intent(this@PerfilActivity, MulDetailActivity::class.java)
        i.putExtra("type", "Resta")
        i.putExtra("userId", textuserId?.text)
        Log.v(TAG,"Data: ${textuserId?.text}")
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        textBestPoints?.text = "0"
        textLastTry?.text = "0"
        textlastTimePlayed?.text = "0000/00/00"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@PerfilActivity , MainActivity::class.java)
        startActivity(i)
        finish()
    }

}