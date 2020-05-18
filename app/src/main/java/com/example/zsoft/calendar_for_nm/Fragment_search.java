package com.example.zsoft.calendar_for_nm;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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

    String value;
    String coloumn;
    int attr;
    ExpandableListView expandableListView;
    EditText editText;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search,container,false);


        final Spinner spinner= view.findViewById(R.id.spinner);
         editText=view.findViewById(R.id.editText3);
       expandableListView=view.findViewById(R.id.expandData);
        Button button=view.findViewById(R.id.button);

        String[]param={getResources().getString(R.string.serch_exp_num),getResources().getString(R.string.serch_exp_name)};
        ArrayAdapter adapter =new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,param);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        editText.getText().clear();
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        editText.removeTextChangedListener(new PhoneNumberFormattingTextWatcher());
                        break;

                    case 0:
                        editText.getText().clear();
                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            editText.addTextChangedListener(new PhoneNumberFormattingTextWatcher(
                                    Locale.getDefault().getCountry()));
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
// input number and click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 attr= (int) spinner.getSelectedItemId();
                 coloumn = null;

                switch (attr) {
                    case 0:
                        coloumn="sf_num";
                        value=editText.getText()
                                .toString().replaceAll("\\D+","");
                        break;

                    case 1:
                        coloumn="name";
                        value=editText.getText().toString();
                        break;
                }
                setList();
            }
        });
    return view;
    }

    public  void setList(){
        String qu_searsh="select \n" +
                "       name,    \n" +
                "       time1,   \n" +
                "       sf_num,  \n" +
                "       pay,     \n" +
                "       date,    \n" +
                "       date1,    \n" +
                "       time_stamp    \n" +
                "                \n" +
                "from clients where "+coloumn+" like '%"+value+
                "%' order by date DESC limit 400";

        ExecDB search_cl     = new ExecDB();
        //rawdata
        List<String> sqlREsp = search_cl.search(getContext(), qu_searsh);
        //name->ordParam
        HashMap<String, List<Constructor_search>> dataToAdapter
                = creatMap(creatListForMap(sqlREsp));

        final BaseExpandableListAdapter adapter=new Adapter_Expandable(
                getContext(),dataToAdapter);

        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                new HelperData().HideKeyboeard(v);
                editText.setText(adapter.getGroup(groupPosition).toString());
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        setList();
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    List<Constructor_search> creatListForMap(List<String> search){
        List<Constructor_search> listreqestSearch=new ArrayList<>();
        for(int i=0;i<search.size();i++){
            if(i%6==0)
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
return search;

    }
}
