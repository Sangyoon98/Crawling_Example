package com.cookandroid.crawling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView tv_textView;
    String URLs = "https://lostark.game.onstove.com/Profile/Character/0iloll0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_textView = findViewById(R.id.tv_textView);

        //new WeatherAsyncTask(tv_textView).execute();
        BackgroundTask(URLs);

    }

    Disposable backgroundtask;
    void BackgroundTask(String URLs) {
        //onPreExecute

        backgroundtask = Observable.fromCallable(() -> {
            //doInBackground

            String result = "";

            try {
                Document document = Jsoup.connect(URLs).get();
                Elements elements = document.select("span[class=profile-character-info__name]");

                for (Element element : elements) {
                    result = result + element.text() + "\n";
                }

                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    //onPostExecute

                    tv_textView.setText(result);

                    backgroundtask.dispose();
                });
    }
}


/*
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
 */