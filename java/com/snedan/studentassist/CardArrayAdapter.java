package com.snedan.studentassist;

/**
 * Created by Snedan on 19-01-2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter  extends ArrayAdapter<Card> {
    private static final String TAG = "CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();

    static class CardViewHolder {
        TextView line1;
        TextView line2;
    }

    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.tv_name);
            viewHolder.line2 = (TextView) row.findViewById(R.id.tv_details);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        if(card.getLine1()==999)
        {
            viewHolder.line1.setText("N.A.");
            viewHolder.line1.setTextColor(0xfff44336);
            viewHolder.line2.setText(card.getLine2());
            viewHolder.line2.setTextColor(0xffffffff);
        }
        else if(card.getLine1()>=SubjectActivity.minPercent)
        {
            viewHolder.line1.setText(card.getLine1()+"%");
            viewHolder.line1.setTextColor(0xff4caf50);
            viewHolder.line2.setText(card.getLine2());
            viewHolder.line2.setTextColor(0xffffffff);
        }
        else {
            viewHolder.line1.setText(card.getLine1()+"%");
            viewHolder.line1.setTextColor(0xfff44336);
            viewHolder.line2.setText(card.getLine2());
            viewHolder.line2.setTextColor(0xffffffff);
        }
        return row;
    }
}
