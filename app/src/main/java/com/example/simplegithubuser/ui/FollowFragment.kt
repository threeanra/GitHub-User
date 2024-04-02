package com.example.simplegithubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplegithubuser.adapter.UserAdapter
import com.example.simplegithubuser.databinding.FragmentFollowBinding
import com.example.simplegithubuser.network.response.ItemsItem
import com.example.simplegithubuser.viewmodel.MainViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    companion object{
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }

    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView

    var position: Int? = null
    lateinit var username: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get data from bundle
        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME).toString()
        }

        // declaration view to variabel
        recyclerView = binding.rvFollow

        // set data to viewModel
        viewModel.getFollowUSer(username)

        // get change data from viewModel
        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        // set layout for recyclerview
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        recyclerView.addItemDecoration(itemDecoration)

        // declaration adapter to variabel
        adapter = UserAdapter()

        // for tab with diffirent data
        if (position == 1){
            viewModel.userFollower.observe(viewLifecycleOwner) {
                setFollowData(it)
            }
        } else {
            viewModel.userFollowing.observe(viewLifecycleOwner) {
                setFollowData(it)
            }
        }

        // set adapter to recycler view
        binding.rvFollow.adapter = adapter

        viewModel.isLoading.observe(viewLifecycleOwner){ load ->
            showLoading(load)
        }



    }

    // set data from viewModel to adapter
    private fun setFollowData(Fol: List<ItemsItem>) {
        adapter.submitList(Fol)
    }

    // show and hidden Progress Bar to loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    // if fragment closed
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}