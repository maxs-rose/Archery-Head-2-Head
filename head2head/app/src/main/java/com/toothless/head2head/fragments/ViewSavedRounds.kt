package com.toothless.head2head.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.toothless.head2head.MainActivity
import com.toothless.head2head.R
import com.toothless.head2head.events.EventBus
import com.toothless.head2head.save.SaveRound
import com.toothless.head2head.save.SavedRoundJSON
import com.toothless.head2head.viewmodels.savedscore.SavedScoreViewModel
import kotlinx.android.synthetic.main.view_saved_rounds.*

class ViewSavedRounds(val parent: MainActivity) : Fragment() {

    lateinit var rounds : List<SavedRoundJSON>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_saved_rounds, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rounds = SaveRound.getSavedRoundFormatted()

        savedRoundsRecycler.layoutManager = LinearLayoutManager(parent)
        savedRoundsRecycler.adapter = SavedScoreViewModel(parent, rounds.toMutableList())
    }
}