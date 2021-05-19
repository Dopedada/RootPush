package com.cdzyx.pushnotice

import com.google.gson.annotations.SerializedName


class AppDownloadInfo {
    @SerializedName("installUrl")
    private String downloadUrl

    String getDownloadUrl() {
        return downloadUrl
    }

    @Override
    String toString() {
        return "下载地址: $downloadUrl"
    }
}
