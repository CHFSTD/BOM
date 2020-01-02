package com.hofstadtchristopher.basal_o_mat.dir_Test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hofstadtchristopher.basal_o_mat.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentTestStart.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentTestStart.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTestStart : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_start, container, false)
    }


}
