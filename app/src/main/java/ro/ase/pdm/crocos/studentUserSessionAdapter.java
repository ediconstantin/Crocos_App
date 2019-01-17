package ro.ase.pdm.crocos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import entities.UserSession;

public class studentUserSessionAdapter extends BaseAdapter {

    Context context;
    List<UserSession> sessions;
    int resource;

    public studentUserSessionAdapter(Context context,  int resource, List<UserSession> sessions) {
        this.context = context;
        this.sessions = sessions;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int position) {
        return sessions.get(position);
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
            holder.sessionName= convertView.findViewById(R.id.lbStudentSessionItem);
            holder.result=convertView.findViewById(R.id.lbResultItem);
            convertView.setTag(holder);
        }

        UserSession session = sessions.get(position);
        holder = (ViewHolder)convertView.getTag();
        holder.sessionName.setText(session.getTestName());
        holder.result.setText(String.valueOf(session.getResult())+ " right answers");
        return convertView;
    }

    static class ViewHolder{
        public TextView sessionName;
        public TextView  result;
    }
}
