/*
 * Copyright © 2017-2019 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package com.sunblackhole.android.preference

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import com.sunblackhole.android.Application
import com.sunblackhole.android.R
import com.sunblackhole.android.util.ToolsInstaller

/**
 * Preference implementing a button that asynchronously runs `ToolsInstaller` and displays the
 * result as the preference summary.
 */
class ToolsInstallerPreference(context: Context, attrs: AttributeSet?) : Preference(context, attrs) {
    private var state = State.INITIAL

    override fun getSummary() = context.getString(state.messageResourceId)

    override fun getTitle() = context.getString(R.string.tools_installer_title)

    override fun onAttached() {
        super.onAttached()
        Application.getAsyncWorker().supplyAsync(Application.getToolsInstaller()::areInstalled).whenComplete(this::onCheckResult)
    }

    private fun onCheckResult(state: Int, throwable: Throwable?) {
        when {
            throwable != null || state == ToolsInstaller.ERROR -> setState(State.INITIAL)
            state and ToolsInstaller.YES == ToolsInstaller.YES -> setState(State.ALREADY)
            state and (ToolsInstaller.MAGISK or ToolsInstaller.NO) == ToolsInstaller.MAGISK or ToolsInstaller.NO -> setState(State.INITIAL_MAGISK)
            state and (ToolsInstaller.SYSTEM or ToolsInstaller.NO) == ToolsInstaller.SYSTEM or ToolsInstaller.NO -> setState(State.INITIAL_SYSTEM)
            else -> setState(State.INITIAL)
        }
    }

    override fun onClick() {
        setState(State.WORKING)
        Application.getAsyncWorker().supplyAsync { Application.getToolsInstaller().install() }.whenComplete { result: Int, throwable: Throwable? -> onInstallResult(result, throwable) }
    }

    private fun onInstallResult(result: Int, throwable: Throwable?) {
        when {
            throwable != null -> setState(State.FAILURE)
            result and (ToolsInstaller.YES or ToolsInstaller.MAGISK) == ToolsInstaller.YES or ToolsInstaller.MAGISK -> setState(State.SUCCESS_MAGISK)
            result and (ToolsInstaller.YES or ToolsInstaller.SYSTEM) == ToolsInstaller.YES or ToolsInstaller.SYSTEM -> setState(State.SUCCESS_SYSTEM)
            else -> setState(State.FAILURE)
        }
    }

    private fun setState(state: State) {
        if (this.state == state) return
        this.state = state
        if (isEnabled != state.shouldEnableView) isEnabled = state.shouldEnableView
        notifyChanged()
    }

    private enum class State(val messageResourceId: Int, val shouldEnableView: Boolean) {
        INITIAL(R.string.tools_installer_initial, true),
        ALREADY(R.string.tools_installer_already, false),
        FAILURE(R.string.tools_installer_failure, true),
        WORKING(R.string.tools_installer_working, false),
        INITIAL_SYSTEM(R.string.tools_installer_initial_system, true),
        SUCCESS_SYSTEM(R.string.tools_installer_success_system, false),
        INITIAL_MAGISK(R.string.tools_installer_initial_magisk, true),
        SUCCESS_MAGISK(R.string.tools_installer_success_magisk, false);
    }
}
