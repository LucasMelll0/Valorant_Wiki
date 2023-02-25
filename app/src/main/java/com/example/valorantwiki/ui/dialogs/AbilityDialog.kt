package com.example.valorantwiki.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.valorantwiki.R
import com.example.valorantwiki.webclient.webClientModel.Ability

class AbilityDialog(private val ability: Ability) : DialogFragment() {

    companion object {
        const val TAG = "Ability Dialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.ability_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        setsUpView()
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun setsUpView() {
        view?.let {
            val textViewAbilityName = it.findViewById<TextView>(R.id.textview_ability_name_dialog)
            val textViewAbilityDescription =
                it.findViewById<TextView>(R.id.textview_ability_description_dialog)
            textViewAbilityName.text = ability.displayName
            textViewAbilityDescription.text = ability.description
        }
    }

}