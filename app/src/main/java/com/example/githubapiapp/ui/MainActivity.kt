package com.example.githubapiapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapiapp.databinding.ActivityMainBinding
import com.example.githubapiapp.ui.adapter.UserAdapter
import com.example.githubapiapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userAdapter = UserAdapter()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) { // Check if it's the "Done" action
                        val query = searchView.text.toString()
                        mainViewModel.searchUser(query) // Trigger search
                        searchView.hide()
                        return@setOnEditorActionListener true
                    }
                    false
                }
        }

        binding.rvAkun.layoutManager = LinearLayoutManager(this)
        binding.rvAkun.adapter = userAdapter
        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { textView, actionId, event ->
            binding.searchView.hide()
            mainViewModel.searchUser(binding.searchView.text.toString())
            false
        }
        mainViewModel.listUser.observe(this) { akun ->
            userAdapter.submitList(akun)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        mainViewModel.isEmpty.observe(this) {
            binding.tvEmpty.isVisible = it
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
