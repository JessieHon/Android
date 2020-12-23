package cn.hbu.cs.maingrid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonDemo extends AppCompatActivity implements View.OnClickListener{
        TextView responseCity,responseYes,responseForcast,responseToday,responseTemp;
        EditText address;
        StringBuilder response;
        String parse_yesterday;
        String prase_city;
        String prase_today;
        String parse_forcast;
        String parse_temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_demo);
        //responseText =  findViewById(R.id.response_text);
        address= findViewById(R.id.weather_address);
        responseCity=findViewById(R.id.response_city);
        responseYes=findViewById(R.id.response_yes);
        responseForcast=findViewById(R.id.response_forcast);
        responseToday=findViewById(R.id.response_today);
        responseTemp=findViewById(R.id.response_temp);
        Button sendRequest =  findViewById(R.id.send_request);
        //Button parseRequest = findViewById(R.id.parse_request);
        sendRequest.setOnClickListener(this);
        //parseRequest.setOnClickListener(this);
        sendRequest();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequest();
        }
    }
    private void sendRequest() {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                response=new StringBuilder();

                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    String s="http://wthrcdn.etouch.cn/weather_mini?city=";
                    if(address.getText().toString().trim()!=null&&address.getText().toString().trim()!=""){
                        s+=address.getText().toString().trim();
                    }else {
                        s+="保定";
                    }
                    //获取到HttpConnection的实例，new出一个URL对象，并传入目标的网址，然后调用一下openConnection（）方法
                    /*URL url = new URL("http://www.weather.com.cn/data/sk/101090201.html");*/
                    URL url = new URL(s);
                    // manifest文件增加：android:usesCleartextTraffic="true"
                    // 解决高版本只能https，不能http问题（Cleartext HTTP traffic to XXX not permitted）
                    // 2019.9.4 By David
                    Log.e("wyg2",url.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); //得到了HttpConnection的实例后，设置请求所用的方法（GET：从服务器获取数据，POST：提交数据给服务器）
                    connection.setConnectTimeout(8000);//设置连接超时，读取的毫秒数
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();//利用getInputStream（）方法获取服务器的返回的输入流，然后读取
                    reader = new BufferedReader(new InputStreamReader(in));// 对获取到的输入流进行读取

                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    //showResponse(response.toString());//在模拟器显示返回值
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        parseRequest();
                    }
                    if (connection != null) {
                        //调用disconnect（）方法将HTTP连接关闭掉
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseRequest() {
        try {
            parse_yesterday="";
            prase_city="";
            parse_forcast="";
            prase_today="";
            parse_temp="";
            JSONObject object = new JSONObject(String.valueOf(response));
            String status=object.getString("status");
            if(status.equals("1000")){
                JSONObject object1 = object.getJSONObject("data");
                JSONObject yesterday=object1.getJSONObject("yesterday");//昨天
                String yes_date=yesterday.getString("date");
                parse_yesterday+=yes_date+" ";
                String yes_high=yesterday.getString("high");
                parse_yesterday+=yes_high+" ";
                String yes_fx=yesterday.getString("fx");//风向
                parse_yesterday+=yes_fx+" ";
                String yes_low=yesterday.getString("low");//低温
                parse_yesterday+=yes_low+" ";
                /*String yes_fl=yesterday.getString("fl");//风级
                parse_yesterday+=yes_fl+" ";*/
                String yes_type=yesterday.getString("type");//状况
                parse_yesterday+=yes_type+" ";

                String address=object1.getString("city");//城市
                prase_city+=address;

                JSONArray forecast=object1.getJSONArray("forecast");//今天和未来几天的天气
                int length = forecast.length();
                for(int i=0;i<length;i++){
                    JSONObject o = (JSONObject)forecast.get(i);
                    String date=o.getString("date");
                    String high=o.getString("high");
                    String low=o.getString("low");
                    String fengxiang=o.getString("fengxiang");
                    String type=o.getString("type");
                    if(i==0){
                        prase_today+=date+" "+high+" "+low+" "+fengxiang+" "+type+"\n";
                    }else {
                        parse_forcast+=date+" "+high+" "+low+" "+fengxiang+" "+type+"\n";
                    }

                }
                parse_temp=object1.getString("wendu")+"°C";//当前温度

                String ganmao=object1.getString("ganmao");
                prase_today+="\n"+ganmao+"\n";
            }else if (status.equals("1002")){
                prase_city="请输入正确的城市名称！";
                parse_forcast="";
                parse_yesterday="";
                parse_temp="";
            }
            showResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResponse() { // 在这里进行UI操作，将结果显示到界面上
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //responseText.setText(response);
                responseCity.setText(prase_city);
                responseYes.setText(parse_yesterday);
                responseForcast.setText(parse_forcast);
                responseToday.setText(prase_today);
                responseTemp.setText(parse_temp);
            }
        });
    }


}
