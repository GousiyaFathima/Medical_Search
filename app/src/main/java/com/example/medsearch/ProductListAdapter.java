package com.example.medsearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Product> mProductList;

    public ProductListAdapter(Context mcontext, List<Product> mProductList) {
        this.mcontext = mcontext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = View.inflate(mcontext, R.layout.list_item, null);
        TextView tvname = (TextView) v.findViewById(R.id.name);
        TextView tvstreet = (TextView) v.findViewById(R.id.street);
        TextView tvplace = (TextView) v.findViewById(R.id.place);
        TextView tvpno = (TextView) v.findViewById(R.id.pno);

        tvname.setText(mProductList.get(i).getName());
        tvstreet.setText(mProductList.get(i).getStreet());
        tvplace.setText(mProductList.get(i).getPlace());
        tvpno.setText(mProductList.get(i).getPno());

        v.setTag(mProductList.get(i).getid());
        return v;
    }
}
