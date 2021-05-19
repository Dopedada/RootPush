package com.cdzyx.pushnotice


class UploadInfo {
    boolean needUpload
    String changeLog
    String apiEnvironmentText
    String appTestVersionCodeText
    String canProxyText
    String needAtPeopleMobiles
    String apiToken
    String packageName
    String robotToken
    String appName
    String appIconPath
    String firAppName


    @Override
    String toString() {
        return "UploadInfo{" +
                "needUpload=" + needUpload +
                ", changeLog='" + changeLog + '\'' +
                ", apiEnvironmentText='" + apiEnvironmentText + '\'' +
                ", appTestVersionCodeText='" + appTestVersionCodeText + '\'' +
                ", canProxyText='" + canProxyText + '\'' +
                ", needAtPeopleMobiles='" + needAtPeopleMobiles + '\'' +
                ", apiToken='" + apiToken + '\'' +
                ", packageName='" + packageName + '\'' +
                ", robotToken='" + robotToken + '\'' +
                ", appName='" + appName + '\'' +
                ", appIconPath='" + appIconPath + '\'' +
                ", firAppName='" + firAppName + '\'' +
                '}'
    }
}