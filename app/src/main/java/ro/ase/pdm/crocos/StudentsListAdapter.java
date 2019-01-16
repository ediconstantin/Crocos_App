package ro.ase.pdm.crocos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import entities.Student;


public class StudentsListAdapter extends BaseAdapter {


    List<Student> students;
    Context context;
    int resource;

    public StudentsListAdapter( Context context, int resource, List<Student> students) {
        this.students = students;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
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
            holder.firstName = convertView.findViewById(R.id.lbStudentFirstName);
            holder.lastName = convertView.findViewById(R.id.lbStudentLastName);
            holder.tvGroup = convertView.findViewById(R.id.lbStudentGroup);
            convertView.setTag(holder);
        }
        Student student = students.get(position);

        holder = (ViewHolder)convertView.getTag();
        holder.firstName.setText(student.getFirstName());
        holder.lastName.setText(student.getLastName());
        holder.tvGroup.setText(student.getGroup());

        return convertView;
    }

    static class ViewHolder{
        public TextView firstName;
        public TextView lastName;
        public TextView tvGroup;
    }

}
