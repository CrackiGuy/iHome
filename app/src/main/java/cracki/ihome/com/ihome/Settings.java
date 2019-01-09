package cracki.ihome.com.ihome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Settings extends AppCompatActivity {
    EditText edtServer;
    ImageButton imgSave;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        session = Session.newInstance(this);

        edtServer = findViewById(R.id.edit_server);
        imgSave = findViewById(R.id.btn_save);

        edtServer.setText(session.get("server"));

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.set("server",edtServer.getText().toString().trim());
                edtServer.setText(session.get("server"));
            }
        });
    }
}
