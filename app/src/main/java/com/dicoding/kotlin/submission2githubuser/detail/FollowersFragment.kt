package com.dicoding.kotlin.submission2githubuser.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kotlin.submission2githubuser.R
import com.dicoding.kotlin.submission2githubuser.utils.GithubUserRepo
import kotlinx.android.synthetic.main.followers_fragment.*

class FollowersFragment : Fragment() {
    private lateinit var followersAdapter: FollowersAdapter
    private val userRepo = GithubUserRepo()

    companion object {
        private const val USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.followers_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated{
            showLoading(true)
            val username = arguments?.getString(USERNAME, "username")
            followersAdapter = FollowersAdapter()
            followersAdapter.notifyDataSetChanged()
            rv_followers.layoutManager = LinearLayoutManager(context)
            rv_followers.adapter = followersAdapter


            val followers = userRepo.getService().returnFollowersList(username)
            followersAdapter.setData(followers)
            showLoading(false)
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFragment.visibility = View.VISIBLE
        }else {
            progressBarFragment.visibility = View.GONE
        }
    }
}