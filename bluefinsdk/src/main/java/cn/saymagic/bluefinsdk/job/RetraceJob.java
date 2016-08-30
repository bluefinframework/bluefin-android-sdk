package cn.saymagic.bluefinsdk.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.saymagic.bluefinsdk.BluefinHandler;
import cn.saymagic.bluefinsdk.exception.BluefinException;
import cn.saymagic.bluefinsdk.exception.BluefinNotFoundException;
import cn.saymagic.bluefinsdk.exception.BluefinUnknowException;
import cn.saymagic.bluefinsdk.util.IOUtil;
import cn.saymagic.bluefinsdk.util.URLUtil;

/**
 * Created by saymagic on 16/7/5.
 */
public class RetraceJob extends Job<String> {

    private static String MAPPING_PATH = "/api/v1/%s/%s/mapping/";

    private File mEncryptFile;

    private String mEncryptString;

    private String mPackageName;

    private String mIdentity;

    public RetraceJob(String mEncryptString, String packageName, String identity) {
        this.mEncryptString = mEncryptString;
        this.mPackageName = packageName;
        this.mIdentity = identity;
    }

    public RetraceJob(File mEncryptFile, String packageName, String identity) {
        this.mEncryptFile = mEncryptFile;
        this.mPackageName = packageName;
        this.mIdentity = identity;
    }

    @Override
    public String perform() throws BluefinException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(URLUtil.join(mServerUrl, String.format(MAPPING_PATH, mPackageName, mIdentity)));
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            outputStream = connection.getOutputStream();
            outputStream.write(getOutputBytes());
            outputStream.flush();
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:{
                    inputStream = url.openStream();
                    return IOUtil.readInputStreamAsString(inputStream);
                }case HttpURLConnection.HTTP_NOT_FOUND:{
                    throw new BluefinNotFoundException();
                }default:{
                    throw new Exception("bad request , the response code is " + connection.getResponseCode());
                }
            }
        }catch (Exception e){
            throw new BluefinUnknowException(e);
        }finally {
            connection.disconnect();
            IOUtil.close(inputStream);
            IOUtil.close(outputStream);
        }
    }

    @Override
    public String getName() {
        return "RetraceJob";
    }

    @Override
    public void onDone(String s) {
        sendMessageData(BluefinHandler.RETRACE_DONE, s);
    }

    @Override
    public void onFail(Exception e) {
        sendMessageData(BluefinHandler.RETRACE_FAILED, e);
    }

    @Override
    public void onCancel(String s) {
        sendMessageData(BluefinHandler.RETRACE_CANCLE, s);
    }

    private byte[] getOutputBytes() throws IOException {
        if (mEncryptFile != null) {
            return IOUtil.readInputStreamAsString(new FileInputStream(mEncryptFile)).getBytes();
        } else {
            return mEncryptString == null ? new byte[0] : mEncryptString.getBytes();
        }
    }

}
