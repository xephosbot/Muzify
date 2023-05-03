package com.xbot.muzify.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.xbot.muzify.R
import com.xbot.muzify.databinding.ActivityMainBinding
import com.xbot.muzify.ui.extensions.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

        splashScreen.setOnExitAnimationListener(::onSplashScreenExit)
        navController = findNavController(R.id.nav_host_fragment)

        (binding.navigation as NavigationBarView).setupWithNavController(navController)
    }

    private fun findNavController(@IdRes id: Int): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(id) as NavHostFragment
        return navHostFragment.navController
    }

    private fun onSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        val accelerateInterpolator = FastOutLinearInInterpolator()
        val splashScreenView = splashScreenViewProvider.view
        val iconView = splashScreenViewProvider.iconView

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f),
            ObjectAnimator.ofFloat(iconView, View.ALPHA, 1f, 0f)
        )
        animatorSet.duration = SPLASHSCREEN_ALPHA_ANIMATION_DURATION.toLong()
        animatorSet.interpolator = accelerateInterpolator

        animatorSet.doOnEnd { splashScreenViewProvider.remove() }

        if (WAIT_FOR_AVD_TO_FINISH) {
            waitForAnimatedIconToFinish(splashScreenViewProvider, splashScreenView, animatorSet)
        } else {
            animatorSet.start()
        }
    }

    private fun waitForAnimatedIconToFinish(
        splashScreenViewProvider: SplashScreenViewProvider,
        view: View,
        animatorSet: AnimatorSet
    ) {
        val delayMillis: Long = (splashScreenViewProvider.iconAnimationStartMillis +
                splashScreenViewProvider.iconAnimationDurationMillis) - System.currentTimeMillis()
        view.postDelayed({ animatorSet.start() }, delayMillis)
    }

    private companion object {
        const val SPLASHSCREEN_ALPHA_ANIMATION_DURATION = 500
        const val WAIT_FOR_AVD_TO_FINISH = false
    }
}