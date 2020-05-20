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
import com.example.bookshare.viewmodel.ProfileEditViewModel
import com.example.bookshare.R
import com.example.bookshare.databinding.ProfileEditFragmentBinding


class ProfileEditFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileEditFragment()
    }

    private val viewModel: ProfileEditViewModel by viewModels()

    private lateinit var mBinding: ProfileEditFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<ProfileEditFragmentBinding>(inflater,R.layout.profile_edit_fragment,container,false)
            .also { mBinding = it }
            .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.lifecycleOwner = activity
        mBinding.viewModel = viewModel

        viewModel.navigationCommandSender.observe(activity as LifecycleOwner){
            findNavController().navigate(ProfileEditFragmentDirections.actionProfileEditFragmentToProfileFragment())
        }
    }

}
