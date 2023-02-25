package com.example.valorantwiki.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantwiki.databinding.BottomsheetSkinsBinding
import com.example.valorantwiki.ui.recyclerview.adapter.WeaponSkinsAdapter
import com.example.valorantwiki.webclient.webClientModel.WeaponSkin
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class WeaponSkinsBottomSheet(private val skins: List<WeaponSkin>) :
    BottomSheetDialogFragment() {

    private var _binding: BottomsheetSkinsBinding? = null
    private val binding get() = _binding!!
    private val adapter: WeaponSkinsAdapter by inject()

    companion object {
        const val TAG = "WeaponSkinBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetSkinsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setsUpRecyclerViewSkins()
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setsUpRecyclerViewSkins() {
        context?.let {
            val recyclerViewSkins = binding.recyclerviewWeaponSkins
            recyclerViewSkins.adapter = adapter
            recyclerViewSkins.layoutManager = GridLayoutManager(it, 2).apply {
                orientation = GridLayoutManager.HORIZONTAL
            }
            adapter.submitList(skins)
            Log.i(TAG, "setsUpRecyclerViewSkins: ${adapter.currentList}")
        }
    }


}