package devpaul.business.piensarapido

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import devpaul.business.piensarapido.activities.start.LoginActivity
import devpaul.business.piensarapido.adapter.OnBoardingViewPagerAdapter
import devpaul.business.piensarapido.model.OnBoardingData
import java.util.ArrayList

class OnBoardingActivity : AppCompatActivity() {

    var onBoardingViewPagerAdapter : OnBoardingViewPagerAdapter? = null
    var tabLayout : TabLayout? = null
    var onBoardingViewPager : ViewPager? = null
    var next : TextView? = null
    var position = 0
    var sharedPreferences : SharedPreferences? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        if(restorePrefData()){
            val i = Intent(applicationContext, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        tabLayout = findViewById(R.id.tab_indicator)
        next = findViewById(R.id.next)

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        //Data
        val onBoardingData:  MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Matemáticas Básicas","Permanecerás cautivo con esta aplicación que ofrece niveles básicos, que serivarán para ejercitar tu mente",R.drawable.first_view))
        onBoardingData.add(OnBoardingData("Niveles Selectivos","Selección del nivel que desees, además de contar con tiempo limitado para resolver la mayor cantidad de problemas.",R.drawable.second_view))
        onBoardingData.add(OnBoardingData("Mente Activa","Mantén tu mente ocupada con estas pruebas de rapidez mental, piensa rápido.",R.drawable.third_view))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        next?.setOnClickListener {
            if (position < onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingData.size){
                savePrefData()
                val i = Intent(applicationContext, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                position = tab!!.position
                if (tab.position == onBoardingData.size - 1){
                    next!!.text = "Empezar"
                }else{
                    next!!.text = "Siguiente"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){

        onBoardingViewPager = findViewById(R.id.screenViewPager);
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)

    }

    private  fun savePrefData(){
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isFirstTImeRun", true)
        editor.apply()
    }

    private fun restorePrefData() : Boolean{
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isFirstTImeRun",false)
    }

}