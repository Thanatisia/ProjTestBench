package com.personal.projtestbench.templates;

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
import java.util.HashMap;

import com.personal.projtestbench.R;
import com.personal.projtestbench.database.dbConstants;
import com.personal.projtestbench.utilities;

/* Must Have */
import com.personal.projtestbench.database.dbConstants;
import com.personal.projtestbench.pages.DebugMenu;
import com.personal.projtestbench.utilities.utilToast;
import com.personal.projtestbench.utilities.utilString;
import com.personal.projtestbench.utilities.utilValidation;
import com.personal.projtestbench.utilities.utilHashMap;
import com.personal.projtestbench.utilities.utilDebugger;
import com.personal.projtestbench.utilities.utilIntents;
import com.personal.projtestbench.utilities.utilFiles;

public class activity_class extends AppCompatActivity {

    //Classes
    dbConstants const_db;
    utilities util;
    utilToast util_Toast;
    utilString util_String;
    utilValidation util_Validation;
    utilHashMap util_Hashmap;
    utilDebugger util_Debugger;
    utilIntents util_Intents;
    utilFiles util_Files;

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

        //Initialize all classes and widgets - must have
        init();

    }

    public void init()
    {
        init_class();
        init_widgets();
    }

    public void init_class()
    {
        //Basics
        const_db = new dbConstants();
        util = new utilities();
        util_Toast = new utilToast();
        util_String = new utilString();
        util_Validation = new utilValidation();
        util_Hashmap = new utilHashMap();
        util_Debugger = new utilDebugger();
        util_Intents = new utilIntents();
        util_Files = new utilFiles();
    }

    public void init_widgets()
    {
        //Initialize widgets here
    }

    //Intents control
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
