package com.regent.tech.quote_saver.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.regent.tech.quote_saver.database.model.Quote;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.MyViewHolder>{

    private Context context;
    private List<Quote> quoteList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView quote;
        public TextView quote_author;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view){
            super(view);
        }

    }

    public QuotesAdapter(Context context, List<Quote> quoteList){
        this.context = context;
        this.quoteList = quoteList;
    }

}
