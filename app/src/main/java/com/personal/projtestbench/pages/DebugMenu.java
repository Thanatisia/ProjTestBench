package com.personal.projtestbench.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.personal.projtestbench.R;
import com.personal.projtestbench.database.dbConstants;
import com.personal.projtestbench.utilities;

import com.personal.projtestbench.database.dbConstants;
import com.personal.projtestbench.pages.DebugMenu;
import com.personal.projtestbench.utilities.utilToast;
import com.personal.projtestbench.utilities.utilString;
import com.personal.projtestbench.utilities.utilValidation;
import com.personal.projtestbench.utilities.utilHashMap;
import com.personal.projtestbench.utilities.utilDebugger;

import java.util.HashMap;

public class DebugMenu extends AppCompatActivity {

    //Classes
    dbConstants const_db;
    utilities util;
    utilities.utilToast util_Toast;
    utilities.utilString util_String;
    utilities.utilValidation util_Validation;
    utilities.utilHashMap util_Hashmap;
    utilities.utilDebugger util_Debugger;

    // Device Info
    Context c = this;
    Activity a = this;
    Application app = getApplication();

    /* Widgets */

    /* Constants */
    String public_external_directory_root = Environment.getExternalStorageDirectory().toString();
    String db_Path;
    String db_Path_Alt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_menu);

        //Initialize all classes and widgets
        init();

        try {
            //util_Hashmap.hashmap_example_1(c, util_Toast.LENGTH_LONG);
            util_Hashmap.hashmap_example_2_forloop(c, util_Toast.LENGTH_LONG);
        }
        catch (Exception ex)
        {
            HashMap<?, ?> ex_map = util_Debugger.catch_exception(ex);
            util_Debugger.print_Exception(c, ex_map, Toast.LENGTH_LONG);
        }
    }

    public void init()
    {
        init_class();
        init_widgets();
    }

    public void init_class()
    {
        const_db = new dbConstants();
        util = new utilities();
        util_Toast = new utilToast();
        util_String = new utilString();
        util_Validation = new utilValidation();
        util_Hashmap = new utilHashMap();
        util_Debugger = new utilDebugger();
    }
    public void init_widgets()
    {

    }


    public Intent create_intent(Intent intent_object, Context c, Class<?> target_class)
    {
        intent_object = new Intent(c, target_class);
        return intent_object;
    }

    //Overload function if intent_object variable is found
    public Intent create_intent(Context c, Class<?> target_class)
    {
        Intent myintent = new Intent(c, target_class);
        return myintent;
    }

    public void start_intent(Intent intent)
    {
        startActivity(intent);
    }
}
