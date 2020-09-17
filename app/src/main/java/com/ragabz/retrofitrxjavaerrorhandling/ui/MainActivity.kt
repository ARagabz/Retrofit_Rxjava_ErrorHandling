package com.ragabz.retrofitrxjavaerrorhandling.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ragabz.retrofitrxjavaerrorhandling.*
import com.ragabz.retrofitrxjavaerrorhandling.databinding.ActivityMainBinding
import com.ragabz.retrofitrxjavaerrorhandling.common.extensions.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModelLiveData()

        binding.btnLogin.click {
            val mobileNumber = binding.txtMobileNumber.text.toString()
            // call viewModel login
            viewModel.login(mobileNumber)
        }

        binding.btnGetChallenges.click {
            // call viewModel load challenge list
            viewModel.loadChallengeList()
        }
    }

    fun observeViewModelLiveData() {
        viewModel.errorState.observe(this, {
            binding.txtError.text = it
        })
    }


}

