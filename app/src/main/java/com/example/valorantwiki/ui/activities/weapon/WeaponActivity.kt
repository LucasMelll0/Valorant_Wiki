package com.example.valorantwiki.ui.activities.weapon

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.ActivityWeaponBinding
import com.example.valorantwiki.databinding.BottomsheetSkinsBinding
import com.example.valorantwiki.model.Weapon
import com.example.valorantwiki.ui.activities.WEAPON_UUID
import com.example.valorantwiki.ui.recyclerview.adapter.DamageRangesAdapter
import com.example.valorantwiki.ui.recyclerview.adapter.WeaponSkinsAdapter
import com.example.valorantwiki.viewmodel.weaponViewModel.WeaponViewModel
import com.example.valorantwiki.webclient.webClientModel.DamageRange
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeaponActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWeaponBinding.inflate(layoutInflater) }
    private val viewModel: WeaponViewModel by viewModel()
    private val damageRangesAdapter: DamageRangesAdapter by inject()
    private val weaponSkinsAdapter: WeaponSkinsAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setsUpBackButton()
        tryToGetWeapon()
    }

    private fun setsUpBackButton() {
        val buttonBack = binding.imagebuttonBackWeaponActivity
        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun tryToGetWeapon() {
        lifecycleScope.launch {
            intent.extras?.getString(WEAPON_UUID)?.let { uuid ->
                val language = getString(R.string.linguagem)
                progressBar(true)
                viewModel.getById(uuid, language)
                viewModel.weapon.observe(this@WeaponActivity) { weapon ->
                    weapon?.let {
                        fillFields(weapon)
                        setsUpShowSkinsButton(weapon)
                        progressBar(false)
                    }
                }
            } ?: finish()
        }
    }

    private fun setsUpShowSkinsButton(weapon: Weapon) {
        val buttonShowSkins = binding.buttonShowSkinsWeaponActivity
        buttonShowSkins.setOnClickListener {
            setsUpBottomSheetSkins(weapon)
        }
    }

    private fun setsUpBottomSheetSkins(weapon: Weapon) {
        val bottomSheetDialog = BottomSheetDialog(
            this@WeaponActivity,
            com.google.android.material.R.style.Theme_Design_BottomSheetDialog
        )
        BottomsheetSkinsBinding.inflate(layoutInflater).apply {
            recyclerviewWeaponSkins
                .layoutManager = GridLayoutManager(this@WeaponActivity, 2).apply {
                orientation = GridLayoutManager.HORIZONTAL
            }
            recyclerviewWeaponSkins.adapter = weaponSkinsAdapter
            weaponSkinsAdapter.submitList(weapon.skins)
            bottomSheetDialog.setContentView(root)
            bottomSheetDialog.show()
        }

    }

    private fun fillFields(weapon: Weapon) {
        val imageView = binding.imageviewPortraitWeaponActivity
        val textViewName = binding.textviewNameWeaponActivity
        val textviewCategory = binding.textviewCategoryWeaponActivity
        val textviewCost = binding.textviewCostWeaponActivity
        val textViewFireRate = binding.textviewFireRateWeaponActivity
        val textViewRunSpeed = binding.textviewRunSpeedWeaponActivity
        val textViewReloadSpeed = binding.textviewReloadSpeedWeaponActivity
        val textViewMagazine = binding.textviewMagazineWeaponActivity
        val textViewEquipSpeed = binding.textviewEquipSpeedWeaponActivity
        weapon.apply {
            imageView.load(image)
            textViewName.text = name
            shopData?.let {
                textviewCategory.text = it.categoryText
                textviewCost.text = it.cost.toString()
            } ?: run {
                val cardViewShopInformation = binding.cardviewShopInformations
                cardViewShopInformation.visibility = View.GONE
            }
            stats?.let {
                textViewFireRate.text =
                    getString(R.string.activity_weapon_fire_rate_format, it.fireRate)
                textViewRunSpeed.text =
                    getString(R.string.activity_weapon_run_speed_format, it.runSpeedMultiplier)
                textViewEquipSpeed.text =
                    getString(R.string.activity_weapon_equip_speed_format, it.equipTimeSeconds)
                textViewReloadSpeed.text =
                    getString(R.string.activity_weapon_reload_speed_format, it.reloadTimeSeconds)
                textViewMagazine.text =
                    getString(R.string.activity_weapon_magazine_format, it.magazineSize)
                setsUpDamageRangesRecyclerView(it.damageRanges)
            } ?: run {
                val cardViewStatsInformation = binding.cardviewWeaponInformations
                val cardViewDamageInformation = binding.cardviewDamageInformations
                cardViewStatsInformation.visibility = View.GONE
                cardViewDamageInformation.visibility = View.GONE

            }
        }
    }

    private fun setsUpDamageRangesRecyclerView(damageRanges: List<DamageRange>) {
        val recyclerviewDamageRanges = binding.recyclerviewDamageRangesWeaponActivity
        recyclerviewDamageRanges.adapter = damageRangesAdapter
        recyclerviewDamageRanges.layoutManager = LinearLayoutManager(this@WeaponActivity)
        damageRangesAdapter.submitList(damageRanges)
    }

    private fun progressBar(visible: Boolean) {
        val progressBar = binding.progressbarWeaponActivity
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
}