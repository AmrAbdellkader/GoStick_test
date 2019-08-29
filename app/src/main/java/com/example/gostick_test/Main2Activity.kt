package com.example.gostick_test

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.view.GravityCompat
import com.example.gostick_test.main2ActivityFragments.FragPlayStationsList
import com.google.firebase.auth.FirebaseAuth
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.nav_header_main2.view.*

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        drawerLayout()
    }

    override fun onStart() {
        super.onStart()
        navigateToFragPlayStationsList()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_my_account -> {
                com.example.gostick_test.ui.FragMyAccount().show(supportFragmentManager,"pop")
            }
            R.id.nav_reservations -> {
                com.example.gostick_test.ui.FragMyReservations().show(supportFragmentManager,"pop")
            }
            R.id.nav_language -> {
                com.example.gostick_test.ui.FragLanguage().show(supportFragmentManager,"pop")
            }
            R.id.nav_contact_us -> { }
            R.id.nav_rate -> {
                try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.android.chrome")))
                }catch (e: ActivityNotFoundException){
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + "com.android.chrome")))
                }
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) { drawerLayout.closeDrawer(GravityCompat.START) }
        else { super.onBackPressed() }
    }

    private fun drawerLayout(){
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val header = navView.getHeaderView(0)

        header.txt_nav_header_email.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        header.txt_nav_header_user_name.text = FirebaseAuth.getInstance().currentUser?.displayName.toString()

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    private fun navigateToFragPlayStationsList(){
        supportFragmentManager.beginTransaction().add(R.id.content_main, FragPlayStationsList()).commit()
    }
}
