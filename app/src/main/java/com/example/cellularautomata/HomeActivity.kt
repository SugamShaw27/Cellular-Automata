package com.example.cellularautomata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import Fragments.ContriAboutAppFragment
import Fragments.DashboardFragment
import Fragments.HomeFragment
import Fragments.NotificationFragment
import Fragments.ProfileFragment
import Fragments.SearchFragment
import com.example.cellularautomata.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    var previousMenuItem: MenuItem? = null
    var previousBottomItem: MenuItem? = null
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navController = findNavController(R.id.FrameLayout)
        binding.bottomNavView.setupWithNavController(navController)


        setUpToolBar()
        bottomNavigationSelected()
        drawerNavigationSelected()
    }

    private fun drawerNavigationSelected() {
        binding.navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when (it.itemId) {
                R.id.nav_homeFragment -> {
                    openFragment(HomeFragment())
                    binding.bottomNavView.menu.findItem(R.id.homeFragment)?.isChecked = true
                }

                R.id.nav_dashboardFragment -> {
                    openFragment(DashboardFragment())
                    binding.bottomNavView.menu.findItem(R.id.dashboardFragment)?.isChecked = true
                }

                R.id.nav_searchFragment -> {
                    openFragment(SearchFragment())
                    binding.bottomNavView.menu.findItem(R.id.searchFragment)?.isChecked = true
                }

                R.id.nav_notificationFragment -> {
                    openFragment(NotificationFragment())
                    binding.bottomNavView.menu.findItem(R.id.notificationFragment)?.isChecked = true

                }

                R.id.nav_profileFragment -> {
                    openFragment(ProfileFragment())
                    binding.bottomNavView.menu.findItem(R.id.profileFragment)?.isChecked = true
                }

                R.id.contriAboutAppFragment -> {
                    navigateToFragment("Contributor section bro")

                }
                R.id.nav_AboutApp -> {
                    navigateToFragment("About section bro")
                }

                R.id.nav_logout -> {
                    val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                }
            }
            binding.DrawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    private fun bottomNavigationSelected() {
        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    binding.navigationView.menu.findItem(R.id.nav_homeFragment)?.isChecked = true
                    openFragment(HomeFragment())
                }

                R.id.dashboardFragment -> {
                    binding.navigationView.menu.findItem(R.id.nav_dashboardFragment)?.isChecked = true
                    openFragment(DashboardFragment())
                }

                R.id.searchFragment -> {
                    binding.navigationView.menu.findItem(R.id.nav_searchFragment)?.isChecked = true
                    openFragment(SearchFragment())
                }

                R.id.notificationFragment -> {
                    binding.navigationView.menu.findItem(R.id.nav_notificationFragment)?.isChecked = true
                    openFragment(NotificationFragment())
                }

                R.id.profileFragment -> {
                    binding.navigationView.menu.findItem(R.id.nav_profileFragment)?.isChecked = true
                    openFragment(ProfileFragment())
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun navigateToFragment(message: String) {
        val fragment= ContriAboutAppFragment().apply {
            arguments=Bundle().apply { putString("message",message) }
        }
        binding.bottomNavView.menu.findItem(R.id.homeFragment)?.isChecked = true
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportActionBar?.title = when (fragment) {
            is HomeFragment -> "Cellular Automata"
            is DashboardFragment -> "Dashboard"
            is SearchFragment -> "Search"
            is NotificationFragment -> "Notification"
            is ProfileFragment -> "Profile"
            is ContriAboutAppFragment -> "About App"
            else -> "Nothing"
        }
        supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragment).commit()
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbar)
        openFragment(HomeFragment())
        binding.bottomNavView.menu.findItem(R.id.homeFragment)?.isChecked = true
        binding.navigationView.menu.findItem(R.id.nav_homeFragment)?.isChecked = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@HomeActivity,
            binding.DrawerLayout,
            binding.toolbar,
            R.string.open_drawer,
            R.string.closed_drawer
        )
        binding.DrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.homeFragment) {
            binding.DrawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.FrameLayout)
        if (fragment !is HomeFragment) {
            openFragment(HomeFragment())
            binding.bottomNavView.menu.findItem(R.id.homeFragment)?.isChecked = true
            binding.navigationView.menu.findItem(R.id.nav_homeFragment)?.isChecked = true
        } else {
            finish()
            super.onBackPressed()
        }
    }

}
