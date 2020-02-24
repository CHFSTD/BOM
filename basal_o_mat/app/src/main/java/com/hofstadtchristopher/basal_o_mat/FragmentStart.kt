package com.hofstadtchristopher.basal_o_mat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.recyclerView.NormItemAdapter
import com.hofstadtchristopher.basal_o_mat.viewModel.FStartViewModel
//import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_start.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentStart.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentStart.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentStart : Fragment() {

    private lateinit var vMdl: FStartViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(this).get(FStartViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_fStart)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fStart_btn_test.setOnClickListener {
            Navigation.findNavController(it).navigate(FragmentStartDirections.actionToNavigationTest())
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = NormItemAdapter(context!!)
        adapter.setItems(vMdl.recViewitems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

}
