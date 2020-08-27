package com.example.climadiario.ui.customs

import android.view.View
import androidx.fragment.app.Fragment
import com.example.climadiario.ui.interfaces.IOnBackPressFragment

abstract class BaseFragment : Fragment() , IOnBackPressFragment {
    //EXTENSION FUNCTIONS
    fun View.showView(show: Boolean){
        if(show) this.visibility = View.VISIBLE
        else this.visibility = View.GONE
    }
}