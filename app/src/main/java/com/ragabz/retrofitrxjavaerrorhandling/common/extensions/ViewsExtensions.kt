package com.ragabz.retrofitrxjavaerrorhandling.common.extensions

import android.view.View

inline fun View.click(crossinline action: () -> Unit) {
    this.setOnClickListener {
        action.invoke()
    }
}
