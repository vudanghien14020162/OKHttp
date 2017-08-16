package exercise.pclap.demookhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PC Lap on 8/16/2017.
 */
public class OKHTTPMain extends AppCompatActivity {

    private String url = "http://ybox.vn/search?page=1&category=recruitment";

    private static final String TAG = OKHTTPMain.class.getSimpleName();
    private TextView tvShow;
    private String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShow = (TextView) findViewById(R.id.tv_show);

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //xu ly khong dong bo
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //xu ly yeu cau neu cotruongf hop bi huy va khong co ket noi
                if(response.isSuccessful()){
                    data = response.body().string();
                    Log.d(TAG, "String: " + data);

                    Log.i(TAG, "onResponse: " + data);

                }else {
                    throw new IOException("Unexpected code " + response);

                }
            }
        });
    }
}
