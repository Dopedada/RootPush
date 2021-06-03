[![](https://jitpack.io/v/ZYX520YTT/RootPush.svg)](https://jitpack.io/#ZYX520YTT/RootPush)

### RootPush
 RootPush是一个包含apk打包上传到fir平台，然后通过钉钉机器人自动发布应用的插件
 
 
 **添加依赖**
 
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
        classpath "com.github.ZYX520YTT:RootPush:1.0.2"
}
```
#### 使用说明
1.在应用目录下的build.gradle下，配置如下代码
```
plugins {
    ...
    id 'com.cdzyx.pushnotice'
}
android {
    ...
    applicationVariants.all { variant ->
        variant.outputs.all {
            //可以不用限制 name != "debug"
            if (variant.buildType.name != "debug") {
                variant.packageApplication.outputDirectory = new File("${project.buildDir}/outputs/upload")
                outputFileName = "**appName(替换为应用名称)**-${variant.buildType.name}-v${variant.versionName}.apk"
            }
        }
    }
    ...
}
uploadInfo {
    needUpload = true
    needAtPeopleMobiles = "123,456,789"
    changeLog = "测试打包机器人"
    appTestVersionCodeText = "测试版本:0.0.1"
    apiToken="***"
    packageName="***"
    robotToken="***"
    appIconPath="***"
    appName="***"
    firAppName="***"
}

```
2.uploadInfo内参数说明

 - needUpload
```
是否需要上传到fir平台并通过机器人发送到群聊
```
 
 - needAtPeopleMobiles 
 ```
 需要@的人的手机号严格以逗号分隔
 若不需要@人,则needAtPeopleMobiles=""
 若需要@一个人needAtPeopleMobiles = "123"
 若需要@多个人needAtPeopleMobiles = "123,456"
 ```
 - changeLog
 ```
 打包版本说明，例如：修改了v1.0.0bug,预发布包等
 ```
 - appTestVersionCodeText
 ```
 版本号说明
 ```
 - apiToken
 ```
 fir平台上个人的API  Token，点击头像下拉框可查看
 ```
 - packageName
 ```
 在fir上创建的应用的包名
 ```
  ![packageName.png](https://github.com/ZYX520YTT/RootPush/blob/main/picture/packageName.png)
 - firAppName
 ```
 在fir上创建的应用名称
 ```
   ![firAppName.png](https://github.com/ZYX520YTT/RootPush/blob/main/picture/firAppName.png)
- appName
```
app的应用名称
```
- appIconPath
```
app的logo,在AndroidStudio内的地址，例如：appIconPath=project.projectDir.path + "/src/main/res/mipmap-xxhdpi/ic_launcher.png"
```
- robotToken
```
在钉钉群聊里面找到机器人，查看机器人设置，找到WebHook地址，地址中的access_token即为robotToken
```


### 注意:请在机器人设置的安全设置里添加关键词，这里是appName
