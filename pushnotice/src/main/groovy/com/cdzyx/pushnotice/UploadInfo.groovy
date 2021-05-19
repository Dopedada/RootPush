package com.cdzyx.pushnotice


class UploadInfo {
    boolean needUpload
    String changeLog
    String apiEnvironmentText
    String appTestVersionCodeText
    String canProxyText
    String needAtPeopleMobiles

    @Override
    public String toString() {
        return "UploadInfo{" +
                "needUpload=" + needUpload +
                ", changeLog='" + changeLog + '\'' +
                ", apiEnvironmentText='" + apiEnvironmentText + '\'' +
                ", appTestVersionCodeText='" + appTestVersionCodeText + '\'' +
                ", canProxyText='" + canProxyText + '\'' +
                ", needAtPeopleMobiles='" + needAtPeopleMobiles + '\'' +
                '}';
    }
}