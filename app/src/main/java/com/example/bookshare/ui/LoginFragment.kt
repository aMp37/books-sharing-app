package com.example.bookshare.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.bookshare.viewmodel.LoginViewModel
import com.example.bookshare.R
import com.example.bookshare.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory() }
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<LoginFragmentBinding>(inflater,R.layout.login_fragment,container,false).also {
            binding = it
            binding.lifecycleOwner = this
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.navigationCommandSender.observe(this as LifecycleOwner) {
            when(it){
                is LoginViewModel.NavigationCommand.ToSignUpFragment ->{
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                }

                is LoginViewModel.NavigationCommand.ShowWrongEmailMessage->{Toast.makeText(activity,"Wrong email! ",Toast.LENGTH_SHORT).show()}  //TODO implement dialog

                is LoginViewModel.NavigationCommand.ShowWrongPasswordMessage->{Toast.makeText(activity,"Wrong password! ",Toast.LENGTH_SHORT).show()}//TODO implement dialog
            }
        }

    }

}
