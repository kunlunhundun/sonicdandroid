/*
 * Copyright © 2020 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.wireguard.android.alWidget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wireguard.android.Application
import com.wireguard.android.R
import com.wireguard.android.alModel.VpnPointModel

class VpnPointItemAdapter (mList: MutableList<VpnPointModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: MutableList<VpnPointModel> = mList
    var onItemClickLister: OnItemClickLister? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.ali_vpn_point_item, parent, false)
        return VpnPointHolder(view)
    }

    override fun getItemCount(): Int {
        return mList?.size
    }

    override fun getItemViewType(position: Int): Int {

        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var model = mList[position]

        if (holder is VpnPointHolder) {
            holder.mItemView.setOnClickListener {
                onItemClickLister?.onItemClick(position)
            }
            holder.pointName.text = model.pointName
            val icon = Application.get().resources.getIdentifier(model.icon, "mipmap", Application.get().packageName)
            holder.iconFlag.setImageResource(icon)
        }
    }

    inner class VpnPointHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconFlag: ImageView = itemView.findViewById(R.id.iv_icon_flag)
        var pointName : TextView = itemView.findViewById(R.id.tv_point_name)
        var mItemView:View = itemView
    }
    interface OnItemClickLister {
        fun onItemClick(position: Int)
    }

}