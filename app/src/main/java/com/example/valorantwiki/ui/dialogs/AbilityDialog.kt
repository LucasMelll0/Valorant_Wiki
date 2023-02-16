package com.example.valorantwiki.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.valorantwiki.R
import com.example.valorantwiki.webclient.webClientModel.Ability

class AbilityDialog(private val ability: Ability) : DialogFragment() {

    companion object {
        const val TAG = "Ability Dialog"
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.ability_dialog, null)
            setsUpView(view)
            builder.setView(view)
            builder.create()

        } ?: throw IllegalStateException("Activity não pode ser nula")

    }

    private fun setsUpView(view: View) {
        val abilityName = view.findViewById<TextView>(R.id.textview_ability_name_dialog)
        val abilituDescription =
            view.findViewById<TextView>(R.id.textview_ability_description_dialog)
        abilityName.text = ability.displayName
        abilituDescription.text = ability.description
    }

}