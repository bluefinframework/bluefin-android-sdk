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
import cn.saymagic.bluefinsdk.exception.BluefinException;
import cn.saymagic.bluefinsdk.exception.BluefinUnknowException;
import cn.saymagic.bluefinsdk.util.IOUtil;
import cn.saymagic.bluefinsdk.util.URLUtil;

/**
 * Created by saymagic on 16/8/26.
 */
public class ListApksJob extends Job<List<BluefinApkData>> {

    public static final String LIST_ALL_APKS = "/api/v1/apks/";

    @Override
    public List<BluefinApkData> perform() throws BluefinException {
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(URLUtil.join(mServerUrl, LIST_ALL_APKS));
            HttpURLConnection connection = URLUtil.openConnection(url);
            connection.connect();
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK: {
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
                }
                default: {
                    throw new BluefinUnknowException("bad request , the response code is " + connection.getResponseCode());
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
        return "ListApksJob";
    }

    @Override
    public void onDone(List<BluefinApkData> bluefinApkDatas) {
        sendMessageData(BluefinHandler.LIST_ALL_APKS_DONE, bluefinApkDatas);
    }

    @Override
    public void onFail(Exception e) {
        sendMessageData(BluefinHandler.LIST_ALL_APKS_FAILED, e);
    }

    @Override
    public void onCancel(List<BluefinApkData> bluefinApkDatas) {
        sendMessageData(BluefinHandler.LIST_ALL_APKS_CANCLE, bluefinApkDatas);
    }
}
