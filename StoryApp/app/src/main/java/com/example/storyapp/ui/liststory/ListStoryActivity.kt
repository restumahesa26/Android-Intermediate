package com.example.storyapp.ui.liststory

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.adapter.ListUserAdapter
import com.example.storyapp.factory.ViewModelFactory
import com.example.storyapp.model.ListUserModel
import com.example.storyapp.R
import com.example.storyapp.ui.addnewstory.AddNewStoryActivity
import com.example.storyapp.ui.addnewstory.AddNewStoryViewModel
import com.example.storyapp.ui.detail.DetailActivity
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.databinding.ActivityListStoryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListStoryBinding
    private lateinit var listStoryViewModel: ListStoryViewModel
    private lateinit var addNewStoryViewModel: AddNewStoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val layoutManager = GridLayoutManager(this, 2)
            binding.rvUser.layoutManager = layoutManager
        } else {
            val layoutManager = LinearLayoutManager(this)
            binding.rvUser.layoutManager = layoutManager
        }

        listStoryViewModel.listUsers.observe(this, {
            showRecylerList(it)
        })

        listStoryViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        listStoryViewModel.isMessage.observe(this, {
            showToast(it)
        })

        setupAction()
        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                listStoryViewModel.logout()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return true
            }
            else -> {
                return true
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.title = "List Story"
    }

    private fun setupViewModel() {
        listStoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[ListStoryViewModel::class.java]

        addNewStoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[AddNewStoryViewModel::class.java]
    }

    private fun setupAction() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, AddNewStoryActivity::class.java))
        }
    }

    private fun showRecylerList(user: ArrayList<ListUserModel>) {
        val adapter = ListUserAdapter(user)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUserModel) {
                val intent = Intent(this@ListStoryActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.NAME_ID, data.name)
                intent.putExtra(DetailActivity.DESCRIPTION_ID, data.description)
                intent.putExtra(DetailActivity.PHOTO_URL_ID, data.photoUrl)

                val image = findViewById<ImageView>(R.id.imageViewUser)
                val name = findViewById<TextView>(R.id.tvNameUser)
                val description = findViewById<TextView>(R.id.tvDescriptionUser)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@ListStoryActivity,
                    Pair(image, "image"),
                    Pair(name, "name"),
                    Pair(description, "description")
                )

                startActivity(intent, optionsCompat.toBundle())
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ListStoryActivity, message, Toast.LENGTH_SHORT).show()
    }
}