package devpaul.business.piensarapido

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class OnBoardingActivity : AppCompatActivity() {

    var onBoardingViewPagerAdapter : OnBoardingViewPagerAdapter? = null
    var tabLayout : TabLayout? = null
    var onBoardingViewPager : ViewPager? = null
    var next : TextView? = null
    var position = 0
    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
    }
}