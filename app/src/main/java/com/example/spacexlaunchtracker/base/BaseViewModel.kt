package com.example.spacexlaunchtracker.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T>: ViewModel() {
    abstract fun setStateEvent(intent: T)
}