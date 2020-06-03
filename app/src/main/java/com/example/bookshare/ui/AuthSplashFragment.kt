package com.example.bookshare.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.observe
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.bookshare.R
import com.example.bookshare.repository.AuthRepository


class AuthSplashFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            //lock back press on splash screen
        }

        AuthRepositoryImpl.networkState.observe(this){
            when(it){
                is AuthRepository.NetworkState.Success->{
                    when(it.operation){
                        AuthRepository.Operation.LOGIN->{
                            findNavController().navigate(
                                AuthSplashFragmentDirections.actionAuthSplashFragmentToMainActivity())
                            (activity as AuthActivity).finish()
                        }
                        AuthRepository.Operation.SIGNUP->findNavController().popBackStack(R.id.loginFragment,false)
                    }

                }
                is AuthRepository.NetworkState.Error->{

                    when(it.operation){
                        AuthRepository.Operation.LOGIN ->findNavController().popBackStack(R.id.loginFragment,false)
                        AuthRepository.Operation.SIGNUP->findNavController().popBackStack(R.id.signUpFragment,false)
                    }
                }

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_splash, container, false)
    }

}
