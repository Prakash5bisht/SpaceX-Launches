package com.example.spacexlaunchtracker.extentions

import androidx.fragment.app.Fragment

fun Fragment.isActive() : Boolean {
    return null != this.activity && this.isAdded && !this.isDetached && !isRemoving
}