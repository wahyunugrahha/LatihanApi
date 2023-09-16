package com.example.githubapiapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.githubapiapp.R
import com.example.githubapiapp.databinding.ActivityDetailUserBinding
import com.example.githubapiapp.ui.adapter.SectionsPagerAdapter
import com.example.githubapiapp.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_LOGIN)

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }
        detailViewModel.getDetailUser(username.toString())

        detailViewModel.detailUser.observe(this) {
            with(binding) {
                tvTotalFollowers.text = it.followers.toString()
                tvTotalFollowing.text = it.following.toString()
                tvUsername.text = it.login
                tvFullname.text = it.name
                tvTotalCommit.text = it.publicRepos.toString()
                Glide.with(binding.root)
                    .load(it.avatarUrl)
                    .into(binding.ivAvatar)
                    .clearOnDetach()
            }
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        const val EXTRA_LOGIN = "login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}

