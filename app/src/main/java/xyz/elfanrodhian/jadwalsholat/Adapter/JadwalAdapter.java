package xyz.elfanrodhian.jadwalsholat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.elfanrodhian.jadwalsholat.JadwalActivity;
import xyz.elfanrodhian.jadwalsholat.R;

/**
 * Created by elfar on 09/01/18.
 */

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> {
    Context ctx;
    List<xyz.elfanrodhian.jadwalsholat.Model.Item> itemList;

    public JadwalAdapter(Context ctx, List<xyz.elfanrodhian.jadwalsholat.Model.Item> itemList) {
        this.ctx = ctx;
        this.itemList = itemList;
    }

    @Override
    public JadwalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jd, parent,false);
        JadwalViewHolder vh = new JadwalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(JadwalViewHolder holder, int position) {
        final xyz.elfanrodhian.jadwalsholat.Model.Item itemJadwal = itemList.get(position);
        holder.date.setText("Tanggal : "+itemJadwal.getDateFor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = itemJadwal.getDateFor();
                Intent i = new Intent(v.getContext(), JadwalActivity.class);
                i.putExtra("link", link);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class JadwalViewHolder extends RecyclerView.ViewHolder {
        TextView date;

        public JadwalViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.txtDate);
        }
    }
}
