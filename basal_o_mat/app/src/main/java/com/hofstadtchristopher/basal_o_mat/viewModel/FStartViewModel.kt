package com.hofstadtchristopher.basal_o_mat.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.recyclerView.NormItem

class FStartViewModel(application: Application) : AndroidViewModel(application) {

    var recViewitems: ArrayList<NormItem> = ArrayList()

    init {
        recViewitems.add(
            NormItem(R.drawable.ic_highlight_off_black_24dp, "test", "subtest")
        )
        recViewitems.add(
            NormItem(R.drawable.ic_highlight_off_black_24dp, "test2", "subtest2")
        )
    }
}