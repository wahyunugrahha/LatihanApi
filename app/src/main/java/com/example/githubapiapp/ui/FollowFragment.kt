package com.example.githubapiapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapiapp.data.response.GithubResponseDetail
import com.example.githubapiapp.data.response.ItemsItem
import com.example.githubapiapp.databinding.FragmentFollowBinding
import com.example.githubapiapp.ui.adapter.UserAdapter
import com.example.githubapiapp.ui.viewmodel.FollowViewModal

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = requireActivity().intent.getStringExtra(DetailUserActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.rvGithub.layoutManager = layoutManager

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it

        }
        followViewModel.isEmpty.observe(viewLifecycleOwner) {
            binding.tvEmpty.isVisible = it
        }
        if (index == 1) {
            followViewModel.getFollowers(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner) {
                setAdapter(it)
            }
        } else {
            followViewModel.getFollowings(username.toString())
            followViewModel.listFollowings.observe(viewLifecycleOwner) {
                setAdapter(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setAdapter(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvGithub.adapter = adapter
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}