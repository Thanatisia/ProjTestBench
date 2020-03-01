package com.personal.projtestbench.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.personal.projtestbench.database.dbConstants;
import com.personal.projtestbench.database.dbUtilities;
import com.personal.projtestbench.pages.DebugMenu;
import com.personal.projtestbench.utilities;
import com.personal.projtestbench.utilities.utilToast;
import com.personal.projtestbench.utilities.utilString;
import com.personal.projtestbench.utilities.utilValidation;
import com.personal.projtestbench.utilities.utilHashMap;
import com.personal.projtestbench.utilities.utilDebugger;
import com.personal.projtestbench.utilities.utilIntents;
import com.personal.projtestbench.utilities.utilFiles;
import com.personal.projtestbench.utilities.utilSecurity;
import com.personal.projtestbench.utilities.utilSecurity.AccessControlList;
import com.personal.projtestbench.zipper;
import com.personal.projtestbench.zipper.ZipDirectory;
import com.personal.projtestbench.zipper.UnzipFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.personal.projtestbench.R;

public class AccessControlListMenu extends AppCompatActivity {

    /* Must have - Top half - Variables
    // Classes
    // Device Info
        Context c = this;
        Activity a = this;
        Application app = getApplication();
    // Widgets
    // Constants
     */

    // Classes
    dbConstants const_db;
    dbUtilities util_db;
    utilities util;
    utilToast util_Toast;
    utilString util_String;
    utilValidation util_Validation;
    utilHashMap util_Hashmap;
    utilDebugger util_Debugger;
    utilIntents util_Intents;
    utilFiles util_Files;
    zipper zip;
    UnzipFile unzipper;
    ZipDirectory zipDir;
    utilSecurity util_Security;
    AccessControlList acl;

    // Device Info
    Context c = this;
    Activity a = this;
    Application app = getApplication();

    // Widgets
    Button btn_ACLTestbench_Welcome;

    // Constants

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_control_list_menu);

        /* Must have - internal - Application Domain
            //Initialize
            init();
         */
        init();

        btn_ACLTestbench_Welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> acl_list = acl.acl(c);
                util_Toast.show_message(c, String.format("Hello ACL\n%s",acl_list), 1);
            }
        });
    }

    /* Must have - Bottom half - Functions
    public void init()
    {
        init_class();
        init_widgets();
    }

    public void init_class()
    {
        const_db = new dbConstants();
        util_db = new dbUtilities();
        util = new utilities();
        util_Toast = new utilToast();
        util_String = new utilString();
        util_Validation = new utilValidation();
        util_Hashmap = new utilHashMap();
        util_Debugger = new utilDebugger();
        util_Intents = new utilIntents();
        util_Files = new utilFiles();
        util_Security = new utilSecurity();
        acl = new AccessControlList();
        zip = new zipper();
        unzipper = new UnzipFile();
        zipDir = new ZipDirectory();
    }
    public void init_widgets()
    {

    }
     */
    public void init()
    {
        init_class();
        init_widgets();
    }

    public void init_class()
    {
        const_db = new dbConstants();
        util_db = new dbUtilities();
        util = new utilities();
        util_Toast = new utilToast();
        util_String = new utilString();
        util_Validation = new utilValidation();
        util_Hashmap = new utilHashMap();
        util_Debugger = new utilDebugger();
        util_Intents = new utilIntents();
        util_Files = new utilFiles();
        util_Security = new utilSecurity();
        acl = new AccessControlList();
        zip = new zipper();
        unzipper = new UnzipFile();
        zipDir = new ZipDirectory();
    }
    public void init_widgets()
    {
        btn_ACLTestbench_Welcome = (Button)findViewById(R.id.btn_acl_testbench_welcome);
    }

    public View init_view(View view_object, int id)
    {
        view_object = (View)findViewById(id);
        return view_object;
    }
}
