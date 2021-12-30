package com.uyghar.firebasedemo.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.uyghar.firebasedemo.R
import com.uyghar.firebasedemo.databinding.FragmentHomeBinding
import com.uyghar.firebasedemo.databinding.FragmentUserBinding
import com.uyghar.firebasedemo.models.DataModel
import com.uyghar.firebasedemo.models.FBMessage
import com.uyghar.firebasedemo.models.MyUser
import com.uyghar.firebasedemo.models.NotificationModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentUserBinding? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val prefrences = activity?.getSharedPreferences("firebase", Context.MODE_PRIVATE)
        binding.buttonSend.setOnClickListener {
            val token = prefrences?.getString("token","")
            val name = binding.editName.text.toString()
            sendUser(name, token ?: "")
        }



        return binding.root
    }

    fun sendUser(name: String, token: String) {
        val myUser = MyUser(name, token)
        val gson = GsonBuilder().create()
        val json_param = gson.toJson(myUser)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json_param.toRequestBody(mediaType)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL("http://172.104.143.75:8004/api/ezalar/"))
            //.addHeader("Authorization","key=AAAAHA1uUKg:APA91bEqm5WPLFi_PWEGAvimrMOOLH8afJJeph0csfcNCUOuOMIup-dWD-7h3O4ueIbx4AYXLXKCG8qt9JUc0wDt8vti9Xv0k1q1ItIAryLAri8W6kJpgVAWZi4UARjeXVJqsMFDDkhi")
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}