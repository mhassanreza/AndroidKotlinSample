package org.aaaid.eservices.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import org.aaaid.eservices.R

object LoadingDialogFragment : DialogFragment() {
    private var isLoading = false

    fun showLoading(fragmentManager: FragmentManager) {
        if (isLoading || isAdded) return
        isLoading = true
        show(fragmentManager, "LoadingDialogFragment")
    }

    fun hideLoading() {
        if (!isLoading) return
        isLoading = false
        dismissAllowingStateLoss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.MyCustomDialog).apply {
            setContentView(R.layout.fragment_loading_dialog)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.apply {
                setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setGravity(Gravity.CENTER)
            }
        }
    }

}