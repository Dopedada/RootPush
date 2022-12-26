package com.cdzyx.pushnotice

import com.google.gson.annotations.SerializedName

/**
 * markdown格式
 */
class WeiXinTalkBean {
    @SerializedName("msgtype")
    private String msgType
    private MarkDownContent markdown
    private AtPeople at

    WeiXinTalkBean(String msgType, MarkDownContent markdown, AtPeople at) {
        this.msgType = msgType
        this.markdown = markdown
        this.at = at
    }

    static class MarkDownContent {
        private String content

        MarkDownContent(String content) {
            this.content = content
        }
    }

    static class AtPeople {
        private List<String> atMobiles
        private boolean isAtAll

        AtPeople(List<String> atMobiles, boolean isAtAll) {
            this.atMobiles = atMobiles
            this.isAtAll = isAtAll
        }
    }

}