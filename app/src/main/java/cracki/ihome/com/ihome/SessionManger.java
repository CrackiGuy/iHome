package cracki.ihome.com.ihome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Cracki on 11/5/2018.
 */

public class SessionManger {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE =0;

    private    static  final String PREFF_NAME = "LOGIN";
    private   static  final String LOGIN = "IS_LOGIN";
    public  static  final String NAME = "NAME";
    public  static  final String EMAIL = "EMAIL";
    public  static  final String ID = "ID";

    public SessionManger(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void createSession(String name,String email,String id){
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(ID,id);
        editor.apply();
    }
    public boolean  isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void  checkLogin(){
        if(!this.isLoggin()){
            Intent i = new Intent(context,Signin.class);
            context.startActivity(i);
            ((Home)context).finish();
        }
    }
    public void  checkKey(){
        if(this.isLoggin()){
            Intent i = new Intent(context,Home.class);
            context.startActivity(i);
            ((Splah)context).finish();
        }
    }
    public HashMap<String,String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(ID,sharedPreferences.getString(ID,null));

        return  user;
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context,Signin.class);
        context.startActivity(i);
        ((Home)context).finish();
    }
}
