package cn.saymagic.bluefinsdk.job;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.saymagic.bluefinsdk.BluefinHandler;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.exception.BluefinException;
import cn.saymagic.bluefinsdk.exception.BluefinStateException;
import cn.saymagic.bluefinsdk.exception.BluefinUnknowException;
import cn.saymagic.bluefinsdk.exception.BluefinNotFoundException;
import cn.saymagic.bluefinsdk.util.IOUtil;
import cn.saymagic.bluefinsdk.util.URLUtil;

/**
 * Created by saymagic on 16/6/23.
 */
public class GetNewestVersionJob extends Job<BluefinApkData>{

    private static String UPDATE_PATH = "/api/v1/%s/info/";

    private String mPackageName;

    public GetNewestVersionJob(String packageName) {
        this.mPackageName = packageName;
    }

    @Override
    public BluefinApkData perform() throws BluefinException {
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(URLUtil.join(mServerUrl, String.format(UPDATE_PATH, mPackageName)));
            HttpURLConnection connection = URLUtil.openConnection(url);
            connection.connect();
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:{
                    inputStream = url.openStream();
                    JSONObject object = new JSONObject(IOUtil.readInputStreamAsString(inputStream));
                    return BluefinApkData.from(object);
                }case HttpURLConnection.HTTP_NOT_FOUND:{
                    throw new BluefinNotFoundException();
                }default:{
                    throw new BluefinStateException("bad request , the response code is " + connection.getResponseCode());
                }
            }
        }catch (Exception e){
            throw new BluefinUnknowException(e);
        }finally {
            IOUtil.close(inputStream);
        }
    }

    @Override
    public String getName() {
        return "GetNewestVersionJob";
    }

    @Override
    public void onDone(BluefinApkData data) {
        sendMessageData(BluefinHandler.CHECK_UPDATE_DONE, data);
    }

    @Override
    public void onFail(Exception e) {
        sendMessageData(BluefinHandler.CHECK_UPDATE_FAILED, e);
    }

    @Override
    public void onCancel(BluefinApkData data) {
        sendMessageData( BluefinHandler.CHECK_UPDATE_CANCLE, data);
    }

}
