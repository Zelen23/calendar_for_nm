package com.example.zsoft.calendar_for_nm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class UserEditActivity extends AppCompatActivity {

    EditText eName, eLastName, eNun, eAbout;
    Button bUserEditOK;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit);

        eName= findViewById(R.id.eUserEditName);
        eLastName= findViewById(R.id.eUserEditLastName);
        eNun= findViewById(R.id.eUserEditNnm);
        eAbout= findViewById(R.id.eUserEditAbout);

        Intent intent=getIntent();
        final String pk_num=intent.getStringExtra("pk_num");
        ArrayList<String> data = userObj(pk_num);

        eName.setText(data.get(1));
        eLastName.setText(data.get(2));
        eNun.setText(data.get(3));
        eAbout.setText(data.get(4));


        bUserEditOK=findViewById(R.id.bUserEdit);
        bUserEditOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            ExecDB execDB=new ExecDB();
            execDB.updateUser(UserEditActivity.this,
                    pk_num,
                    eNun.getText().toString(),
                    eName.getText().toString(),
                    eLastName.getText().toString(),
                    eAbout.getText().toString());
              finish();
            }
        });
    }

    public ArrayList<String> userObj(String pk_num){

        ExecDB execDB =new ExecDB();
        ArrayList<String> userData = execDB.getLine_(this, "user", pk_num);
        return userData;
    }
}
