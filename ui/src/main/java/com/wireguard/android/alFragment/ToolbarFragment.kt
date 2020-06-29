/*
 * Copyright © 2020 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.wireguard.android.alFragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.wireguard.android.R
import com.trello.rxlifecycle2.components.support.RxFragment

open class ToolbarFragment : RxFragment() {
   open  lateinit var toolbar: Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)

    }

    open fun onBackPressed(): Boolean = false
}