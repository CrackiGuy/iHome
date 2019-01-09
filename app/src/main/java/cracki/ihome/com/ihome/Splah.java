package cracki.ihome.com.ihome;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Splah extends AppCompatActivity {
    Button btn;
    EditText edt;
    Session session;
    SessionManger sessionManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);

        sessionManger = new SessionManger(this);
        sessionManger.checkKey();
//        if (!sessionManger.isLoggin()){
//            this.finish();
//        }

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        btn = findViewById(R.id.btn_start);
        edt = findViewById(R.id.editText);
        session = Session.newInstance(this);
        if(edt.getText().toString().equals(null)){
            btn.setClickable(false);
        }else {
            btn.setClickable(true);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.set("server",edt.getText().toString().trim());
                Intent mainIntent = new Intent(Splah.this,Home.class);
                Splah.this.startActivity(mainIntent);
                Splah.this.finish();
            }
        });
    }
}
