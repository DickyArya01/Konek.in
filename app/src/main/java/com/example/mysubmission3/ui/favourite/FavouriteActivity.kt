package com.example.mysubmission3.ui.favourite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission3.data.local.FavouriteUser
import com.example.mysubmission3.data.model.User
import com.example.mysubmission3.databinding.ActivityFavouriteBinding
import com.example.mysubmission3.ui.detail.DetailUserActivity
import com.example.mysubmission3.ui.main.UserAdapter

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var adapter: UserAdapter

    fun getFavouriteUser(): LiveData<List<FavouriteUser>> = viewModel.getFavouriteUser()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favourite User"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
               Intent(this@FavouriteActivity, DetailUserActivity::class.java).also {
                   it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                   it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                   it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatarUrl)
                   startActivity(it)
               }
            }

        })

        binding.apply {
            rvFavouriteUserList.layoutManager = LinearLayoutManager(this@FavouriteActivity)
            rvFavouriteUserList.setHasFixedSize(true)
            rvFavouriteUserList.adapter = adapter

        }

        viewModel.getFavouriteUser().observe(this){
            if (it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(users : List<FavouriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users){
            val listMapped = User(
                user.avatar,
                user.id,
                user.login
            )
            listUser.add(listMapped)
        }
        return listUser
    }

}