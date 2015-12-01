package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOne extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  TextView tv_title_one = (TextView)getView().findViewById(R.id.tv_title_one);
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        TextView tv_title_one = (TextView)view.findViewById(R.id.tv_title_one);
        //this for get object bundle
        //that sent from fiveactivity
        Bundle bundle = getArguments();
        if (bundle != null) {
            if(bundle.containsKey("title_one")) {
                tv_title_one.setText(bundle.getString("title_one"));
            }
        }
        //end get object bundle
//        return inflater.inflate(R.layout.fragment_fragment_one, container, false);
        return view;
    }

}
