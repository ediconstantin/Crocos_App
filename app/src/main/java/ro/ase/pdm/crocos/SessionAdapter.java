package ro.ase.pdm.crocos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import entities.Session;

public class SessionAdapter extends BaseAdapter {

    List<Session> sessions;
    Context context;
    int resource;

    public SessionAdapter(Context context, int resource, List<Session> sessions) {
        this.sessions = sessions;
        this.context = context;
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
            holder.listItem = convertView.findViewById(R.id.lbSessionItem);
            holder.colorBox = convertView.findViewById(R.id.lbColorBox);
            convertView.setTag(holder);
        }
        Session session = sessions.get(position);
        holder = (ViewHolder)convertView.getTag();
        holder.listItem.setText(session.getTestName());
        int status = session.getStatus();
        int resID;
        if(status==1){
            resID = R.drawable.gradientbackground;
                holder.colorBox.setBackgroundResource(resID);
            } else if(status==0){
            resID = R.drawable.yellowbackground;
            holder.colorBox.setBackgroundResource(resID);

        }else if (status==2){
            resID = R.drawable.redbackground;
            holder.colorBox.setBackgroundResource(resID);
        }

        return convertView;
    }

    static class ViewHolder{
        public TextView listItem;
        public TextView colorBox;
    }

}
