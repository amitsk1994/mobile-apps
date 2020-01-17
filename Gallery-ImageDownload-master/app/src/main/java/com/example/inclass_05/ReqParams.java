package com.example.inclass_05;



import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class ReqParams {
    private HashMap<String,String> map;
    StringBuilder buf=new StringBuilder();

    public ReqParams() {
        map=new HashMap<>();
    }
    public ReqParams addParameter(String key, String value) {
        try {
            map.put(key, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    private String getEncodedParams(){
        for(String key:map.keySet()){
            if(buf.length()>0){
                buf.append("&");
            }
            buf.append(key+"="+map.get(key));
        }
        return buf.toString();
    }

    public String  getEncodedURL(String url){
        return url+"?"+getEncodedParams();
    }

    public void encodePostparams(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
        out.write(getEncodedParams());
        out.flush();
    }
}
