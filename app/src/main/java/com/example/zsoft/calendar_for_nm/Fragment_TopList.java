package com.example.zsoft.calendar_for_nm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

/**
 * Created by AZelinskiy on 20.06.2018.
 */

public class Fragment_TopList extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.layout_top40,container,false);
        GridView gridView=view.findViewById(R.id.top40);


        Adapter_TopUser adapterTopUser=new Adapter_TopUser(getContext(),
                new ExecDB().userlist("limit 40",getContext()));
        gridView.setAdapter(adapterTopUser);

        return view;
    }




}
