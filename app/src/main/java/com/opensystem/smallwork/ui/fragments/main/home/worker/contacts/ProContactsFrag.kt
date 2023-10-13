package com.opensystem.smallwork.ui.fragments.main.home.worker.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opensystem.smallwork.R

/**
 * create an instance of this fragment.
 */
class ProContactsFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pro_contacts, container, false)
    }

}