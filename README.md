# Bluefin-Android-SDK

> Android SDK for Bluefin Server, provides a convenient interface to access Bluefin Server.

## Install

* compile 'cn.saymagic:bluefinsdk:1.0.9'

## Useage

* Bluefin.init(Context context, BluefinCallback callback, String serverUrl)

	 Initialization Bluefin, need to specify the server url and context, optional parameters BluefinCallback, used to receive callbacks for operations.

* Bluefin.checkUpdate()

	 Check the current application of the latest version. Result will be called back with the init callback.
	 
* Bluefin.checkUpdate(BluefinJobWatcher<BluefinApkData> watcher)

	 Check the current application of the latest version. Result will be called back with the watcher.

* Bluefin.listAllVersion()

	 List all versions. Result will be called back with the init callback.

* Bluefin.listAllVersion(BluefinJobWatcher<List<BluefinApkData>> watcher)

	List all versions. Result will be called backwith the watcher.

* Bluefin.retrace(String s)

	retrace s, Result will be called back with the init callback.

* Bluefin.retrace(String s, BluefinJobWatcher<String> watcher)
	retrace s, Result will be called back callback with the watcher.

* Bluefin.simpleUpdate()

	Check the latest version, and compared with the current version, when need to update , the new apk will be download.
	
PS: All requests are handling in a background thread and all callbacks will be triggered in the UI thread.


## Licence

[gpl-3.0](https://opensource.org/licenses/gpl-3.0.html)