package cn.heyanle.rss;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IO {

    public static String read (File file) {

        String s = "";

        BufferedInputStream in = null;

        if (!file.exists()) {
            return s;
        }

        try {

            byte[] b = new byte[(int) file.length()];
            int len = 0;

            in = new BufferedInputStream(new FileInputStream(file));
            len = in.read(b);

            s = new String(b, 0, len);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return s;
    }

    public static void write(File file,String value){

        file.getParentFile().mkdirs();

        BufferedOutputStream out = null;

        try{
            if (! file.exists()){
                file.createNewFile();
            }

            if(!file.canWrite()){
                return;
            }

            out = new BufferedOutputStream(new FileOutputStream(file));

            if (value == null){
                return;
            }

            out.write(value.getBytes());

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if (out != null){
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }


}
