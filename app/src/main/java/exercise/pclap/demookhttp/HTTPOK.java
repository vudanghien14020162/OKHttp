package exercise.pclap.demookhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PC Lap on 8/16/2017.
 */
public class HTTPOK extends AppCompatActivity {

    private static final String TAG = HTTPOK.class.getSimpleName();

    private OkHttpClient client;
    private TextView tvShow;
    private String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewByIds();
        initComponents();
        setEvents();

    }

    private void findViewByIds() {
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    private void initComponents() {
        client = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse("https://httpbin.org/get").newBuilder();

        builder.addQueryParameter("website", "www.journaldev.com");
        builder.addQueryParameter("tutorials", "android");
        String url = builder.build().toString();

        Log.d(TAG, "URL: " + url);

        //yeu cau den trang web
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "Error: " + e.getMessage().toString());
                e.printStackTrace();
                call.cancel();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.d(TAG, "Ket Noi On Dinh!!!");
                    //copy json
                    data = response.body().string();
                    Log.d(TAG, "String: " + data);

                }else {
                    Log.d(TAG, "Ket noi bi ngat quang!!!");
                }
            }
        });

        tvShow.setText(data + "");
    }


    private void setEvents() {

    }
}
