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
 * {@link FragmentPager.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPager extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static FragmentPager newInstance(String name, String email) {
        FragmentPager fragment = new FragmentPager();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("email", email);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_pager, container, false);
        TextView tv_name = (TextView)view.findViewById(R.id.tv_name_nine);
        TextView tv_email = (TextView)view.findViewById(R.id.tv_email);
        //this bundle will retrieve value bundle
        //from constructor bundl
        Bundle bundle = getArguments();
        tv_name.setText(bundle.getString("name"));
        tv_email.setText(bundle.getString("email"));
        return view;
    }


}