package com.example.sqlitecontactes;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private MyDB md;
    private LayoutInflater mInflater;
    private Cursor c;
    public WordListAdapter(Context context, MyDB md) {
        mInflater = LayoutInflater.from(context);
        this.md = md;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType){ // Inflate an item view.
                View mItemView = mInflater.inflate(R.layout.item, parent, false);
                return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        this.c=md.selectContact(md.getId(position));
        String mCurrent = c.getString(1);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return md.getCount();
    }



    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView wordItemView;
        private WordListAdapter mAdapter;
        private Boolean edited=false;
        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int index = getAdapterPosition();
            Context context = v.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater factory = LayoutInflater.from(context);
            final View textEntryView = factory.inflate(R.layout.info, null);
            final EditText text1 = (EditText) textEntryView.findViewById(R.id.textView4);
            Button button = (Button) textEntryView.findViewById(R.id.button);
            text1.setEnabled(false);
            Cursor c = md.selectContacts();
            c.move(index);
            builder.setTitle(c.getString(1));
            text1.setText(c.getString(2));
            builder.setView(textEntryView);
            edited=false;
            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (edited){
                        md.updateContact(md.getId(index),text1.getText().toString());
                    }
                    edited=false;
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text1.setEnabled(true);
                    edited=true;
                }
            });
            builder.show();
        }
    }
}
