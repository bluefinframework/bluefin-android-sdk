package cn.saymagic.bluefinsdk.job;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.saymagic.bluefinsdk.BluefinHandler;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.exception.NotFoundException;
import cn.saymagic.bluefinsdk.util.IOUtil;
import cn.saymagic.bluefinsdk.util.URLUtil;

/**
 * Created by saymagic on 16/6/23.
 */
public class CheckUpdateJob extends Job<BluefinApkData>{

    private static String UPDATE_PATH = "/api/v1/info/%s/";

    @Override
    public BluefinApkData perform() throws Exception {
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(URLUtil.join(mServerUrl, String.format(UPDATE_PATH, mPackageName)));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:{
                    inputStream = url.openStream();
                    JSONObject object = new JSONObject(IOUtil.readInputStreamAsString(inputStream));
                    return BluefinApkData.from(object);
                }case HttpURLConnection.HTTP_NOT_FOUND:{
                    throw new NotFoundException();
                }default:{
                    throw new Exception("bad request , the response code is " + connection.getResponseCode());
                }
            }
        }finally {
            IOUtil.close(inputStream);
        }
    }

    @Override
    public String getName() {
        return "CheckUpdateJob";
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
