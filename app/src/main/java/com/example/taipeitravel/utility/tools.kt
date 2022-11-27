package com.example.taipeitravel.utility

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showDialog(title: String, message: String, context: Context) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("確認") { dialogInterface, _  ->
            dialogInterface.dismiss()
        }
        .setCancelable(false)
        .show()
}

