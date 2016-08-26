package cn.saymagic.bluefinsdk.job;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.saymagic.bluefinsdk.BluefinHandler;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.exception.NotFoundException;
import cn.saymagic.bluefinsdk.util.IOUtil;
import cn.saymagic.bluefinsdk.util.URLUtil;

/**
 * Created by saymagic on 16/6/25.
 */
public class ListAllVersionJob extends Job<List<BluefinApkData>>{

    public static final String LIST_ALL_VERSION = "/api/v1/%s/list/";

    @Override
    public List<BluefinApkData> perform() throws Exception {
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(URLUtil.join(mServerUrl, String.format(LIST_ALL_VERSION, mPackageName)));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:{
                    inputStream = url.openStream();
                    JSONArray array = new JSONArray(IOUtil.readInputStreamAsString(inputStream));
                    int length = array.length();
                    List<BluefinApkData> datas = new ArrayList<>(length);
                    for (int i = 0; i < length; i++) {
                        JSONObject object = array.optJSONObject(i);
                        BluefinApkData data = BluefinApkData.from(object);
                        if (!data.isEmpty()) {
                            datas.add(data);
                        }
                    }
                    return datas;
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
        return "ListAllVersionJob";
    }

    @Override
    public void onDone(List<BluefinApkData> data) {
        sendMessageData(BluefinHandler.LIST_ALL_VERSION_DONE, data);
    }

    @Override
    public void onFail(Exception e) {
        sendMessageData(BluefinHandler.LIST_ALL_VERSION_FAILED, e);
    }

    @Override
    public void onCancel(List<BluefinApkData> data) {
        sendMessageData(BluefinHandler.LIST_ALL_VERSION_CANCLE, data);
    }
}
