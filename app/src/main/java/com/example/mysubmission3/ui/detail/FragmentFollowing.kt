package com.example.mysubmission3.ui.detail
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission3.R
import com.example.mysubmission3.databinding.FragmentFollowBinding
import com.example.mysubmission3.ui.main.UserAdapter

class FragmentFollowing: Fragment(R.layout.fragment_follow){
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        binding.apply {
            rvFollowUser.setHasFixedSize(true)
            rvFollowUser.layoutManager = LinearLayoutManager(activity)
            rvFollowUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                adapter.setList(it)
                binding.tvDataEmpty.visibility = View.VISIBLE
                showLoading(false)
            }else{
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean){
        if (state) binding.progressBarDetail.visibility = View.VISIBLE
        else binding.progressBarDetail.visibility = View.GONE
    }
}
