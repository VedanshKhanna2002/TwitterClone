package com.vedansh.twitterclone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.vedansh.twitterclone.Adapters.ViewPagerAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var floatingbtn : FloatingActionButton
    private lateinit var vpAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout : TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        TabLayoutMediator(tablayout , viewPager) { tab  : TabLayout.Tab , position : Int ->
            when(position){
                0 -> tab.text = "Account"
                else -> tab.text = "Tweets"
            }
        }.attach()
        floatingbtn.setOnClickListener {
            val intent = Intent(this , TweetActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
    private fun init(){
        auth = Firebase.auth
        floatingbtn = findViewById<FloatingActionButton>(R.id.floating_button)
        vpAdapter = ViewPagerAdapter(this)
        viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = vpAdapter
        tablayout = findViewById<TabLayout>(R.id.tab_layout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile -> {
                val intent = Intent(this , ProfileActivity::class.java)
                startActivity(intent)
                Toast.makeText(this , "Profile is Clicked" , Toast.LENGTH_LONG).show()

            }
            R.id.Login_out -> {
                auth.signOut()
                val intent = Intent(this , LoginActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this , "You have been successfully Logged Out" , Toast.LENGTH_LONG).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}