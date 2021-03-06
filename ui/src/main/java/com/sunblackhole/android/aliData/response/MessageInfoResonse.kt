package com.sunblackhole.android.aliData.response

import java.util.*
import kotlin.collections.ArrayList

class MessageInfoResonse : BaseResponseObject(){

    val data: ArrayList<MessageObj>? = null

    data class MessageObj (
            var comment: CommentObj? = null,
            var officialReplyList: MutableList<ReplyObj>? = null
    )

    data class CommentObj (
            var id: String? = null,
            var memberId: String? = null,
            var commentType: Int? = null,
            var content: String? = null,
            var createTime:String? = null
    )

    data class ReplyObj (
            var id: String? = null,
            var commentId: String? = null,
            var memberId: String? = null,
            var content:String? = null,
            var createTime: String? = null
    )

}