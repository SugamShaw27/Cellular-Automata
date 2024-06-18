package com.example.cellularautomata

import android.app.Notification.Action
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cellularautomata.Fragments.ContriAboutAppFragment
import com.example.cellularautomata.Fragments.DashboardFragment
import com.example.cellularautomata.Fragments.HomeFragment
import com.example.cellularautomata.Fragments.NotificationFragment
import com.example.cellularautomata.Fragments.ProfileFragment
import com.example.cellularautomata.Fragments.SearchFragment
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
        openFragment(HomeFragment())
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@HomeActivity,
            binding.DrawerLayout,
            binding.toolbar,
            R.string.open_drawer,
            R.string.closed_drawer
        )
        binding.DrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    binding.navigationView.menu.findItem(R.id.nav_homeFragment)?.isChecked = true
                    openFragment(HomeFragment())
                }

                R.id.dashboardFragment -> {
                    navController.navigate(R.id.dashboardFragment)
                    binding.navigationView.menu.findItem(R.id.nav_dashboardFragment)?.isChecked = true
                    openFragment(DashboardFragment())
                }

                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    binding.navigationView.menu.findItem(R.id.nav_searchFragment)?.isChecked = true
                    openFragment(SearchFragment())
                }

                R.id.notificationFragment -> {
                    navController.navigate(R.id.notificationFragment)
                    binding.navigationView.menu.findItem(R.id.nav_notificationFragment)?.isChecked = true
                    openFragment(NotificationFragment())
                }

                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    binding.navigationView.menu.findItem(R.id.nav_profileFragment)?.isChecked = true
                    openFragment(ProfileFragment())
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

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
                    navController.navigate(R.id.homeFragment)
                    binding.bottomNavView.menu.findItem(R.id.nav_homeFragment)?.isChecked = true
                }

                R.id.nav_dashboardFragment -> {
                    openFragment(DashboardFragment())
                    navController.navigate(R.id.dashboardFragment)
                    binding.bottomNavView.menu.findItem(R.id.nav_dashboardFragment)?.isChecked = true
                }

                R.id.nav_searchFragment -> {
                    openFragment(SearchFragment())
                    navController.navigate(R.id.searchFragment)
                    binding.bottomNavView.menu.findItem(R.id.nav_searchFragment)?.isChecked = true
                }

                R.id.nav_notificationFragment -> {
                    openFragment(NotificationFragment())
                    navController.navigate(R.id.notificationFragment)
                    binding.bottomNavView.menu.findItem(R.id.nav_notificationFragment)?.isChecked = true

                }

                R.id.nav_profileFragment -> {
                    openFragment(ProfileFragment())
                    navController.navigate(R.id.profileFragment)
                    binding.bottomNavView.menu.findItem(R.id.nav_profileFragment)?.isChecked = true
                }

                R.id.contriAboutAppFragment -> {
                    navController.navigate(R.id.contriAboutAppFragment)
                    navigateToFragment("Contributor section bro")

                }
                R.id.nav_AboutApp -> {
                    navController.navigate(R.id.contriAboutAppFragment)
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

    private fun navigateToFragment(message: String) {
        val fragment = ContriAboutAppFragment().apply {
            arguments=Bundle().apply { putString("message",message) }
        }
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportActionBar?.title = when (fragment) {
            is HomeFragment -> "Home"
            is DashboardFragment -> "Dashboard"
            is SearchFragment -> "Search"
            is NotificationFragment -> "Notification"
            is ProfileFragment -> "Profile"
            is ContriAboutAppFragment -> "About App"
            else -> "CELL"
        }
        supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragment).commit()
//        when (fragment) {
//            is HomeFragment -> {
//                navController.navigate(R.id.homeFragment)
//                binding.bottomNavView.menu.findItem(R.id.homeFragment)?.isChecked = true
//                binding.navigationView.menu.findItem(R.id.homeFragment)?.isChecked = true
//            }
//
//            is DashboardFragment -> {
//                navController.navigate(R.id.dashboardFragment)
//                binding.bottomNavView.menu.findItem(R.id.dashboardFragment)?.isChecked = true
//                binding.navigationView.menu.findItem(R.id.dashboardFragment)?.isChecked = true
//            }
//
//            is SearchFragment -> {
//                navController.navigate(R.id.searchFragment)
//                binding.bottomNavView.menu.findItem(R.id.searchFragment)?.isChecked = true
//                binding.navigationView.menu.findItem(R.id.searchFragment)?.isChecked = true
//            }
//
//            is NotificationFragment -> {
//                navController.navigate(R.id.notificationFragment)
//                binding.bottomNavView.menu.findItem(R.id.notificationFragment)?.isChecked = true
//                binding.navigationView.menu.findItem(R.id.notificationFragment)?.isChecked = true
//            }
//
//            is ProfileFragment -> {
//                navController.navigate(R.id.profileFragment)
//                binding.bottomNavView.menu.findItem(R.id.profileFragment)?.isChecked = true
//                binding.navigationView.menu.findItem(R.id.profileFragment)?.isChecked = true
//            }
//            else->{}
//        }

    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbar)
        openFragment(HomeFragment())
//        supportActionBar?.title = "Cellular Automata"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        when (fragment) {
            !is HomeFragment -> {
                val navController = findNavController(R.id.FrameLayout)
                openFragment(HomeFragment())
                navController.navigate(R.id.homeFragment)
            }

            else -> super.onBackPressed()
        }
    }

}
