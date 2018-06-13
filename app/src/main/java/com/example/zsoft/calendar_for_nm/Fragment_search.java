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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                creatListForMap(search_cl.search(getContext(),qu_searsh));

            }
        });

        return view;
    }

    void creatListForMap(List<String> search){
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

    }

    void creatMap(List reqest){

        HashMap<String,List<String>> search=new HashMap<>();

        for(Object elt:reqest){



        }

    }
}
