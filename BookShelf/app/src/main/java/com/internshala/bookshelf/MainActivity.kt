package com.internshala.bookshelf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout:DrawerLayout
    lateinit var navigationView:NavigationView
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    var  previousMenuItem:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    previousMenuItem
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawerLayout)
        navigationView=findViewById(R.id.navigationView)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frame)
        setUpToolBar()
        val actionBarDrawerToggle= ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    openDashboard()
    navigationView.setNavigationItemSelectedListener {
        if(previousMenuItem!=null){
            previousMenuItem?.isChecked=false
        }
        it.isCheckable=true
        it.isChecked=true
        previousMenuItem=it
        when(it.itemId){
            R.id.profile->{
                supportFragmentManager.beginTransaction().replace(R.id.frame,ProfileFragment()).commit()
                supportActionBar?.title="Profile"
                drawerLayout.closeDrawers()
            }
            R.id.about->{
                supportFragmentManager.beginTransaction().replace(R.id.frame,AboutFragment()).commit()
                supportActionBar?.title="About"
                drawerLayout.closeDrawers()
            }
            R.id.favourites->{
                supportFragmentManager.beginTransaction().replace(R.id.frame,FavouriteFragment()).commit()
                supportActionBar?.title="Favourites"
                drawerLayout.closeDrawers()
            }
            R.id.dashboard->{
                openDashboard()
                drawerLayout.closeDrawers()
            }
        }
        return@setNavigationItemSelectedListener true
    }

    }
    fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Stall"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openDashboard(){
        val fragment=DashboardFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,DashboardFragment()).commit()
        navigationView.setCheckedItem(R.id.dashboard)
        supportActionBar?.title="Dashboard"


    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame)
        when(frag){
            is DashboardFragment-> super.onBackPressed()
            else-> openDashboard()
        }
    }
}