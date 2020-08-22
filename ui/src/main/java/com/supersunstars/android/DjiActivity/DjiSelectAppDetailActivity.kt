

package com.supersunstars.android.DjiActivity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.supersunstars.android.Application
import com.supersunstars.android.R
import com.supersunstars.android.DjiModel.AppPackageModel
import com.supersunstars.android.DjiWidget.adapter.AppDetailAdapter
import kotlinx.android.synthetic.main.dji_select_app_detail_activity.*

class DjiSelectAppDetailActivity : DjiBaseActivity() {

    var mListData: ArrayList<AppPackageModel> = ArrayList()
    lateinit var appDetailAdapter: AppDetailAdapter

    var isFromInclude: Boolean = false
    lateinit var selectedAppList: ArrayList<AppPackageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.dji_select_app_detail_activity
    }

    private fun initData() {

        setTile("App select")

        isFromInclude =  intent.getBooleanExtra(DjiAppFilterActivity.APP_ITEM_IS_INCLUDE,false)
        selectedAppList = if (isFromInclude) Application.getIncludeAppList() else Application.getExcludeAppList()
        mListData =Application.getAllAppList()
        mListData.forEach {
            it.isCheck = false;
            selectedAppList.forEach { item:AppPackageModel ->
                if( item.name == it.name) {
                    item.isCheck = true
                }
            }
        }
    }

    fun initView() {

        val manager =  LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        rv_app_detail_channel.setLayoutManager(manager);
        val addapter =  AppDetailAdapter(mListData);
        rv_app_detail_channel.setAdapter(addapter);
        appDetailAdapter = addapter
        addapter.onItemClickLister = object : AppDetailAdapter.OnItemClickLister {
            override fun onItemClick(position: Int) {
                refreshAdapter(position)
            }
        }
        addapter.notifyDataSetChanged()
    }

    fun  refreshAdapter(position: Int) {
        val model = mListData.get(position)
        if (model.isCheck) {
            selectedAppList.add(model)
        } else {
            selectedAppList.remove(model)
        }
    }
}