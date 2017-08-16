package exercise.pclap.demookhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    /* OK HTTP de nhan va gui du lieu tu web*/
//    OkHttp là một thư viện của bên thứ ba được phát triển bởi Square để gửi và nhận
// các yêu cầu mạng dựa trên HTTP. Nó được xây dựng trên đầu trang của thư viện Okio ,
// giúp cho việc đọc và ghi dữ liệu hiệu quả hơn các thư viện Java I / O tiêu chuẩn bằng
// cách tạo ra một bộ nhớ chia sẻ . Đây cũng là thư viện bên dưới cho thư viện Retrofit cung cấp sự an toàn
// cho việc tiêu thụ các API dựa trên REST.
    private TextView tvShow;
    private OkHttpClient client;
    public String url= "https://reqres.in/api/users/2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds();
        initComponents();
        setEvents();

    }
    private void setEvents() {

    }
    private void initComponents() {
        client = new OkHttpClient();

        OkHttpHandler handler = new OkHttpHandler();
        handler.execute(url);
    }

    private class OkHttpHandler extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();

            builder.url(params[0]);
            Request request = builder.build();
            //khi server tra loi
            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "Downn Thanh Cong!!!!");
                return response.body().string();

            } catch (IOException e) {
                Log.d(TAG, "Download khong thanh cong!!!");
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject object = jsonObject.getJSONObject("data");
                int id = object.getInt("id");
                String firstName = object.getString("first_name");
                String lastName = object.getString("last_name");
                String avatar = object.getString("avatar");

                Toast.makeText(getBaseContext(), "Object: " + id + "___" + firstName + "___" + lastName + "____" + avatar, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvShow.setText(s);
        }
    }

    private void findViewByIds() {
        tvShow = (TextView) findViewById(R.id.tv_show);
    }
}
