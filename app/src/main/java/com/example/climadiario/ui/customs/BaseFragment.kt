package com.example.climadiario.ui.customs

import android.view.View
import androidx.fragment.app.Fragment
import com.example.climadiario.ui.interfaces.IOnBackPressFragment

/**
 * Fragment base de la aplicación que implementa una interface de retroceso entre fragments
 * @author Axel Sanchez
 */
abstract class BaseFragment : Fragment() , IOnBackPressFragment {
    /**
     * Extension Function que permite ocultar y mostrar cualquier vista
     * @param [show] recibe un boolean que indica si quiere ocultar o mostrar la vista
     */
    fun View.showView(show: Boolean){
        if(show) this.visibility = View.VISIBLE
        else this.visibility = View.GONE
    }
}