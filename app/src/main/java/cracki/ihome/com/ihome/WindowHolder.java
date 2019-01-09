package cracki.ihome.com.ihome;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Cracki on 11/11/2018.
 */

public class WindowHolder extends RecyclerView.ViewHolder{
    public TextView txt_title;
    public Switch switch_one;

    public WindowHolder(final View itemView) {
        super(itemView);
        txt_title = itemView.findViewById(R.id.card_title);
        switch_one = itemView.findViewById(R.id.switch_one);
    }
}
