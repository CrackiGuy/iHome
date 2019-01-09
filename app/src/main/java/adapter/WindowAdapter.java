package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cracki.ihome.com.ihome.R;
import cracki.ihome.com.ihome.SwitchBtn;
import cracki.ihome.com.ihome.WindowBtn;
import cracki.ihome.com.ihome.WindowHolder;

/**
 * Created by Cracki on 11/11/2018.
 */

public class WindowAdapter extends RecyclerView.Adapter<WindowHolder> {
    Context context;
    ArrayList<WindowBtn> data;


    public WindowAdapter(Context context, ArrayList<WindowBtn> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public WindowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_window,parent,false);
        return new WindowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WindowHolder holder, int position) {
        boolean status = true;
        if (data.get(position).getStatus().toString().equals("1")){
            status = true;
        }else if (data.get(position).getStatus().toString().equals("0")){
            status = false;
        }
        holder.txt_title.setText(data.get(position).getName());
        holder.switch_one.setChecked(status);
        holder.switch_one.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
