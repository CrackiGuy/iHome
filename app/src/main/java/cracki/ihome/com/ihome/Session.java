package cracki.ihome.com.ihome;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cracki on 11/7/2018.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE =0;
    private    static  final String PREFF_NAME = "Session";

    public Session(){

    }

    public static Session newInstance(Context context){
        Session session = new Session(context);
        return  session;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void set(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }
    public String get(String key){
        return sharedPreferences.getString(key,"no");
    }
}
