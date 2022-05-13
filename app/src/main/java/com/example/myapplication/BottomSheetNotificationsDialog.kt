package com.example.myapplication

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.myapplication.BottomSheetNotificationsPM.News
import com.example.myapplication.BottomSheetNotificationsPM.State
import com.example.myapplication.MVP.KPresentationModelDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_notifications.*


class BottomSheetNotificationsDialog :
    KPresentationModelDialog<State, BottomSheetNotificationsPM, State, News>() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pm = BottomSheetNotificationsPM()
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val contentView: View =
            View.inflate(context, R.layout.dialog_notifications, null)
        dialog.apply {
            setContentView(contentView)
            titleNotificationsTV.text = "Уведомления"
        }
                dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parentLayout ->
                val behaviour = BottomSheetBehavior.from(parentLayout)
                setupFullHeight(parentLayout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}