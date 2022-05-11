package devpaul.business.piensarapido.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import devpaul.business.piensarapido.R
import devpaul.business.piensarapido.model.OnBoardingData

class OnBoardingViewPagerAdapter(
    private var context : Context,
    private var onboardingDataList : List<OnBoardingData>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        //Ctrl + Alta y la tecla llaves
      return view == `object`
    }

    override fun getCount(): Int {
        return onboardingDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as View)
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val View = LayoutInflater.from(context).inflate(R.layout.onboarding_screen_layout, null)
        val imageView: ImageView
        val title : TextView
        val desc : TextView

        imageView = View.findViewById(R.id.imageView)
        title = View.findViewById(R.id.tittle)
        desc = View.findViewById(R.id.desc)

        imageView.setImageResource(onboardingDataList[position].imageUrl)
        title.text = onboardingDataList[position].tittle
        desc.text = onboardingDataList[position].desc

        container.addView(View)
        return View
    }
}