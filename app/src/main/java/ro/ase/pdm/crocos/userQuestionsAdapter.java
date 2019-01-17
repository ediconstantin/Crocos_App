package ro.ase.pdm.crocos;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import entities.Answer;

public class userQuestionsAdapter extends BaseAdapter {

    List<Answer> answers;
    Context context;
    int resource;

    public userQuestionsAdapter(Context context, int resource, List<Answer> answers) {
        this.answers = answers;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Object getItem(int position) {
        return answers.get(position);
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
            holder.questionId = convertView.findViewById(R.id.tvQuestionID);
            holder.correctValue = convertView.findViewById(R.id.tvCorrectQuestionValue);
            holder.givenValue = convertView.findViewById(R.id.tvGivenValue);
            holder.feedbackValue = convertView.findViewById(R.id.tvFeedbackValue);
            holder.layout = convertView.findViewById(R.id.layoutID);
            convertView.setTag(holder);
        }
        Answer answer = answers.get(position);
        holder = (ViewHolder)convertView.getTag();
        String label = "Question";
        holder.questionId.setText(label+String.valueOf(position+1));
        holder.correctValue.setText(answer.getCorrectAnswer());
        holder.givenValue.setText(answer.getGivenAnswer());
        holder.feedbackValue.setText(answer.getFeedback());
        int resID;

        if(answer.getCorrectAnswer().trim().equalsIgnoreCase(answer.getGivenAnswer().trim())){
            resID = R.drawable.green_border;
            holder.layout.setBackgroundResource(resID);
        }
        else if(!(answer.getCorrectAnswer().trim().equalsIgnoreCase(answer.getGivenAnswer().trim()))){
            resID = R.drawable.red_border;
            holder.layout.setBackgroundResource(resID);
        }



        if(answer.isOpen()){
            resID = R.drawable.blue_border;
            holder.layout.setBackgroundResource(resID);
            //holder.openQuestion.setVisibility(View.VISIBLE);
        }
        else{
            //holder.openQuestion.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }

    static class ViewHolder{
        public TextView questionId;
        public TextView correctValue;
        public TextView givenValue;
        public TextView feedbackValue;
        public ConstraintLayout layout;
    }

}
