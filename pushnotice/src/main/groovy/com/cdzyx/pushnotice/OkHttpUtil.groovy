package com.cdzyx.pushnotice


import com.google.gson.Gson
import okhttp3.*

import java.util.concurrent.TimeUnit

class OkHttpUtil {
    private OkHttpClient okHttpClient
    private Gson gson
    private static final String API_TOKEN = "293457325da4b6363ecec48593d45fc4"
    private static final String PACKAGE_NAME = "com.cdzyx.firupload"//应用包名
    private static final String DING_DING_ROBOT_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=82249f9cb272edcb47e8ac81a18a6e40db323175cfd24e61a7581c6e81bb8334"

    OkHttpUtil() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build()
        gson = new Gson()
    }
    /**
     * 获取fir的上传凭证
     * @return 上传凭证
     */
    AppInFirInfo getCert() {
        FormBody.Builder build = new FormBody.Builder()
        build.add("bundle_id", PACKAGE_NAME)
        build.add("api_token", API_TOKEN)
        build.add("type", "android")
        Request request = new Request.Builder().url("http://api.bq04.com/apps").post(build.build()).build()
        Response response = okHttpClient.newCall(request).execute()
        String result = response.body.string()
        return gson.fromJson(result, AppInFirInfo.class)
    }

    //上传apk文件
    String uploadApk(File apkFile, String uploadKey, String uploadToken, String appVersionName, Integer appVersionCode, String uploadUrl, String changeLog) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), apkFile)
        MultipartBody body = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("key", uploadKey)
                .addFormDataPart("token", uploadToken)
                .addFormDataPart("x:name", "firupload")
                .addFormDataPart("x:version", appVersionName)
                .addFormDataPart("x:build", String.valueOf(appVersionCode))
                .addFormDataPart("file", apkFile.name, fileBody)
                .addFormDataPart("x:changelog", changeLog)
                .build()
        Request requestApk = new Request.Builder().url(uploadUrl).post(body).build()
        Response responseApk = okHttpClient.newCall(requestApk).execute()
        return responseApk.body.string()
    }


    UploadResultInfo uploadIcon(File apkIconFile, String uploadKey, String uploadToken, String uploadUrl) {
        RequestBody fileBodyIcon = RequestBody.create(MediaType.parse("application/octet-stream"), apkIconFile)
        MultipartBody bodyIcon = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("key", uploadKey)
                .addFormDataPart("token", uploadToken)
                .addFormDataPart("file", "icon.png", fileBodyIcon)
                .build()
        Request requestIcon = new Request.Builder().url(uploadUrl).post(bodyIcon).build()
        Response responseIcon = okHttpClient.newCall(requestIcon).execute()
        return gson.fromJson(responseIcon.body.string(), UploadResultInfo.class)
    }

    AppDownloadInfo getAppDownloadInfo() {
        // 获取成功连接
        String queryurl =
                "http://api.bq04.com/apps/latest/$PACKAGE_NAME?api_token=$API_TOKEN&type=android"
        Request requestUrl = new Request.Builder().url(queryurl).get().build()
        Response responseUrl = okHttpClient.newCall(requestUrl).execute()
        String result = responseUrl.body.string()
        return gson.fromJson(result, AppDownloadInfo.class)
    }

    String sendDingTalk(DingTalkBean bean) {
        println("发送给钉钉消息:"+gson.toJson(bean))
        RequestBody markdownBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(bean))
        Request mdDingTalk = new Request.Builder().url(DING_DING_ROBOT_TOKEN)
                .post(markdownBody).build()
        Response responseLink = okHttpClient.newCall(mdDingTalk).execute()
        return responseLink.body.string()
    }

}