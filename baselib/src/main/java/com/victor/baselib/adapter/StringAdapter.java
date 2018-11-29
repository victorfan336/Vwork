package com.victor.baselib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.baselib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/23
 */
public class StringAdapter extends RecyclerView.Adapter {

    private List<String> list = new ArrayList<>();
    private Context context;

    public StringAdapter(Context context,List<String> list) {
        this.context = context;
        if (list != null) {
            this.list = list;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StringViewHolder(LayoutInflater.from(context).inflate(R.layout.string_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StringViewHolder stringViewHolder = (StringViewHolder) holder;
        stringViewHolder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    private class StringViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public StringViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
        }

        public void bindData(String content) {
            if (content != null) {
                textView.setText(content);
            }
        }
    }

}
