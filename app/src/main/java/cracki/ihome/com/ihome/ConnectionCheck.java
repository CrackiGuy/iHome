package cracki.ihome.com.ihome;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tapadoo.alerter.Alerter;

/**
 * Created by Cracki on 11/5/2018.
 */

public class ConnectionCheck {
    Context context;
    public ConnectionCheck(Context context){
        this.context = context;
    }

    public boolean isConnected() {
        ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(conn != null){
            NetworkInfo info = conn.getActiveNetworkInfo();
            if(info != null){
                if (info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }
        public void checkInternet(){
            if(!new ConnectionCheck(context).isConnected()){
                if(Alerter.isShowing()){
                    Alerter.hide();
                }
                Alerter.create((Activity) context)
                        .setTitle("No Connection!")
                        .setText("No Internet Access!")
                        .setBackgroundColorRes(R.color.primaryDarkColor)
                        .setIcon(R.drawable.ic_cloud_off)
                        .setIconColorFilter(0)
                        .setDuration(5000)
                        .show();
            }
        }

}
