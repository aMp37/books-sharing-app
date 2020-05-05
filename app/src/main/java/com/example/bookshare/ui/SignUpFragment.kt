package com.example.bookshare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.bookshare.R
import com.example.bookshare.databinding.SignUpFragmentBinding
import com.example.bookshare.viewmodel.SignUpViewModel


class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
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
    }
}
