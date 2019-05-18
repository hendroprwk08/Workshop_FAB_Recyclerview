package com.example.workshop_fab_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //tangkap bundle
        Bundle b = null;
        b = this.getIntent().getExtras();

        //letakkan isi bundle pada TextView
        //--- definisi widget dahulu
        final TextView tvNama = (TextView) findViewById(R.id.tv_nama);
        final TextView tvInstansi = (TextView) findViewById(R.id.tv_instansi);

        //--- letakkan bundle disini
        tvNama.setText(b.getString("b_nama"));
        tvInstansi.setText(b.getString("b_instansi"));

        getSupportActionBar().setTitle("Detail Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //alt + ins -> override methods

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
