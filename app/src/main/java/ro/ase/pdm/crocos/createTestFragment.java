package ro.ase.pdm.crocos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class createTestFragment extends Fragment {

    Button btn;
    QuestionsFragment questionsFragment;

    public createTestFragment() {
        // Required empty public constructor
        questionsFragment = new QuestionsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_create_test, container, false);

        btn = rootView.findViewById(R.id.btnAdd);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, questionsFragment);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }



}
