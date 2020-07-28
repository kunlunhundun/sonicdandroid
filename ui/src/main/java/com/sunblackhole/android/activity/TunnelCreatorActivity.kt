/*
 * Copyright © 2017-2019 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package com.sunblackhole.android.activity

import android.os.Bundle
import com.sunblackhole.android.fragment.TunnelEditorFragment
import com.sunblackhole.android.model.ObservableTunnel

/**
 * Standalone activity for creating tunnels.
 */
class TunnelCreatorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.findFragmentById(android.R.id.content) == null) {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, TunnelEditorFragment())
                    .commit()
        }
    }

    override fun onSelectedTunnelChanged(oldTunnel: ObservableTunnel?, newTunnel: ObservableTunnel?) {
        finish()
    }
}