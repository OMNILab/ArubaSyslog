package sjtu.com.apdetector;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Button test,test2,test3,test4;
    DataBase data;
    AlarmManager amanager;
    GPS_Server gps;
    WifiManager wm;
    WifiInfo wInfo;
    private LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data=DataBase.GetData(this);
        test=(Button)findViewById(R.id.test);
        test2=(Button)findViewById(R.id.test2);
        test3=(Button)findViewById(R.id.test3);
        test4=(Button)findViewById(R.id.test4);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
        }
        gps=new GPS_Server(this);
        wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        wInfo = wm.getConnectionInfo();
        amanager=(AlarmManager)getSystemService(Service.ALARM_SERVICE);
        Intent intent=new Intent(MainActivity.this,FirstService.class);
        final PendingIntent pi=PendingIntent.getService(MainActivity.this,0,intent,0);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amanager.setRepeating(AlarmManager.ELAPSED_REALTIME,0,5000,pi);
                test.setEnabled(false);
                test3.setEnabled(true);
                Toast.makeText(MainActivity.this,"后台程序已启动",Toast.LENGTH_SHORT).show();
            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setEnabled(true);
                amanager.cancel(pi);
                Toast.makeText(MainActivity.this,"后台程序已停止",Toast.LENGTH_SHORT).show();
            }
        });
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,History.class);
                startActivity(intent2);
            }
        });
        test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = data.getReadableDatabase().rawQuery("select * from data", null);
                ExportToCSV(c, "test.csv");
            }
        });
    }
    public void ExportToCSV(Cursor c, String fileName) {

        int rowCount = 0;
        int colCount = 0;
        FileWriter fw;
        BufferedWriter bfw;
        String sdCardDir = Environment.getExternalStorageDirectory()
                .getAbsolutePath()+"/"+fileName;

        File saveFile = new File(sdCardDir);
        try {

            rowCount = c.getCount();
            colCount = c.getColumnCount();
            fw = new FileWriter(saveFile);
            bfw = new BufferedWriter(fw);
            if (rowCount > 0) {
                c.moveToFirst();
                // 写入表头
                for (int i = 0; i < colCount; i++) {
                    if (i != colCount - 1)
                        bfw.write(c.getColumnName(i) + ',');
                    else
                        bfw.write(c.getColumnName(i));
                }
                // 写好表头后换行
                bfw.newLine();
                // 写入数据
                for (int i = 0; i < rowCount; i++) {
                    c.moveToPosition(i);
                    // Toast.makeText(mContext, "正在导出第"+(i+1)+"条",
                    // Toast.LENGTH_SHORT).show();
                    Log.v("导出数据", "正在导出第" + (i + 1) + "条");
                    for (int j = 0; j < colCount; j++) {
                        if (j != colCount - 1)
                            bfw.write(c.getString(j) + ',');
                        else
                            bfw.write(c.getString(j));
                    }
                    // 写好每条记录后换行
                    bfw.newLine();
                }
            }
            // 将缓存数据写入文件
            bfw.flush();
            // 释放缓存
            bfw.close();
            // Toast.makeText(mContext, "导出完毕！", Toast.LENGTH_SHORT).show();
            Log.v("导出数据", "导出完毕！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            c.close();
        }
    }
}
