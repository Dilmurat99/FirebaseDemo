package com.uyghar.firebasedemo

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.uyghar.firebasedemo.databinding.ActivityMainBinding
import com.uyghar.firebasedemo.models.DataModel
import com.uyghar.firebasedemo.models.FBMessage
import com.uyghar.firebasedemo.models.NotificationModel
import com.uyghar.firebasedemo.ui.home.HomeFragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var messageText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        binding.appBarMain.fab.setOnClickListener {
            val prefrences = this.getSharedPreferences("firebase", Context.MODE_PRIVATE)
            val token = prefrences.getString("token","")
            sendNotification("Alo", messageText.text.toString(), 0, token ?: "")
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun sendNotification(title: String, body: String, id: Int, token: String) {
        val notificationModel = NotificationModel(title,body,"high",true)
        val dataModel = DataModel(id)
        //val token = "eQAq9PzySZyVgCz89ToEK2:APA91bFp1lhbwRatjnpoSs8tFKBHXWII64DD0lNxn71psuFHCNRw6fvuTH-VFrLlYcWXRg-ckr2BoN1mgkG13WSn9z-0SD3nM_gqbgGFBMo-svcco8Ed29tCn1gPadIddSSB2QvF_Imx"
        val fbMessage = FBMessage(token,notificationModel, dataModel)
        val gson = GsonBuilder().create()
        val json_param = gson.toJson(fbMessage)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json_param.toRequestBody(mediaType)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL("https://fcm.googleapis.com/fcm/send"))
            .addHeader("Authorization","key=AAAAHA1uUKg:APA91bEqm5WPLFi_PWEGAvimrMOOLH8afJJeph0csfcNCUOuOMIup-dWD-7h3O4ueIbx4AYXLXKCG8qt9JUc0wDt8vti9Xv0k1q1ItIAryLAri8W6kJpgVAWZi4UARjeXVJqsMFDDkhi")
            .post(body)
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                Log.i("response", res ?: "")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}