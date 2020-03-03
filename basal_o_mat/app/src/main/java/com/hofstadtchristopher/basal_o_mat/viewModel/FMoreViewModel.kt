package com.hofstadtchristopher.basal_o_mat.viewModel

import androidx.lifecycle.ViewModel
import com.hofstadtchristopher.basal_o_mat.recyclerView.ListItem

class FMoreViewModel() : ViewModel() {
    var recViewitems: ArrayList<ListItem> = ArrayList()

    init {
        recViewitems.add(
            ListItem("Placeholder Title")
        )
        recViewitems.add(
            ListItem("Placeholder Title")
        )
        recViewitems.add(
            ListItem("Placeholder Title")
        )
    }
}