package ro.ase.pdm.crocos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import entities.Question;

public class OtherQuestionsAdapter extends BaseAdapter {
    List<Question> questions;
    Context context;
    int resource;

    public OtherQuestionsAdapter(Context context, int resource, List<Question> objects) {
        this.context=context;
        this.questions=objects;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
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
            holder.listItem = convertView.findViewById(R.id.lbListItem);
            convertView.setTag(holder);
        }

        Question question = questions.get(position);
        holder = (ViewHolder)convertView.getTag();
        holder.listItem.setText(question.getQuestion());
        return convertView;
    }

    static class ViewHolder{
        public TextView listItem;
    }

}
