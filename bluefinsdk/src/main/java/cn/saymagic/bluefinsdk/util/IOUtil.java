package cn.saymagic.bluefinsdk.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by saymagic on 16/6/25.
 */
public class IOUtil {

    public static String readInputStreamAsString(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(stream));
            String line;
            while((line = br.readLine())!=null){
                builder.append(line);
            }
        }finally {
            close(br);
        }
        return builder.toString();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
