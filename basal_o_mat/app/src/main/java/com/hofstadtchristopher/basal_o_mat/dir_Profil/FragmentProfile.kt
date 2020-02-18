package com.hofstadtchristopher.basal_o_mat.dir_Profil


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.recyclerView.BasalProfileItemAdapter
import com.hofstadtchristopher.basal_o_mat.viewModel.FProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentProfile : Fragment() {

    private lateinit var fProfileViewModel: FProfileViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fProfileViewModel = ViewModelProvider(activity!!).get(FProfileViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_fProfile)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fProfil_fab.setOnClickListener {
            Navigation.findNavController(it).navigate(FragmentProfileDirections.actionToFragmentAddBasalRate())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = BasalProfileItemAdapter()

        adapter.setOnItemClickListener(object: BasalProfileItemAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Navigation.findNavController(view!!)
                    .navigate(FragmentProfileDirections.actionToFragmentUpdateBasalRate(position))

            }
        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        fProfileViewModel.allBasalRates.observe(viewLifecycleOwner, Observer { bRates ->
            bRates?.let {
                adapter.setItems(it)
            }
        })
    }

}
