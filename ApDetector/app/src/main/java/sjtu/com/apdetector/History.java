package sjtu.com.apdetector;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by colin1434 on 2015/4/9.
 */
public class History extends Activity
{
    ListView list;
    private ArrayAdapter<String> adapt;
    private List<Map<String, String>> dataList=new ArrayList<Map<String, String>>();
    private Cursor cursor=null;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        DataBase sq=DataBase.GetData(this);
        list=(ListView)findViewById(R.id.list);
        Cursor cursor=null;
        dataList.clear();
        Map<String, String> map = new HashMap<String, String>();
        cursor=sq.getReadableDatabase().rawQuery("select distinct * from data order by _id DESC", null);

        while(cursor.moveToNext()){
            map=new HashMap<String, String>();
            map.put("tex1",cursor.getString(1));
            map.put("tex2",cursor.getString(2));
            map.put("tex3",cursor.getString(3));
            map.put("tex4",cursor.getString(4));
            dataList.add(map);
            simpleAdapter = new SimpleAdapter(History.this, dataList,
                    R.layout.wlist2, new String[] {"tex1", "tex2", "tex3","tex4"},
                    new int[] { R.id.tex1,R.id.tex2,R.id.tex3,R.id.tex4});
            list.setAdapter(simpleAdapter);
        }
        cursor.close();
        sq.close();
    }

}
