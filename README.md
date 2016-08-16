# Bluefin-Android-SDK

> 与Bluefin Server 交互的Android SDK，提供了方便的接口来访问Bluefin Server。


## 安装

* 将bluefinsdk整个library以源码的形式引用
* compile 'cn.saymagic:bluefinsdk:1.0.6'

## 使用
Bluefin SDK 对外公开的方法都在`Bluefin`类中以静态的方法提供，具体如下：

* Bluefin.init(Context, BluefinCallback, String )

	 初始化Bluefin，需要指定server url 与context， 可选参数BluefinCallback，用来接收各种操作的回调。

* Bluefin.checkUpdate()

	 检查当前应用最新版本信息。通过init中传入的BluefinCallback进行回调。

* Bluefin.checkUpdate(BluefinJobWatcher<BluefinApkData> watcher)

	 检查当前应用最新版本信息。通过watcher 回调。

* Bluefin.listAllVersion()

	 列出当前应用的所有版本。通过init中传入的BluefinCallback进行回调。

* Bluefin.listAllVersion(BluefinJobWatcher<List<BluefinApkData>> watcher)

	列出当前应用的所有版本。通过watcher 回调。

* Bluefin.retrace(String s)

	将字符串s进行还原。通过init中传入的BluefinCallback进行回调。

* Bluefin.retraceretrace(String s, BluefinJobWatcher<String> watcher)
	将s进行还原。通过watcher 回调。

* Bluefin.simpleUpdate()

	检查最新版本，并与当前版本做比较，当需要更新时，对新的apk进行下载。

注：所有请求都在非UI线程处理，所有回调都在UI线程触发。


## Licence

[gpl-3.0](https://opensource.org/licenses/gpl-3.0.html)