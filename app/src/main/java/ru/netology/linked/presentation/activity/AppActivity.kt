package ru.netology.linked.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.linked.R
import ru.netology.linked.presentation.auth.AppAuth
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import ru.netology.linked.presentation.viewmodel.MainViewModel
import ru.netology.linked.presentation.viewmodel.MenuAction
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : AppCompatActivity(R.layout.activity_app) {

    @Inject
    lateinit var appAuth: AppAuth

    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private var currentMenuProvider: MenuProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_activity) as NavHostFragment? ?: return

        val navController = host.navController

        observeViewModel()
        menuNavigation(navController)
    }

    private fun observeViewModel() {
        authViewModel.dataAuth.observe(this) {
            currentMenuProvider?.also(::removeMenuProvider)
            addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                    val authorized = authViewModel.authorized
                    menu.setGroupVisible(R.id.authorized, authorized)
                    menu.setGroupVisible(R.id.unauthorized, !authorized)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                    when (menuItem.itemId) {
                        R.id.signIn -> {
                            authViewModel.signIn()
                            true
                        }
                        R.id.signUp -> {
                            authViewModel.signUp()
                            true
                        }
                        R.id.logout -> {
                            authViewModel.signOut()
                            true
                        }
                        else -> false
                    }


            }.apply {
                currentMenuProvider = this
            })
        }

        authViewModel.token.observe(this) { token ->
            println("Token ${token.id} ${token.token}")
            appAuth.setAuth(token.id, token.token ?: "")
            supportFragmentManager.popBackStack()
        }

        authViewModel.signUpSignal.observe(this) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity, SignUpFragment())
                .addToBackStack("SignUp")
                .commit()
        }

        authViewModel.signInSignal.observe(this) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity, SignInFragment())
                .addToBackStack("SignIn")
                .commit()
        }
    }

    private fun menuNavigation(navController: NavController) {
        mainViewModel.menuAction.observe(this) { action ->
            when (action) {
                MenuAction.HOME -> {
                    navController.navigate(R.id.homeFragment)
                }
                MenuAction.USERS -> {

                }
                MenuAction.EVENTS -> {
                    navController.navigate(R.id.eventsFragment)
                }
                MenuAction.ADD -> {

                }
                else -> {}
            }
        }
    }
}