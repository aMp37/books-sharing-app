package com.example.bookshare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.bookshare.R
import com.example.bookshare.databinding.SignUpFragmentBinding
import com.example.bookshare.viewmodel.SignUpViewModel


class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
    }

    private lateinit var binding: SignUpFragmentBinding
    private val viewModel: SignUpViewModel by viewModels {SignUpViewModel.Factory()  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<SignUpFragmentBinding>(inflater,R.layout.sign_up_fragment,container,false).also {
            binding = it
            binding.lifecycleOwner = this
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        viewModel.navigationCommandSender.observe(this as LifecycleOwner){
            when(it){
                is SignUpViewModel.NavigationCommand.ToSplashFragment->findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToAuthSplashFragment())
                is SignUpViewModel.NavigationCommand.ToLoginFragment-> findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
            }
        }

    }
}
