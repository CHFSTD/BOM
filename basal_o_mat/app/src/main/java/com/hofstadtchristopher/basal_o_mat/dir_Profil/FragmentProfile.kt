package com.hofstadtchristopher.basal_o_mat.dir_Profil


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.viewModel.FProfileViewModel

/**
 * A simple [Fragment] subclass.
 */
class FragmentProfile : Fragment() {

    private lateinit var fProfileViewModel: FProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fProfileViewModel = ViewModelProvider(this).get(FProfileViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }


}
