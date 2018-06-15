package com.example.zsoft.calendar_for_nm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AZelinskiy on 13.06.2018.
 */

/*номер или имя
* запрос: получаю массив 200строк
*   имя
*   номер
*   время1
*   дату
*   таймштамп
*
*
*
*   */

/*select *,count(sf_num) from clients
group by sf_num having count(sf_num)>10*/

public class Fragment_search extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search,container,false);

        final Spinner spinner= view.findViewById(R.id.spinner);
        final EditText editText=view.findViewById(R.id.editText3);
        final ExpandableListView expandableListView=view.findViewById(R.id.expandData);
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


                creatMap(creatListForMap(search_cl.search(getContext(),qu_searsh)));
                BaseExpandableListAdapter adapter=new Adapter_Expandable(getContext(),
                        creatMap(creatListForMap(search_cl.search(getContext(), qu_searsh))));
                expandableListView.setAdapter(adapter );
                // Log.i("clk",""+position);
                expandableListView.expandGroup(0);



                expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        Log.d("onGroupClick:", "worked");
                        parent.expandGroup(groupPosition);
                        return false;

                    }
                });
                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Toast.makeText(getContext(),"--",Toast.LENGTH_SHORT)
                                .show();

                        return false;
                    }
                });


            }
        });

        return view;
    }

    List<Constructor_search> creatListForMap(List<String> search){
        List<Constructor_search> listreqestSearch=new ArrayList<>();
        for(int i=0;i<search.size();i++){
            if(i%5==0)
            listreqestSearch.add(new Constructor_search(
                   search.get(i),
                   search.get(i+1),
                   search.get(i+2),
                   search.get(i+3),
                   search.get(i+4))
                   );
        }
        return listreqestSearch;
    }

    HashMap<String, List<Constructor_search>> creatMap(List<Constructor_search> reqest){

        HashMap<String,List<Constructor_search>> search=new HashMap<>();


        for(int i=0;i<reqest.size();i++){

            if(search.containsKey(reqest.get(i).sf_num)){

                List<Constructor_search> val=new ArrayList<>();
                val=search.get(reqest.get(i).sf_num);
                //val.add(reqest.get(i).name);
                val.add(new Constructor_search(
                        reqest.get(i).sf_num,
                        reqest.get(i).name,
                        reqest.get(i).time1,
                        reqest.get(i).date,
                        reqest.get(i).date1
                ));

                search.put(reqest.get(i).sf_num,val);
            }else{

                List<Constructor_search> val=new ArrayList<>();
               val.add(new Constructor_search(
                        reqest.get(i).sf_num,
                        reqest.get(i).name,
                        reqest.get(i).time1,
                        reqest.get(i).date,
                        reqest.get(i).date1
                ));
                search.put(reqest.get(i).sf_num,val);
            }



            }



           // for(List elt: search.values()){

           // Log.i("MapKey",elt.toString());
           // }
return search;

    }
}
