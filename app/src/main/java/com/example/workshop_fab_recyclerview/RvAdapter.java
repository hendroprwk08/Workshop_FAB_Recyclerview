package com.example.workshop_fab_recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//3 kali alt + enter ditulisan CardViewHolder
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.CardViewHolder> {
    ArrayList<Siswa> siswas;
    Context context;

    //alt + ins ---> construct ---> pilih context
    public RvAdapter(Context context) {
        this.context = context;
    }

    //alt + ins ---> getter and setter ---> pilih ArrayList<Siswa>
    public ArrayList<Siswa> getSiswas() {
        return siswas;
    }

    public void setSiswas(ArrayList<Siswa> siswas) {
        this.siswas = siswas;
    }

    @NonNull
    @Override
    public RvAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //tarik layout cardview disini
        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.card_view_layout, viewGroup, false);

        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.CardViewHolder cardViewHolder, int i) {
        //taruh data disini
        cardViewHolder.tvNama.setText(getSiswas().get(i).getNama());
        cardViewHolder.tvInstansi.setText(getSiswas().get(i).getInstansi());

        final String nama, instansi;
        nama = getSiswas().get(i).getNama();
        instansi = getSiswas().get(i).getInstansi();

        //tempat melempar data ke detail activity
        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.saveArrayList(context, siswas, "list_siswa");

                //set bundle
                Bundle b = new Bundle();
                b.putString("b_nama", nama);
                b.putString("b_instansi", instansi);

                //sisipkan kedalam intent
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtras(b);

                //buka detail activity
                ((Activity) context).startActivity(i);
                }
        });
    }

    @Override
    public int getItemCount() {
        return getSiswas().size();
    }

    //alt + enter
    public class CardViewHolder extends RecyclerView.ViewHolder {
        // ini tempat untuk sinkronisasi layout dengan data
        TextView tvNama, tvInstansi;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = (TextView) itemView.findViewById(R.id.tv_cv_nama);
            tvInstansi = (TextView) itemView.findViewById(R.id.tv_cv_instansi);
        }
    }
}
