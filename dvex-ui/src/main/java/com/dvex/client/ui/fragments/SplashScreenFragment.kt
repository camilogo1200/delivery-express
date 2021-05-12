package com.dvex.client.ui.fragments


import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dvex.client.R
import com.dvex.client.application.ui.fragments.BaseFragment
import com.dvex.client.databinding.FragmentSplashScreenBinding
import com.dvex.client.utils.makeVisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : BaseFragment() {

    private lateinit var _binding: FragmentSplashScreenBinding
    private val alphaFadeInAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.splash_screen_animation_01
        )
    }

    private val TIME_BOX_ANIMATION = 1000L
    private val SPLASH_SCREEN_ANIMATION_DURATION = 3500L
    private val SPLASH_SCREEN_CHILDREN_ANIMATION_DURATION = 2500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        startAppIntroAnimation()
        return _binding.root
    }

    private fun startAppIntroAnimation() {

        lifecycleScope.launch(Dispatchers.Main) {
            val transition: TransitionDrawable = _binding.root.background as TransitionDrawable
            transition.startTransition(SPLASH_SCREEN_ANIMATION_DURATION.toInt())
            delay(TIME_BOX_ANIMATION)
            with(_binding) {
                main.children.asIterable().onEach { view ->
                    view.apply {
                        animation = alphaFadeInAnimation
                        animation.duration = SPLASH_SCREEN_CHILDREN_ANIMATION_DURATION
                    }
                }.onEach { children -> children.makeVisible() }
                delay(SPLASH_SCREEN_ANIMATION_DURATION)
            }
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }
    }
}
