package ro.ase.pdm.crocos;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entities.GlobalVar;
import entities.Session;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;
import utils.Constant;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinFragment extends Fragment implements Constant {



    Button btnJoin;
    Session session;
    String token;
    EditText etToken;

    public JoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_join, container, false);


        etToken = view.findViewById(R.id.etJoinSession);
        btnJoin = view.findViewById(R.id.btnJoin);
        session = new Session();

        token = etToken.getText().toString().trim();


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){
                            session = JSONifier.jsonToSession(response.getResponse());

                        } else {
                            Toast.makeText(view.getContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                httpHandler.execute(GET_METHOD, API_URL + "/session/public/" + token);
            }
        });


        return view;
    }

}
