package com.uyghar.firebasedemo.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.uyghar.firebasedemo.MainActivity
import com.uyghar.firebasedemo.R
import com.uyghar.firebasedemo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    var binding : FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = _binding
        val root: View = binding!!.root
        (activity as MainActivity).fragmentHomeBinding = binding!!
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}



