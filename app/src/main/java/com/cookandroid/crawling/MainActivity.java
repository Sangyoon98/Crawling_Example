package com.cookandroid.crawling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView tv_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_textView = findViewById(R.id.tv_textView);

        new WeatherAsyncTask(tv_textView).execute();
    }
}

class WeatherAsyncTask extends AsyncTask<String, Void, String> {
    TextView textView;

    public WeatherAsyncTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "";

        try {
            Document document = Jsoup.connect("https://lostark.game.onstove.com/Profile/Character/%EB%A9%B4%ED%83%B1%EC%9D%B4").get();
            Elements elements = document.select("span[class=profile-character-info__name]");

            for (Element element : elements) {
                result = result + element.text() + "\n";
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        textView.setText(s);
    }
}