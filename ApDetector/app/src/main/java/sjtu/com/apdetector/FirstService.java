package sjtu.com.apdetector;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by colin1434 on 2015/5/15.
 */
public class FirstService extends Service{
    private static final String TAG = "LocalService";
    WifiManager wm;
    SimpleDateFormat formatter    =   new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
    DataBase data;
    GPS_Server gps;
    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        data=DataBase.GetData(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(wm.isWifiEnabled()){
            Date curDate    =   new Date(System.currentTimeMillis());//获取当前时间
            String    str    =    formatter.format(curDate);
            WifiInfo wInfo = wm.getConnectionInfo();
            if(data.IsEquals(wInfo.getBSSID().replace(":",""))&&!wInfo.getBSSID().replace(":","").equals("000000000000")){
                gps=new GPS_Server(this);
                data.InsertData(str,wInfo.getBSSID().replace(":",""),String.valueOf(gps.get_longitude()),String.valueOf(gps.get_latitude()));
            };
        }
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
