package com.cdzyx.pushnotice

import com.google.gson.annotations.SerializedName


class UploadResultInfo {
    @SerializedName("download_url")
    String getUrl

    @Override
    public String toString() {
        return "UploadResultInfo{" +
                "getUrl='" + getUrl + '\'' +
                '}'
    }
}