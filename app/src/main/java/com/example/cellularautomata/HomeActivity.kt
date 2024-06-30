package com.example.cellularautomata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cellularautomata.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.FrameLayout
        ) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.dashboardFragment, R.id.searchFragment,
                R.id.notificationFragment, R.id.profileFragment,R.id.contributorFragment,R.id.aboutAppFragment
            ), binding.DrawerLayout
        )
        setUpToolBar()
        setupNavigation()

    }
    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbar)
//        val actionBarDrawerToggle = ActionBarDrawerToggle(
//            this@HomeActivity,
//            binding.DrawerLayout,
//            binding.toolbar,
//            R.string.open_drawer,
//            R.string.closed_drawer
//        )
//        binding.DrawerLayout.addDrawerListener(actionBarDrawerToggle)
//        actionBarDrawerToggle.syncState()
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun setupNavigation() {

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            if(menuItem.itemId != R.id.logout)
            {
                navController.navigate(menuItem.itemId)
                binding.DrawerLayout.closeDrawers()
            }
            else{
                handleLogout()
            }

            true
        }

        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            navController.navigate(menuItem.itemId)
            true
        }
    }

    private fun handleLogout() {
        val sharedPreferences = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onBackPressed() {
////        val fragment = supportFragmentManager.findFragmentById(R.id.FrameLayout)
////        if (fragment !is HomeFragment) {
////            navController.navigate(R.id.homeFragment)
////            binding.bottomNavView.menu.findItem(R.id.homeFragment)?.isChecked = true
////            binding.navigationView.menu.findItem(R.id.homeFragment)?.isChecked = true
////        } else {
////            super.onBackPressed()
////        }
//        if (!navController.popBackStack()) {
//            super.onBackPressed()
//        }
//    }
}
