package com.example.bookshare.util

object UiAvatarsUtil {
    fun getAvatarUrlByDisplayName(displayName: String): String{
        return "https://eu.ui-avatars.com/api/?name=${displayName.replace(' ','+')}"
    }
}