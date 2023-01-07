package ru.netology.linked.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.linked.R
import ru.netology.linked.presentation.auth.AppAuth
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : AppCompatActivity(R.layout.activity_app) {

    @Inject
    lateinit var appAuth: AppAuth

    private val viewModel: AuthViewModel by viewModels()

    private var currentMenuProvider: MenuProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dataAuth.observe(this) {
            currentMenuProvider?.also(::removeMenuProvider)
            addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                    val authorized = viewModel.authorized
                    menu.setGroupVisible(R.id.authorized, authorized)
                    menu.setGroupVisible(R.id.unauthorized, !authorized)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                    when (menuItem.itemId) {
                        R.id.signIn -> {
                            viewModel.signIn()
                            true
                        }
                        R.id.signUp -> {
                            viewModel.signUp()
                            true
                        }
                        R.id.logout -> {
                            viewModel.signOut()
                            true
                        }
                        else -> false
                    }


            }.apply {
                currentMenuProvider = this
            })
        }

        viewModel.token.observe(this) { token ->
            println("Token ${token.id} ${token.token}")
            appAuth.setAuth(token.id, token.token ?: "")
            supportFragmentManager.popBackStack()
        }

        viewModel.signUpSignal.observe(this) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity, SignUpFragment())
                .addToBackStack("SignUp")
                .commit()
        }
    }
}