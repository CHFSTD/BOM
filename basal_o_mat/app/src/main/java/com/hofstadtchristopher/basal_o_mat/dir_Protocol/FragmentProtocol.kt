package com.hofstadtchristopher.basal_o_mat.dir_Protocol


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.recyclerView.TestResultItemAdapter
import com.hofstadtchristopher.basal_o_mat.viewModel.FProtocolViewModel

/**
 * A simple [Fragment] subclass.
 */
class FragmentProtocol : Fragment() {

    private lateinit var vMdl: FProtocolViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FProtocolViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_protocol, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_fProtocol)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TestResultItemAdapter()

        adapter.setOnItemClickListener(object: TestResultItemAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Navigation.findNavController(view!!)
                    .navigate(FragmentProtocolDirections.actionToFragmentProtocolTestresult(position))
            }

        } )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        vMdl.allTestResults.observe(viewLifecycleOwner, Observer { tResults ->
            tResults?.let {
                adapter.setItems(it)
            }
        })
    }

}
