package com.example.mysubmission3.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mysubmission3.R
import com.example.mysubmission3.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var _isFavourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val mBundle = Bundle()
        mBundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

        showLoading(true)
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .circleCrop()
                        .into(ivProfile)
                    tvDetailName.text = it.name
                    tvDetailUsername.text = it.login
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                    tvRepo.text = it.repo.toString()
                    if (it.company == null || it.location == null) {
                        tvCompany.text = getString(R.string.null_data)
                        tvLocation.text = getString(R.string.null_data)
                    } else {
                        tvCompany.text = it.company.toString()
                        tvLocation.text = it.location.toString()
                    }


                    CoroutineScope(Dispatchers.IO).launch {
                        val count = viewModel.checkUser(id)
                        withContext(Dispatchers.Main) {
                            if (count != null) {
                                if (count > 0) {
                                    binding.ivDelete.visibility = View.VISIBLE
                                    _isFavourite = true
                                } else {
                                    _isFavourite = false
                                }
                            }
                        }
                    }

                    showLoading(false)
                }
            }
        }

        binding.btnAddFavourite.setOnClickListener {
            _isFavourite = !_isFavourite
            if (_isFavourite) {
                if (username != null && avatar != null) {
                    viewModel.addFavourite(username, avatar, id)
                    Toast.makeText(
                        applicationContext,
                        "Data ditambahkan ke Favourite",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Data sudah ditambahkan ke Favourite",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.ivDelete.setOnClickListener {
            viewModel.removeFavourite(id)
            binding.ivDelete.visibility = View.GONE
            Toast.makeText(
                applicationContext,
                "Berhasil dihapus dari favourite",
                Toast.LENGTH_SHORT
            ).show()

        }


        val sectionPagerAdapter = SectionPagerAdapter(this, mBundle)
        binding.vpDetail.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.vpDetail) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBarDetailProfile.visibility = View.VISIBLE
        else binding.progressBarDetailProfile.visibility = View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}

