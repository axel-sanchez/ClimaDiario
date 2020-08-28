package com.example.climadiario.ui.interfaces

import androidx.fragment.app.Fragment

/**
 * Interface destinada a la navegación entre fragments
 * @author Axel Sanchez
 */
interface INavigationHost {

    /**
     * Navega a un nuevo fragment sin destruir el anterior
     * @param [fragment] fragmento al que queremos ir
     * @param [addToBackstack] un boolean que va a determinar si guardamos o no en memoria el fragment actual
     */
    fun navigateTo(fragment: Fragment, addToBackstack: Boolean)

    /**
     * Remplaza el fragment actual por uno nuevo
     * @param [fragment] fragmento al que queremos ir
     * @param [addToBackstack] un boolean que va a determinar si guardamos o no en memoria el fragment actual
     */
    fun replaceTo(fragment: Fragment, addToBackstack: Boolean)

    /**
     * Cierra el fragment y regresa al último guardado en memoria
     */
    fun finish()
}

