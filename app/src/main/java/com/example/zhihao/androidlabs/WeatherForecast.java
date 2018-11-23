package com.example.zhihao.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {
    private static final String ACTIVITY_NAME = "WeatherForecast";

    private static final String URL_address = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    private ImageView currentWeather;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView windSpeed;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        currentWeather = findViewById(R.id.weatherImage);
        currentTemp = findViewById(R.id.currentTemp);
        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        windSpeed = findViewById(R.id.windSpeed);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        ForecastQuery FQ = new ForecastQuery();
        FQ.execute(URL_address);
        Log.i(ACTIVITY_NAME,"onCreate");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>{
        private String min_temp;
        private String max_temp;
        private String current_temp;
        private String wind_speed;
        private Bitmap icon;

        @Override
        protected String doInBackground(String... args) {

            try {
                //Copy from link 1
                URL url = new URL(URL_address);
                HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream in = conn.getInputStream();
                //Instantiate the Parser
                XmlPullParser Parser = Xml.newPullParser();
                Parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                Parser.setInput(in,"UTF-8");

                //check each time not equal
                while (Parser.next() != XmlPullParser.END_DOCUMENT){
                    if(Parser.getEventType()==Parser.START_TAG) {
                        Log.i(ACTIVITY_NAME, "iterate through the XML tags ");
                        System.out.println(Parser.getName());
                        if (Parser.getName().equals("temperature")) {
                            current_temp = Parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            min_temp = Parser.getAttributeValue(null, "min");
                            max_temp = Parser.getAttributeValue(null, "max");
                            publishProgress(50);
                        }
                        if (Parser.getName().equals("speed")) {
                            wind_speed = Parser.getAttributeValue(null, "value");
                            publishProgress(75);
                        }

                        if (Parser.getName().equals("weather")) {
                            String image_name = Parser.getAttributeValue(null, "icon");
                            String image_file = image_name + ".png";
                            if (fileExistence(image_file)) {
                                FileInputStream fis = new FileInputStream(getBaseContext().getFileStreamPath(image_file));
                                icon = BitmapFactory.decodeStream(fis);
                                Log.i(ACTIVITY_NAME, "Image exists");
                            } else {
//                                Bitmap image  = HTTPUtils.getImage(ImageURL));
//                                FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
//                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
//                                outputStream.flush();
//                                outputStream.close();

                                URL iconURL = new URL("http://openweathermap.org/img/w/" + image_name + ".png");
                                icon = getImage(iconURL);
                                FileOutputStream fos = openFileOutput(image_name + ".png", Context.MODE_PRIVATE);
                                icon.compress(Bitmap.CompressFormat.PNG, 80, fos);
                                fos.flush();
                                fos.close();
                                Log.i(ACTIVITY_NAME, "added new image");

                            }

                        }

                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            publishProgress(100);
            Log.i(ACTIVITY_NAME, "completed");

            return "Done";
        }

        //http://www.java2s.com/Code/Android/2D-Graphics/GetBitmapfromUrlwithHttpURLConnection.html
        private boolean fileExistence(String iconFile){
            File file = getBaseContext().getFileStreamPath(iconFile);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

        }

    @Override
        protected void onProgressUpdate(Integer... values) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");

        }

        @Override
        protected void onPostExecute(String str) {
            String degree = " "+Character.toString((char) 0x00B0)+" ";
            currentTemp.setText(currentTemp.getText()+current_temp+degree+"C");
            minTemp.setText(minTemp.getText()+min_temp+degree+"C");
            maxTemp.setText(maxTemp.getText()+max_temp+degree+"C");
            windSpeed.setText(windSpeed.getText() +wind_speed+" m/s");
            currentWeather.setImageBitmap(icon);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

}

