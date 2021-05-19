package com.cdzyx.pushnotice

import com.google.gson.annotations.SerializedName

/**
 * markdown格式
 */
class DingTalkBean {
    @SerializedName("msgtype")
    private String msgType
    private MarkDownContent markdown
    private AtPeople at

    DingTalkBean(String msgType, MarkDownContent markdown, AtPeople at) {
        this.msgType = msgType
        this.markdown = markdown
        this.at = at
    }

    static class MarkDownContent {
        private String title
        private String text

        MarkDownContent(String title, String text) {
            this.title = title
            this.text = text
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