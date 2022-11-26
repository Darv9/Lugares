package com.example.lugares.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lugares.R
import com.example.lugares.databinding.FragmentHomeBinding
import com.example.lugares.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var lugarViewModel: HomeViewModel

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
      lugarViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      binding.addLugarFabButton.setOnClickListener{
          findNavController().navigate(R.id.action_nav_lugar_to_addLugarFragment)
      }


      return binding.root
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}