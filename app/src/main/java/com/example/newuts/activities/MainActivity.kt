package com.example.newuts.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.newuts.R
import com.example.newuts.adapters.ViewPagerAdapter
import com.example.newuts.databinding.ActivityMainBinding
import com.example.newuts.fragments.JourneyTicketFragment
import com.example.newuts.fragments.PlatformTicketFragment
import com.example.newuts.fragments.QrBookingFragment
import com.example.newuts.fragments.QuickBookingFragment
import com.example.newuts.fragments.SeasonTicketFragment
import com.example.newuts.utils.Uts
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var uts:Uts
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)




        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        uts=Uts(this)
        viewPagerAdapter = ViewPagerAdapter(this)

        viewPagerAdapter.addFragment(JourneyTicketFragment(), "Journey Ticket ")
        viewPagerAdapter.addFragment(QrBookingFragment(), "Qr Booking ")
        viewPagerAdapter.addFragment(QuickBookingFragment(), "Quick Booking ")
        viewPagerAdapter.addFragment(PlatformTicketFragment(), "Platform Ticket ")
        viewPagerAdapter.addFragment(SeasonTicketFragment(), "Season Ticket ")
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
if(intent.getIntExtra("mode",0)==2)
        tabLayout.post {
            tabLayout.getTabAt(3)?.select()
        }
//        val actionBar=findViewById<LinearLayout>(R.id.actionBar)
//        actionBar.findViewById<CardView>(R.id.login).setOnClickListener{
//
//        }



customizeTabs()


    }
    private fun customizeTabs() {

        var images=listOf(
            R.drawable.ticket,
            R.drawable.barcode_svgrepo_com__1_,
            R.drawable.qtick,
            R.drawable.train,
            R.drawable.ticket
            )

        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            tab?.customView = layoutInflater.inflate(R.layout.custom_tab, null)
            val tabTextView = tab?.customView?.findViewById<TextView>(R.id.tabText)
            tabTextView?.text = viewPagerAdapter.getPageTitle(i)
            val tabIconView = tab?.customView?.findViewById<ImageView>(R.id.tabIcon)
            tabIconView?.setImageResource(images[i]) // replace with your icon
            tabIconView?.visibility = View.VISIBLE







        }
    }
    fun login(v: View)
    {
        uts.navigate(this@MainActivity, Profile::class.java)
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        // Reset the flag after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

}