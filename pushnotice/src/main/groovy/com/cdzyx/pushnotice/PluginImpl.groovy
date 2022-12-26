package com.cdzyx.pushnotice

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class PluginImpl implements Plugin<Project> {
    private static final String ANSI_RESET = "\u001B[0m"
    private static final String ANSI_RED = "\u001B[31m"
    private static final String ANSI_GREEN = "\u001B[32m"


    @Override
    void apply(Project project) {
        project.extensions.add("uploadInfo", UploadInfo)
        if (project.android.hasProperty("applicationVariants")) {
            project.afterEvaluate {
                project.android.applicationVariants.all { variant ->
                    Task uploadFir = project.task("assemble${variant.name.capitalize()}Fir").doLast {
                        doPublish(project)
                    }
                    uploadFir.dependsOn("assemble${variant.name.capitalize()}")
                }
            }
        }
    }
    //打包完毕,走发布流程
    private static void doPublish(Project project) {
        UploadInfo info = project.uploadInfo
        if (!info.needUpload) {
            return
        }
        OkHttpUtil okHttpUtil = new OkHttpUtil()
        //获取上传凭证
        AppInFirInfo apkInFirInfo = okHttpUtil.getCert(info.packageName, info.apiToken)
        AppInFirInfo.Cert.Upload fileUploadCert = apkInFirInfo.cert.binary
        //上传APK文件
        println(ANSI_RED + "开始上传APK到fir..." + ANSI_RESET)
        File[] uploadFile = new File("${project.buildDir}/outputs/upload").listFiles()
        File apkFile
        for (File file : uploadFile) {
            if (file.name.endsWith("apk")) {
                apkFile = file
                break
            }
        }
        if (apkFile == null) {
            println(ANSI_GREEN + "apk文件不存在" + ANSI_RESET)
        }
        //获取配置信息
        String uploadFileResult = okHttpUtil.uploadApk(
                apkFile,
                fileUploadCert.getUploadKey(),
                fileUploadCert.getUploadToken(),
                project.android.defaultConfig.versionName,
                project.android.defaultConfig.versionCode,
                fileUploadCert.getUploadUrlAddress(),
                info.changeLog,
                info.firAppName
        )
        println(ANSI_GREEN + "apk上传fir结果:$uploadFileResult" + ANSI_RESET)
        //上传logo
        println(ANSI_RED + "开始上传LOGO到fir..." + ANSI_RESET)
        AppInFirInfo.Cert.Upload iconUploadCert = apkInFirInfo.cert.icon
        UploadResultInfo uploadIconResult = okHttpUtil.uploadIcon(
                new File(info.appIconPath),
                iconUploadCert.getUploadKey(),
                iconUploadCert.getUploadToken(),
                iconUploadCert.getUploadUrlAddress())
        println(ANSI_GREEN + "上传Icon返回结果:$uploadIconResult" + ANSI_RESET)
        //获取apk的下载信息
        String downloadUrl = okHttpUtil.getAppDownloadInfo(info.packageName, info.apiToken).downloadUrl
        List<String> needAtPeopleMobiles = new ArrayList<>()
        needAtPeopleMobiles.addAll(info.needAtPeopleMobiles.split(","))
        StringBuilder atPeopleContent = new StringBuilder()
        for (String phone : needAtPeopleMobiles) {
            atPeopleContent.append("@" + phone)
        }
        String content = "### " + info.appName + "最新版已打包发布\n" +
                "\n" +
                "* ${info.changeLog}\n" +
                "* v${project.android.defaultConfig.versionName}\n" +
                "* ${info.appTestVersionCodeText}\n" +
                "\n" +
                "[直接下载]($downloadUrl)\n" +
                "\n" +
                "[查看下载二维码](https://api.pwmqr.com/qrcode/create/?url=$downloadUrl)\n" +
                "\n" +
                "[在Fir中查看]( http://hey.scandown.com/" + info.firAppName + ")\n" +
                getAtPeopleContent(atPeopleContent.toString()) +
                "\n"
        switch (info.platform) {
            case "weixin":
                String sendWexinResult = okHttpUtil.sendWeiXinMessageToTalk(new WeiXinTalkBean(
                        "markdown",
                        new WeiXinTalkBean.MarkDownContent(content),
                        new WeiXinTalkBean.AtPeople(needAtPeopleMobiles, false)
                ), info.robotToken)
                println(ANSI_GREEN + "发送到微信的结果:$sendWexinResult" + ANSI_RESET)
                break
            case "dingding":
                String sendDingDingResult = okHttpUtil.sendDingMessageToTalk(new DingTalkBean(
                        "markdown",
                        new DingTalkBean.MarkDownContent(info.appName + "新版本提示", "![screenshot](${uploadIconResult.getUrl.substring(0, uploadIconResult.getUrl.indexOf("?"))})\n" + content),
                        new DingTalkBean.AtPeople(needAtPeopleMobiles, false)
                ), info.robotToken)
                println(ANSI_GREEN + "发送到钉钉的结果:$sendDingDingResult" + ANSI_RESET)
                break
            default:
                String sendDingDingResult = okHttpUtil.sendDingMessageToTalk(new DingTalkBean(
                        "markdown",
                        new DingTalkBean.MarkDownContent(info.appName + "新版本提示", "![screenshot](${uploadIconResult.getUrl.substring(0, uploadIconResult.getUrl.indexOf("?"))})\n" + content),
                        new DingTalkBean.AtPeople(needAtPeopleMobiles, false)
                ), info.robotToken)
                println(ANSI_GREEN + "发送到钉钉的结果:$sendDingDingResult" + ANSI_RESET)
                break
        }
    }

    private static String getAtPeopleContent(String atPeopleContent) {
        if (atPeopleContent.isEmpty() || atPeopleContent == "@") {
            return ""
        } else {
            return "\n" + atPeopleContent
        }
    }
}