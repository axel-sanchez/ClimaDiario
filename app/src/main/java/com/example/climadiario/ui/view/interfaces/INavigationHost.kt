package com.example.climadiario.ui.view.interfaces

import androidx.fragment.app.Fragment

interface INavigationHost {

    fun navigateTo(fragment: Fragment, addToBackstack: Boolean)

    fun replaceTo(fragment: Fragment, addToBackstack: Boolean)

    fun finish()
}

