package utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import entities.GlobalVar;

public class HTTPHandler extends AsyncTask<String, Integer, HTTPResponse> implements Constant{

    private HttpURLConnection URLConnection;
    private InputStream stream;
    private InputStreamReader streamReader;
    private BufferedReader reader;

    @Override
    protected HTTPResponse doInBackground(String... options){

        URL url;
        StringBuilder builder = new StringBuilder();
        String currentLine;
        String method = options[0];
        HTTPResponse httpResponse = new HTTPResponse();

        try{
            url = new URL(options[1]);
            URLConnection = (HttpURLConnection)url.openConnection();

            if(GlobalVar.getInstance().getInitialized()){
                URLConnection.setRequestProperty("Authorization", "Bearer " +
                        GlobalVar.getInstance().getAppKey());
            }

            URLConnection.setRequestProperty("Content-type", "application/json");

            switch(method) {
                case GET_METHOD:
                    URLConnection.setRequestMethod("GET");
                    break;
                case POST_METHOD:
                    URLConnection.setRequestMethod("POST");
                    URLConnection.setDoInput(true);
                    OutputStream out = URLConnection.getOutputStream();
                    OutputStreamWriter outW = new OutputStreamWriter(out, "UTF-8");
                    outW.write(options[2]);
                    outW.flush();
                    outW.close();
                    out.close();
                    break;
            }

            URLConnection.connect();

            if(URLConnection.getResponseCode() < 400){

                stream = URLConnection.getInputStream();
                streamReader = new InputStreamReader(stream);
                reader = new BufferedReader(streamReader);

                while((currentLine = reader.readLine()) != null){
                    builder.append(currentLine);
                }

                httpResponse.setResponse(builder.toString());
                httpResponse.setStatus(URLConnection.getResponseCode());
                httpResponse.setResult();
            } else {
                httpResponse.setStatus(URLConnection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                reader.close();
                streamReader.close();
                stream.close();
                URLConnection.disconnect();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return httpResponse;
    }
}
