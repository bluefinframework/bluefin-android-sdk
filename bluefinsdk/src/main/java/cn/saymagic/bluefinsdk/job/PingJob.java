package cn.saymagic.bluefinsdk.job;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.saymagic.bluefinsdk.entity.PingResult;
import cn.saymagic.bluefinsdk.exception.BluefinException;
import cn.saymagic.bluefinsdk.exception.BluefinNotConnectException;
import cn.saymagic.bluefinsdk.exception.BluefinUnknowException;
import cn.saymagic.bluefinsdk.util.IOUtil;
import cn.saymagic.bluefinsdk.util.URLUtil;

/**
 * Created by saymagic on 16/8/30.
 */
public class PingJob extends Job<PingResult> {

    public static final String PING = "/api/v1/ping/";

    @Override
    protected PingResult perform() throws BluefinException {
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(URLUtil.join(mServerUrl, PING));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK: {
                    inputStream = url.openStream();
                    JSONObject object = new JSONObject(IOUtil.readInputStreamAsString(inputStream));
                    return PingResult.from(object);
                }
                default: {
                    throw new BluefinUnknowException("bad request , the response code is " + connection.getResponseCode());
                }
            }
        }catch (Exception e){
            throw new BluefinNotConnectException(e);
        }finally {
            IOUtil.close(inputStream);
        }
    }

    @Override
    public String getName() {
        return "PingJob";
    }

    @Override
    public void onDone(PingResult pingResult) {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onCancel(PingResult pingResult) {

    }
}
