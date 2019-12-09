package com.creativeapps.partyapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.creativeapps.partyapp.R
import com.creativeapps.partyapp.data.db.AppDatabase
import com.creativeapps.partyapp.data.db.entities.User
import com.creativeapps.partyapp.data.network.MyApi
import com.creativeapps.partyapp.data.network.NetworkConnectionInterceptor
import com.creativeapps.partyapp.data.repositories.UserRepository
import com.creativeapps.partyapp.databinding.ActivityLoginBinding
import com.creativeapps.partyapp.ui.home.HomeActivity
import com.creativeapps.partyapp.util.hide
import com.creativeapps.partyapp.util.show
import com.creativeapps.partyapp.util.snackbar
import com.creativeapps.partyapp.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
            progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}