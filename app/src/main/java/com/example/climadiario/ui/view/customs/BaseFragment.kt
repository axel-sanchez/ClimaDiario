package com.example.climadiario.ui.view.customs

import android.view.View
import com.example.climadiario.ui.view.interfaces.IOnBackPressFragment

abstract class BaseFragment : androidx.fragment.app.Fragment() , IOnBackPressFragment {
    //EXTENSION FUNCTIONS
    fun View.showView(show: Boolean){
        if(show) this.visibility = View.VISIBLE
        else this.visibility = View.GONE
    }
}