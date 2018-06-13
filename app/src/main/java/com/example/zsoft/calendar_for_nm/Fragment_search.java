package com.example.zsoft.calendar_for_nm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by AZelinskiy on 13.06.2018.
 */

public class Fragment_search extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search,container,false);

        final Spinner spinner= view.findViewById(R.id.spinner);
        final EditText editText=view.findViewById(R.id.editText3);
        final GridView gridView=view.findViewById(R.id.gridSearch);
        Button button=view.findViewById(R.id.button);

        String[]param={"Номер","Имя"};
        ArrayAdapter adapter =new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,param);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int attr= (int) spinner.getSelectedItemId();
                String coloumn = null;

                switch (attr) {
                    case 0:
                        coloumn="sf_num";
                        break;

                    case 1:
                        coloumn="name";
                        break;
                }

                String qu_searsh="select \n" +
                        "       name,    \n" +
                        "       time1,   \n" +
                        "       sf_num,  \n" +
                        "       pay,     \n" +
                        "       date,    \n" +
                        "       date1    \n" +
                        "                \n" +
                        "from clients where "+coloumn+" like '%"+editText.getText()+
                        "%' order by date DESC limit 200";

                ExecDB search_cl=new ExecDB();



                final ArrayAdapter res_search = new ArrayAdapter(getContext(), android.R.layout.
                        simple_list_item_1,search_cl.search(getContext(),qu_searsh));
                gridView.setAdapter(res_search);

            }
        });

        return view;
    }
}
