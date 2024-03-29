package com.toothless.head2head.fragments.name

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.toothless.head2head.MainActivity
import com.toothless.head2head.R
import com.toothless.head2head.ai.AIManager
import com.toothless.head2head.GameManager
import com.toothless.head2head.events.EventBus
import com.toothless.head2head.events.data.StartGameEvent
import kotlinx.android.synthetic.main.name_input_fragment.*

class NameInputFragment(private val numberOfPlayers : Int, private val mainActivity: MainActivity) : Fragment() {

    private lateinit var name1: String
    private lateinit var name2: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.name_input_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(GameManager.isAiGame)
            {
                player2NameInput.visibility = View.GONE
                aiNameSelector.visibility = View.VISIBLE

                val spinValues = ArrayAdapter(mainActivity, R.layout.ai_spinner_item, AIManager.getAINames())
                aiNameSelector.adapter = spinValues
            }

        confirm.setOnClickListener {
            submitName()
        }
    }

    private fun submitName() {
        if (!player1NameInput.text.isNullOrBlank())
            name1 = player1NameInput.text.toString()
        else {
            Toast.makeText(mainActivity, "Please input a name for player 1", Toast.LENGTH_LONG)
                    .show()
            return
        }

        if (!GameManager.isAiGame) {
            if (numberOfPlayers == 2 && !player2NameInput.text.isNullOrBlank())
                name2 = player2NameInput.text.toString()
            else {
                Toast.makeText(mainActivity, "Please input a name for player 2", Toast.LENGTH_LONG)
                        .show()
                return
            }
        } else
            name2 = GameManager.setSelectedAi(aiNameSelector.selectedItem.toString())

        if (!this::name2.isInitialized) {
            Toast.makeText(mainActivity, "Please select and AI", Toast.LENGTH_LONG).show()
            return
        }

        EventBus.startGameEvent(StartGameEvent(this, name1, name2))
    }
}
