package com.sunblackhole.android.alWidget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gyf.barlibrary.ImmersionBar
import com.sunblackhole.android.R
import com.sunblackhole.android.aliData.response.UpdateResponse
import com.sunblackhole.android.alutils.Utils

class UpdateDialog : DialogFragment(){

    private var immersionBar: ImmersionBar? = null
    private var updateV: TextView? = null
    private var cancelBtn: Button? = null
    private var updateBtn: Button? = null
    private var mustUpdateBtn: Button? = null
    private var fileName: String? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable((ColorDrawable(Color.TRANSPARENT)))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(0x00000000))
        dialog?.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        immersionBar = ImmersionBar.with(this, dialog!!).statusBarDarkFont(false)
        immersionBar?.init()
        var rootView = inflater.inflate(R.layout.ali_update_dialog, null)

        updateV = rootView.findViewById(R.id.tv_update_v)
        cancelBtn = rootView.findViewById(R.id.btn_cancel)
        updateBtn = rootView.findViewById(R.id.btn_update)
        mustUpdateBtn = rootView.findViewById(R.id.btn_must_update)

        cancelBtn?.setOnClickListener {
            dismissAllowingStateLoss()
        }
        updateBtn?.setOnClickListener {
            if (fileName?.length ?: 0 > 1 ) {
                Utils.downLoadApp(this.requireContext(),fileName)
                dismissAllowingStateLoss()
            }
        }
        mustUpdateBtn?.setOnClickListener {
            if (fileName?.length ?: 0 > 1 ) {
                Utils.downLoadApp(this.requireContext(),fileName)
                dismissAllowingStateLoss()
            }
        }

        return rootView
    }

    fun updateData( updateObj: UpdateResponse.UpdateObj) {

        updateV?.text = "Discover a new version v" + updateObj.v
        fileName = updateObj.fileName?.replace("localhost","192.168.1.3")

        if (updateObj.mustUpdate == 1) {

        } else if (updateObj.mustUpdate == 2) {
            mustUpdateBtn?.visibility = View.VISIBLE
            updateBtn?.visibility = View.GONE
            cancelBtn?.visibility = View.GONE
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        immersionBar?.destroy()
    }

}