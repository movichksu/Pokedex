package com.pahomovichk.pokedex.core.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pahomovichk.pokedex.R

abstract class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply {
            window?.decorView?.setBackgroundResource(R.color.transparent)
        }

    fun show(fragmentManager: FragmentManager) {
        fragmentManager.findFragmentByTag(TAG_BOTTOM_SHEET_DIALOG)?.let {
            fragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }

        show(fragmentManager, TAG_BOTTOM_SHEET_DIALOG)
    }

    companion object {
        private const val TAG_BOTTOM_SHEET_DIALOG = "TAG_BOTTOM_SHEET_DIALOG"
    }
}