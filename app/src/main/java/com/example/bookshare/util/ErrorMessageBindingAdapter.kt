package com.example.bookshare.util

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.bookshare.repository.AuthRepository

object ErrorMessageBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:visibility")
    fun bindToVisibility(view: View, error: AuthRepository.NetworkState){
        when(error){
            is AuthRepository.NetworkState.Loading->{

                when(view){
                    is TextView -> {
                        view.visibility = View.GONE}

                    is ProgressBar->{
                        view.visibility = View.VISIBLE
                    }
                }
            }

            is AuthRepository.NetworkState.Success->{
                view.visibility = View.GONE
            }

            is AuthRepository.NetworkState.Error->{
                when(view){
                    is TextView->{
                        Log.d("VIS","textViewShown")
                        view.visibility = View.VISIBLE
                    }

                    is ProgressBar->{
                        view.visibility = View.GONE
                    }
                }
            }

        }
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun bindToText(view: TextView, state: AuthRepository.NetworkState){
        when(state){
            is AuthRepository.NetworkState.Error->{
                when(state.errorCode){
                    -1 ->view.text = "Common error" //todo describe errors
                    else->view.text = "Unknown error"
                }
            }
        }
    }

}