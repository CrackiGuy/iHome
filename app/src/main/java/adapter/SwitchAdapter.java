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
import cracki.ihome.com.ihome.SwitchHolder;

/**
 * Created by Cracki on 11/6/2018.
 */

public class SwitchAdapter extends RecyclerView.Adapter<SwitchHolder> {
    Context context;
    ArrayList<SwitchBtn> data;


    public SwitchAdapter(Context context,ArrayList<SwitchBtn> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SwitchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_switch,parent,false);
        return new SwitchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwitchHolder holder, int position) {
        boolean status = true;
        if (data.get(position).getStatus().toString().equals("1")){
            status = true;
        }else if (data.get(position).getStatus().toString().equals("0")){
            status = false;
        }
        holder.txt_title.setText(data.get(position).getName());
        holder.switch_one.setChecked(status);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
