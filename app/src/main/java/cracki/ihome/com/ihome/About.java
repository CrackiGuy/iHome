package cracki.ihome.com.ihome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class About extends AppCompatActivity {
    TextView mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mail = findViewById(R.id.appContVal);

        mail.setText(Html.fromHtml("<a href=\"mailto:naylinaung.nla.xo@gmail.com\">naylinaung.nla.xo@gmail.com</a>"));
        mail.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
