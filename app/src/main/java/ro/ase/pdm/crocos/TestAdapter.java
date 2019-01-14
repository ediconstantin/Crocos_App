package ro.ase.pdm.crocos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entities.Test;

public class TestAdapter extends BaseAdapter {
    List<Test> tests;
    Context context;
    int resource;

    public TestAdapter(Context context, int resource, List<Test> tests) {
        this.tests = tests;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return tests.size();
    }

    @Override
    public Object getItem(int position) {
        return tests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            holder = new ViewHolder();
            holder.testName= convertView.findViewById(R.id.lbTestItem);
            holder.testCategory=convertView.findViewById(R.id.lbTestCategory);
            convertView.setTag(holder);
        }

        Test test = tests.get(position);
        holder = (ViewHolder)convertView.getTag();
        holder.testName.setText(test.getName());
        holder.testCategory.setText(test.getCategory().getName());

        return convertView;
    }

    static class ViewHolder{
        public TextView testName;
        public TextView testCategory;
    }
}
