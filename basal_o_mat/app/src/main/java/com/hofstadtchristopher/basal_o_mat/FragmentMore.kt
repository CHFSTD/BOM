package com.hofstadtchristopher.basal_o_mat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.recyclerView.ListItemAdapter
import com.hofstadtchristopher.basal_o_mat.viewModel.FMoreViewModel

/**
 * A simple [Fragment] subclass.
 */
class FragmentMore : Fragment() {

    private lateinit var vMdl: FMoreViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(this).get(FMoreViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_fMore)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ListItemAdapter()
        adapter.setItems(vMdl.recViewitems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

}
