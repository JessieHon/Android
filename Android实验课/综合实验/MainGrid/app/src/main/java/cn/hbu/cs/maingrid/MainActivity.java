package cn.hbu.cs.maingrid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonCalculator,buttonDail,buttonText,buttonMusic,buttonAlarmClock,buttonCalendar,buttonDailBook,
            buttonLifeCycle,buttonIntent,buttonDialog,buttonMenu,buttonToast,buttonNotification,
    buttonListView,buttonServiceLocal,buttonServiceLifeCycle,buttonServiceBindDemo,buttonBroadcast1,buttonBroadcast2,buttonSharedPreferences,
    buttonDatabaseSQLite,buttonContentProvider,buttonWeatherJSON,buttonThreadUpdate,playButton;
    TextView text;
    String username,usernumber;
    int play=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取按钮的id
        buttonCalculator    = findViewById(R.id.buttonCalculator);
        buttonDail=findViewById(R.id.buttonDail);
        buttonText=findViewById(R.id.buttonText);
        buttonMusic=findViewById(R.id.buttonMusic);
        buttonAlarmClock=findViewById(R.id.buttonAlarmClock);
        /*buttonCalendar=findViewById(R.id.buttonCalendar);*/
        buttonDailBook=findViewById(R.id.buttonDailBook);
        text = findViewById(R.id.number);
        /*buttonLifeCycle     = findViewById(R.id.buttonLifeCycle);*/
        buttonIntent        = findViewById(R.id.buttonIntent);
        /*buttonDialog        = findViewById(R.id.buttonDialog);
        buttonMenu          = findViewById(R.id.buttonMenu);
        buttonToast         = findViewById(R.id.buttonToast);
        buttonNotification  = findViewById(R.id.buttonNotification);
        buttonListView      = findViewById(R.id.buttonListView);
        buttonServiceLocal  = findViewById(R.id.buttonServiceLocal);
        buttonServiceLifeCycle = findViewById(R.id.buttonServiceLifeCycle);
        buttonServiceBindDemo = findViewById(R.id.buttonServiceBindDemo);
        buttonBroadcast1    = findViewById(R.id.buttonBroadcast1);
        buttonBroadcast2    = findViewById(R.id.buttonBroadcast2);
        buttonSharedPreferences = findViewById(R.id.buttonSharedPreferences);
        buttonDatabaseSQLite    = findViewById(R.id.buttonDatabaseSQLite);
        buttonContentProvider   = findViewById(R.id.buttonContentProvider);*/
        buttonWeatherJSON       = findViewById(R.id.buttonWeatherJSON);
       /* buttonThreadUpdate      = findViewById(R.id.buttonThreadUpdate);*/

        playButton = super.findViewById( R.id.buttonPlay );
        playButton.setOnClickListener( onClickListener );

        //为按钮添加监听器
        buttonCalculator.setOnClickListener(onClickListener);
        buttonDail.setOnClickListener(onClickListener);
        buttonText.setOnClickListener(onClickListener);
        buttonMusic.setOnClickListener(onClickListener);
        buttonAlarmClock.setOnClickListener(onClickListener);
        /*buttonCalendar.setOnClickListener(onClickListener);*/
        buttonDailBook.setOnClickListener(onClickListener);

        buttonIntent.setOnClickListener(onClickListener);
        /*buttonLifeCycle.setOnClickListener(onClickListener);

        buttonDialog.setOnClickListener(onClickListener);
        buttonMenu.setOnClickListener(onClickListener);
        buttonToast.setOnClickListener(onClickListener);
        buttonNotification.setOnClickListener(onClickListener);
        buttonListView.setOnClickListener(onClickListener);
        buttonServiceLocal.setOnClickListener(onClickListener);
        buttonServiceLifeCycle.setOnClickListener(onClickListener);
        buttonServiceBindDemo.setOnClickListener(onClickListener);
        buttonBroadcast1.setOnClickListener(onClickListener);
        buttonBroadcast2.setOnClickListener(onClickListener);
        buttonSharedPreferences.setOnClickListener(onClickListener);
        buttonDatabaseSQLite.setOnClickListener(onClickListener);
        buttonContentProvider.setOnClickListener(onClickListener);*/
        buttonWeatherJSON.setOnClickListener(onClickListener);
        /*buttonThreadUpdate.setOnClickListener(onClickListener);*/

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {//侦听器
        @Override
        public void onClick(View view) {//点击事件
            Button button = (Button)view;   //把点击获得的id信息传递给button
            try{
                switch(button.getId())//获取点击按钮的ID，通过ID选择对应的选项执行
                {
                    case R.id.buttonCalculator://如果点击了按钮：“1”
                    {
                        Toast.makeText(getApplicationContext(), "多功能计算器", Toast.LENGTH_SHORT).show();
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this,CalculatorActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonDailBook://打开电话本
                    {
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
                        break;
                    }
                    case R.id.buttonPlay:
                    {
                        switch (play) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setAction("cn.hbu.cs.maingrid.ServiceLocalMusic");
                                intent.setPackage("cn.hbu.cs.maingrid");
                                startService(intent);
                                play=1;
                                playButton.setText("《");
                                break;

                            case 1:
                                Intent intent1 = new Intent();
                                intent1.setAction("cn.hbu.cs.maingrid.ServiceLocalMusic");
                                intent1.setPackage("cn.hbu.cs.maingrid");
                                stopService(intent1);
                                playButton.setText("||");
                                play=0;
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                    case R.id.buttonDail://拨打电话
                    {
                        Toast.makeText(getApplicationContext(), "拨打电话", Toast.LENGTH_SHORT).show();
                        String t="tel:";
                        if(usernumber!=null&&usernumber!=""){
                            t="tel:"+usernumber;
                        }
                        Uri uri = Uri.parse(t);
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonText://发送短信
                    {
                        Toast.makeText(getApplicationContext(), "发送短信", Toast.LENGTH_SHORT).show();

                        String t="smsto:";
                        if(usernumber!=null&&usernumber!=""){
                            t="smsto:"+usernumber;
                        }
                        Uri uri = Uri.parse(t);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body", "");
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonMusic://播放音乐
                    {
                        Toast.makeText(getApplicationContext(), "播放音乐", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonAlarmClock://打开闹钟
                    {
                        Toast.makeText(getApplicationContext(), "打开闹钟", Toast.LENGTH_SHORT).show();
                        Intent alarms = new Intent(AlarmClock.ACTION_SET_ALARM);
                        startActivity(alarms);
                        break;

                    }
                    /*case R.id.buttonCalendar://打开日历
                    {
                        Toast.makeText(getApplicationContext(), "日程提醒", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity"));
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonLifeCycle://2
                    {
                        Toast.makeText(getApplicationContext(), "Activity生命周期", Toast.LENGTH_SHORT).show();
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this,LifeCycle1.class);
                        startActivity(intent);
                        break;
                    }*/
                    case R.id.buttonIntent://3
                    {
                        Intent intent = new Intent( MainActivity.this,Intent1.class);
                        /*Uri uri = Uri.parse("https://www.toutiao.com/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);*/
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "新闻", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    /*case R.id.buttonDialog://4
                    {
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this,DialogMain.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "对话框", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.buttonMenu://5
                    {
                        Toast.makeText(getApplicationContext(), "菜单", Toast.LENGTH_SHORT).show();
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this,MenuActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonToast://6
                    {
                        Toast.makeText(getApplicationContext(), "吐司", Toast.LENGTH_SHORT).show();
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this,ToastActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonNotification://7
                    {
                        Toast.makeText(getApplicationContext(), "通知栏", Toast.LENGTH_SHORT).show();
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this, NotificationActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonListView://8
                    {
                        Toast.makeText(getApplicationContext(), "列表", Toast.LENGTH_SHORT).show();
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this, ListMain.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonServiceLocal://9
                    {
                        Toast.makeText(getApplicationContext(), "本地服务(音乐播放器)", Toast.LENGTH_SHORT).show();
                        //注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this, ServiceLocalPlayMusic.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonServiceLifeCycle://0 .setOnClickListener(onClickListener);
                    {
                        Toast.makeText(getApplicationContext(), "远程服务", Toast.LENGTH_SHORT).show();
//注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this, ServiceLifeCycle.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.buttonServiceBindDemo://0 .setOnClickListener(onClickListener);
                    {
                        Toast.makeText(getApplicationContext(), "band服务", Toast.LENGTH_SHORT).show();
//注意一定要把Activity注册到manifest文件
                        Intent intent = new Intent( MainActivity.this, ServiceBindDemo.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonBroadcast1://Clear
                    {
                        Toast.makeText(getApplicationContext(), "广播1", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( MainActivity.this, BroadcastLifeCycleMain.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonBroadcast2://.
                    {
                        Toast.makeText(getApplicationContext(), "广播2", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( MainActivity.this, BroadcastBattery.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonSharedPreferences://操作符+
                    {
                        Toast.makeText(getApplicationContext(), "共享参数", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( MainActivity.this, SharedPreferencesDemo.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonDatabaseSQLite://操作符-
                    {
                        Toast.makeText(getApplicationContext(), "本地数据库", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( MainActivity.this, DatabaseMain.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.buttonContentProvider://操作符*
                    {
                        Toast.makeText(getApplicationContext(), "内容提供器", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( MainActivity.this, ContentProviderDemo.class);
                        startActivity(intent);
                        break;
                    }*/

                    case R.id.buttonWeatherJSON://操作符 /
                    {
                        Toast.makeText(getApplicationContext(), "JSON天气预报", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( MainActivity.this, JsonDemo.class);
                        startActivity(intent);
                        break;
                    }
                    /*case R.id.buttonThreadUpdate://操作符 =
                    {
                        Toast.makeText(getApplicationContext(), "子线程更新UI", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( MainActivity.this, ThreadDemo.class);
                        startActivity(intent);
                        break;
                    }*/
                    default:
                        break;
                }
            }catch(Exception e){
                Log.e("David","error");
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                text.setText(usernumber+" ("+username+")");
            }

        }
    }

}
