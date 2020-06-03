package com.example.bookshare.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.bookshare.viewmodel.ProfileViewModel
import com.example.bookshare.R
import com.example.bookshare.databinding.ProfileFragmentBinding


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var  mBinding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<ProfileFragmentBinding>(inflater,R.layout.profile_fragment,container,false)
            .also {
                mBinding = it
            }
            .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.lifecycleOwner = activity
        mBinding.viewModel = viewModel

        viewModel.navigationCommandSender.observe(activity as LifecycleOwner) {
            when(it){
                ProfileViewModel.NavigationCommand.ToProfileEditFragment->findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment())
                ProfileViewModel.NavigationCommand.ToLoginScreen->{
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAuthActivity())
                    (activity as MainActivity).finish()
                }
            }

        }

    }

}
