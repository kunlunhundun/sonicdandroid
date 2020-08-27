package com.supersunstars.android.DjiActivity

import android.content.Intent
import android.os.Bundle
import com.hjq.toast.ToastUtils
import com.ljoy.chatbot.sdk.ELvaChatServiceSdk
import com.supersunstars.android.R
import com.supersunstars.android.DjiData.AppConfigData
import com.supersunstars.android.DjiData.net.ApiClient
import com.supersunstars.android.DjiData.net.ApiErrorModel
import com.supersunstars.android.DjiData.net.ApiResponse
import com.supersunstars.android.DjiData.net.NetworkScheduler
import com.supersunstars.android.DjiData.response.BaseResponseObject
import com.supersunstars.android.Djiutils.LogUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import kotlinx.android.synthetic.main.dji_custom_toolbar.view.*
import kotlinx.android.synthetic.main.dji_feed_back_activity.*


class DjiFeedBackActivity : DjiBaseActivity() {

    private var type = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
        initListener()
    }

    override fun getLayoutId(): Int {
        return R.layout.dji_feed_back_activity
    }

    private fun initData() {

        setHasRightLogo(true)

        try {
            // Set Init Callback
            setInitCallback()
            // Init AIHelp SDK
            ELvaChatServiceSdk.init(this,
                    "NOBB_app_f58cca0224a446b986c21b29cb7fb9ab",
                    "nobb@aihelp.net",
                    "nobb_platform_81637864-c68f-44a8-8431-d253bba5e448")
        } catch (e: java.lang.Exception) {
            LogUtils.e( "invalid init params : ")
        }


    }

    private fun initListener() {

        tool_bar.iv_right_logo.setOnClickListener {


            val map: HashMap<String, Any> = HashMap()
            val tags: ArrayList<String> = ArrayList()
            // the tag names are variables
            // the tag names are variables
            tags.add("pay1")
            tags.add("s1")
            tags.add("vip2")

            map["elva-tags"] = tags

            val config: HashMap<String, Any> = HashMap()

            config["elva-custom-metadata"] = map
            config["showContactButtonFlag"] = "1" // The display can be accessed from the upper right corner of the FAQ list (if you do not want to display it, you need to delete this parameter)

            config["showConversationFlag"] = "1" // Click on the upper right corner of the FAQ to enter the upper right corner of the robot interface. (If you do not want to display, you need to delete this parameter.)

            config["directConversation"] = "1" // Click on the upper right corner of the FAQ and you will be taken to the manual customer service page (without adding the default to the robot interface. If you don't need it, delete this parameter)

            ELvaChatServiceSdk.setUserName(AppConfigData.loginName) // set User Name

            ELvaChatServiceSdk.setUserId(AppConfigData.loginName) // set User Id

            ELvaChatServiceSdk.setServerId("server_id") // set Serve Id
            ELvaChatServiceSdk.showFAQs(config)


          //  var intent = Intent(this, ChatMainActivity::class.java)
            //var intent = Intent(this, AliMessageActivity::class.java)

           // startActivity(intent)
        }

        cl_func_item.setOnClickListener {
            type = 0
            ck_func_item.isChecked = true
            ck_optimization_item.isChecked = false
            ck_other_item.isChecked = false
        }
        cl_optimization_item.setOnClickListener {
            type = 1
            ck_func_item.isChecked = false
            ck_optimization_item.isChecked = true
            ck_other_item.isChecked = false
        }
        cl_other_item.setOnClickListener {
            type = 2
            ck_func_item.isChecked = false
            ck_optimization_item.isChecked = false
            ck_other_item.isChecked = true
        }

        btn_feed_back.setOnClickListener {
            var content = et_feedback_content.text.toString().trim()
            if (content.length < 5) {
                ToastUtils.show("Please input at least  5  characters")
            } else {
                //sendFeedBack
                ApiClient.instance.service.sendFeedBack(type,content)
                        .compose(NetworkScheduler.compose())
                        .bindUntilEvent(this, ActivityEvent.DESTROY)
                        .subscribe(object : ApiResponse<BaseResponseObject>(this,true){
                            override fun businessFail(data: BaseResponseObject) {
                                ToastUtils.show(data.message ?: "")
                            }
                            override fun businessSuccess(data: BaseResponseObject) {
                                var intent = Intent(this@DjiFeedBackActivity, DjiMessageActivity::class.java)
                                startActivity(intent)
                                et_feedback_content.setText("")
                            }
                            override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                                if (this != null) {
                                    ToastUtils.show(apiErrorModel.message)
                                }                    }
                        })
            }
        }

    }


    // Before Init, set initializaiton callback method
    fun setInitCallback() {

//        ELvaChatServiceSdk.setOnInitializedCallback {
//            ELvaChatServiceSdk.OnInitializationCallback {
//
//            }
//        }

        ELvaChatServiceSdk.setOnInitializedCallback(object : ELvaChatServiceSdk.OnInitializationCallback {

            override fun onInitialized() {
                println("AIHelp elva Initialization Done!")
            }
        })
    }

}