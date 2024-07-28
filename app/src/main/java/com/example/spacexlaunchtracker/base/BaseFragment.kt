package com.example.spacexlaunchtracker.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {
    protected fun showToast(context: Context, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (!message.isNullOrBlank()) {
            Toast.makeText(context, message, duration).show()
        }
    }
}