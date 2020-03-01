package com.personal.projtestbench;

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

public class MainActivity extends AppCompatActivity {

    //Classes
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

    /* Widgets */
    TextView tvCommentBox;
    EditText etCommentBox;
    Button btnSubmitComment;
    Button btnDebug_RetrieveDatabasePath;
    Button btnDebug_RetrievePublicPathRoot;
    //Button btnDebug_CreateSaveReadWriteCopyCut_File;
    Button btnDebug_BackupProjectFolder;
    Button btnDebug_CreateDB_File;
    Button btnDebug_ExportDB_File;
    Button btnDebug_DeleteDB_File;
    Button btnDebug_ImportDB_File;
    Button btnDebug_Write_File;
    Button btnChangeActivity_Debug_Menu;
    Button btnChangeActivity_Debug_ACL_Menu;


    /* Constants */
    String public_external_directory_root = Environment.getExternalStorageDirectory().getAbsolutePath();
    String db_Path;
    String db_Path_Alt;
    String db_Path_Alt_with_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize
        init();

        //Set Constants
        String dataDir = getDataDir().toString();
        final String packageName = getPackageName();
        db_Path = getDatabasePath(const_db.dbFullName).toString();
        db_Path_Alt = dataDir + "//" + "databases" + "//";
        db_Path_Alt_with_Name = db_Path_Alt + const_db.dbFullName;

        btnSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etCommentBox.getText().toString();
                if(util_Validation.check_empty_string(msg)) /* if message is empty, convert message to "No Message"*/
                {
                    msg = "No Message";
                }
                Toast tmp_toast = util_Toast.create_message_object(c, String.format("Submitted Message : [%s]", msg), Toast.LENGTH_LONG);
                util_Toast.show_message(tmp_toast);
            }
        });

        btnDebug_RetrieveDatabasePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = "Database Path Variable 1 : " + "[" + db_Path + "]" + "\n" +
                        "Database Path Variable 2 : " + "[" + db_Path_Alt_with_Name + "]";
                boolean similarity_token = false;
                if(db_Path.equals(db_Path_Alt_with_Name))
                {
                    similarity_token = true;
                }

                contents += "\n";
                contents += "Similarity : " + "[" + similarity_token + "]";

                Toast tmp_toast = util_Toast.create_message_object(c, contents, Toast.LENGTH_LONG);
                util_Toast.show_message(tmp_toast);
            }
        });

        btnDebug_RetrievePublicPathRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast tmp_toast = util_Toast.create_message_object(c, "Public Directory (Root) : " + "[" + public_external_directory_root + "]" , Toast.LENGTH_LONG);
                util_Toast.show_message(tmp_toast);
            }
        });

        btnDebug_BackupProjectFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String root_Path = Environment.getExternalStorageDirectory() + "/";
                String backup_file_zip = "backup_DEBUG";
                String output_file_path = Environment.getExternalStorageDirectory() + "/" + packageName + "/";
                String backup_file_path = Environment.getExternalStorageDirectory() + "/" + packageName + "_" + "backup" + "/";
                String output_file_name = const_db.dbFullName;
                String output_path_name = "";
                boolean project_folder_exists = util_Files.check_folder_exists(output_file_path);
                boolean backup_folder_exists = false;
                boolean folder_created = false;
                boolean copied = false;
                List<String> error_paths = new ArrayList<>();
                String error_msg = "Errors" + "\n";

                Date date = new Date();
                String date_Format = "yyyy-MM-dd";
                String time_Format = "HH-mm-ss";
                SimpleDateFormat sdf_date = new SimpleDateFormat(date_Format);
                SimpleDateFormat sdf_time = new SimpleDateFormat(time_Format);
                String current_Time = sdf_time.format(date);
                String current_Date = sdf_date.format(date);

                // Check if main project folder exists
                if(project_folder_exists)
                {
                    backup_folder_exists = util_Files.check_folder_exists(backup_file_path);
                    if(!backup_folder_exists)
                    {
                        folder_created = util_Files.create_folder(backup_file_path);
                        if(folder_created)
                        {
                            //Success
                            util_Toast.show_message(c, String.format("Backup Folder created in:\n %s", backup_file_path), 1);
                        }
                    }

                    // Get latest date and time - prepare output file name
                    output_file_name = backup_file_zip + "_" + current_Date + "_" + current_Time + ".zip";

                    // Append latest date and time file name to full path for use
                    output_path_name = backup_file_path + output_file_name;

                    //Zip directory
                    try {
                        zipDir.zipDir(output_file_path, output_path_name);
                    } catch (Exception e) {
                        util_Toast.show_message(c, "Error:" + "\n" + e.getMessage(), 1);
                    }

                    boolean zip_folder_exists = util_Files.check_folder_exists(backup_file_path,output_file_name);
                    if(zip_folder_exists)
                    {
                        util_Toast.show_message(c, "Zipping completed", 1);
                    }
                    else
                    {
                        util_Toast.show_message(c, "Zipping error", 1);
                    }


                    //Copy Directory to backup folder


//                    //Copy Directory to backup folder
//                    util_Files.copy_File(c, output_file_path, backup_file_path);
//                    copied = util_Files.check_copy_success(c, output_file_name, backup_file_path);
//
//                    if(copied) {
//                        util_Toast.show_message(c, String.format("File %s copied successfully.", output_file_name), 1);
//
//                        List<List<String>> ret_list = util_Files.get_entire_Directory(c, output_file_path, backup_file_path);
//                        int ret_list_size = ret_list.size();
//                        List<String> files_list = ret_list.get(0);
//                        int files_list_size = files_list.size();
//                        List<String> dir_list = ret_list.get(1);
//                        int dir_list_size = dir_list.size();
//                        String msg = "";
//
//                        String current_dir_path = "";
//                        String current_file_path = "";
//                        String current_file_name = "";
//
//                        String all_paths = "";
//
//                        all_paths += "Directories:" + "\n";
//                        error_msg += all_paths;
//                        // Copy all directories and subdirectories
//                        for(int i=0; i < dir_list_size; i++)
//                        {
//                            current_dir_path = dir_list.get(i);
//                            all_paths += current_dir_path + "\n";
//                            util_Files.copy_File(c, current_dir_path, backup_file_path);
//                            copied = util_Files.check_copy_success(c, current_dir_path, backup_file_path);
//                            if(!copied)
//                            {
//                                error_paths.add(current_dir_path);
//                            }
//                            else
//                            {
//                                error_msg += String.format("Path [%s] : %s", Integer.toString(i), current_dir_path);
//                            }
//                        }
//
//                        all_paths += "Files:" + "\n";
//                        error_msg += "Files:" + "\n";
//                        // Copy all subfiles after directories
//                        for(int j=0; j < files_list_size; j++)
//                        {
//                            current_file_path = files_list.get(j);
//                            current_file_name = util_Files.get_filename_without_path_and_extension(current_file_path) + util_Files.get_file_extension(current_file_path);
//                            all_paths += current_file_name + "\n";
//                            util_Files.copy_File(c, current_file_path, backup_file_path);
//                            copied = util_Files.check_copy_success(c, current_file_name, backup_file_path);
//                            if(!copied)
//                            {
//                                error_paths.add(current_file_path);
//                            }
//                            else
//                            {
//                                error_msg += String.format("Path [%s] : %s", Integer.toString(j), current_file_path);
//                            }
//                        }
//
//                        // Check for errors
//                        int error_set_size = error_paths.size();
//                        if(error_set_size > 0) {
//                            String errors = "";
//
//                            for (int k = 0; k < error_set_size; k++) {
//                                //If there are errors
//                                errors += error_paths.get(k) + "\n";
//                            }
//                            util_Toast.show_message(c, "Errors:" + "\n" + error_set_size, 1);
//                        }
//                        else
//                        {
//                            util_Toast.show_message(c, "No Errors - Backup complete.", 1);
//                        }
//                    }
                }
            }
        });

        btnDebug_CreateDB_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output_db_path = db_Path;
                create_db(c, output_db_path, 1);
            }
        });

        btnDebug_ExportDB_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output_folder_name = "Database";
                String output_file_path = Environment.getExternalStorageDirectory() + "/" + packageName + "/" + output_folder_name + "/";
                String output_file_name = const_db.dbFullName;

                String documentation_name = "readme.md";
                String documentation_path = output_file_path + documentation_name;

                String msg = String.format(
                        "Notes:" + "\n" +
                                "Please place your desired database file in this folder and rename it as [%s] \n" +
                                "Thank you!",
                        output_file_name
                );

                boolean exists = util_Files.check_folder_exists(documentation_path);
                if(!exists) {
                    util_Files.create_and_write_file(c, msg, output_file_path, documentation_path);
                }

                boolean res = export_db(output_file_path, output_file_name);
                //util_Toast.show_message(c, Boolean.toString(res), 1);
            }
        });

        btnDebug_DeleteDB_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String db_file_name = const_db.dbFullName;
                //delete_db(c, db_file_name);
                boolean res = delete_db(c, db_file_name);

                util_Toast.show_message(c, Boolean.toString(res), 1);
            }
        });

        btnDebug_ImportDB_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output_folder_name = "Database";
                String output_file_path = Environment.getExternalStorageDirectory() + "/" + packageName + "/" + output_folder_name + "/";
                String output_file_name = const_db.dbFullName;


                //import_db(output_file_path, output_file_name);
                boolean res = import_db(output_file_path, output_file_name);

                //Intent refresh page
                if (res) {
                    Toast.makeText(c, "Import successful.\nRestarting app after importing database...", Toast.LENGTH_LONG).show();
                    util_Intents.package_and_start_Intent(c, MainActivity.this, MainActivity.class, null, 1, true);
                }
                else
                {
                    util_Toast.show_message(c, "Import stopped.", 1);
                }
            }
        });

        btnDebug_Write_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etCommentBox.getText().toString();

//                if(util_Validation.check_empty_string(msg)) /* if message is empty, convert message to "No Message"*/
//                {
//                    msg = "No Message";
//                }
//                String h;
//
//                try {
//                    h = DateFormat.format("MM-dd-yyyyy-h-mmssaa", System.currentTimeMillis()).toString();
//                    // this will create a new name everytime and unique
//                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
//                    // if external memory exists and folder with name Notes
//                    if (!root.exists()) {
//                        root.mkdirs(); // this will create folder.
//                    }
//                    File filepath = new File(root, h + ".txt");  // file path to save
//                    FileWriter writer = new FileWriter(filepath);
//                    writer.append(msg);
//                    writer.flush();
//                    writer.close();
//                    String m = "File generated with name " + h + ".txt";
//                    Toast.makeText(c, m, Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
//                }

                //Create file in public external directory
                String h = DateFormat.format("MM-dd-yyyyy-h-mmssaa", System.currentTimeMillis()).toString();
                String output_folder_name = "Notes";
                String output_file_path = Environment.getExternalStorageDirectory() + "/" + output_folder_name + "/";
                String output_file_name = h + ".txt";
                String output_path_name = output_file_path + output_file_name;
                util_Files.create_and_write_file(c, msg, output_folder_name, output_path_name);

                //Copy from public external directory to internal database directory
                try {
                    util_Files.copy_File(c, output_path_name, db_Path_Alt + "/" + output_file_name);
                } catch (Exception ex)
                {
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                //Delete from public external directory
                try
                {
                    util_Files.delete_File(c, Environment.getExternalStorageDirectory() + "/" + output_folder_name + "/" + output_file_name);
                }
                catch (Exception ex)
                {
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                //Copy from internal database directory to public external directory
                try {
                    util_Files.copy_File(c, db_Path_Alt + "/" + output_file_name, Environment.getExternalStorageDirectory() + "/" + output_folder_name + "/" + output_file_name);
                    Toast.makeText(c,
                            "[" + output_file_name + "]" + " " + "copied" + " " + "\n" +
                                    "from\n" + " " + "[" + db_Path_Alt + "/" + "]" + " " + "\n" +
                                    "to\n" + " " +   "[" + Environment.getExternalStorageDirectory() + "/" + output_folder_name + "/" +
                                    "]",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex)
                {
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                //Delete from internal database directory
                try
                {
                    util_Files.delete_File(c, db_Path_Alt + "/" + output_file_name);
                    Toast.makeText(c,
                            "[" + output_file_name + "]" + " " + "Deleted" + "\n" + "from" + " " + "[" + db_Path_Alt + "]",
                            Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnChangeActivity_Debug_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize Variables
                Intent intent_ChangeActivity_GotoDebugRoom = null;
                MainActivity curr_Activity = MainActivity.this;
                Class<DebugMenu> next_Activity = DebugMenu.class;

                /*
                //1. Design Intent
                intent_ChangeActivity_GotoDebugRoom = util_Intents.create_intent(intent_ChangeActivity_GotoDebugRoom, curr_Activity, next_Activity);

                //2. Try to start activity
                try {
                    util_Intents.start_intent(c, intent_ChangeActivity_GotoDebugRoom);
                }
                catch (Exception ex)
                {
                    HashMap<?, ?> ex_map = util_Debugger.catch_exception(ex);
                    util_Debugger.print_Exception(c, ex_map, Toast.LENGTH_LONG);
                }
                */
                util_Intents.package_and_start_Intent(c, curr_Activity, next_Activity, null);
            }
        });

        btnChangeActivity_Debug_ACL_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

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
        tvCommentBox = (TextView)findViewById(R.id.tv_Comments);
        etCommentBox = (EditText)findViewById(R.id.et_Comments);
        btnSubmitComment = (Button)findViewById(R.id.btn_submit_comments);
        btnDebug_RetrieveDatabasePath = (Button)findViewById(R.id.btn_retrieve_database_path);
        btnDebug_RetrievePublicPathRoot = (Button)findViewById(R.id.btn_retrieve_public_directory_path_root);
        btnDebug_BackupProjectFolder = (Button)findViewById(R.id.btn_backup_project_folder);
        btnDebug_CreateDB_File = (Button)findViewById(R.id.btn_create_db);
        btnDebug_ExportDB_File = (Button)findViewById(R.id.btn_export_db);
        btnDebug_DeleteDB_File = (Button)findViewById(R.id.btn_delete_db);
        btnDebug_ImportDB_File = (Button)findViewById(R.id.btn_import_db);
        btnDebug_Write_File = (Button)findViewById(R.id.btn_write_file);
        btnChangeActivity_Debug_Menu = (Button)findViewById(R.id.btn_goto_debug_menu);
        btnChangeActivity_Debug_ACL_Menu = (Button)findViewById(R.id.btn_goto_access_control_list_menu);
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

    public void create_db(Context c, String output_file_path, int toast_Length)
    {
        //Create File
        try {
//            util_Files.create_new_file(c, db_Path, 1);
            util_Files.create_new_file(c, output_file_path, toast_Length);
        }catch (Exception ex)
        {
            Toast.makeText(c, "Exception for [Creating Database]: \n" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean export_db(String output_file_path, String output_file_name)
    {
        String output_path_name = output_file_path + output_file_name;
        //int lastDot = output_path_name.lastIndexOf('.'); //Index containing the dot for the extension
        //String s = output_path_name.substring(0,lastDot); //File name WITHOUT extension
        //String ext = output_path_name.substring(lastDot); //Extension of file
        Date date = new Date();
//        String date_Format = "yyyy.MM.dd";
//        String time_Format = "HH:mm:ss";
        String date_Format = "yyyy-MM-dd";
        String time_Format = "HH-mm-ss";
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        SimpleDateFormat sdf_date = new SimpleDateFormat(date_Format);
        SimpleDateFormat sdf_time = new SimpleDateFormat(time_Format);
        String current_Time = sdf_time.format(date);
        String current_Date = sdf_date.format(date);

        String filename_without_extension = util_Files.get_filename_without_extension(output_path_name);
        String filename_without_counter = "";
        String concat = "1"; //Text to concat to existing file
        int concat_pos = 1;
        int counter = 0;
        String ext = util_Files.get_file_extension(output_path_name);
        String out_msg = "";
        boolean export_success = false;

        //Check if folder exists
        boolean exists = util_Files.check_folder_exists(output_file_path);
        if(!exists) {
            boolean created = util_Files.create_folder(output_file_path);
            if (created) {
                Toast.makeText(c, "Folder" + " " + "[" + output_file_path + "]" + " " + "successfully created.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Folder" + " " + "[" + output_file_path + "]" + " " + "failed to created.", Toast.LENGTH_LONG).show();
            }
        }

        //Copy File out to another folder (Export)
        try {
            //Check if folder exists
            exists = util_Files.check_folder_exists(output_path_name);
            if(exists) {
                output_path_name += "_" + current_Date + "_" + current_Time + ext;
//                output_path_name = filename_without_extension + "_" + concat + ext;
//                //Check if concat file exists
//                exists = util_Files.check_folder_exists(output_path_name);
//
//                if(exists)
//                {
//                    filename_without_extension = util_Files.get_filename_without_extension(output_path_name);
////                    concat =
////                    output_path_name = filename_without_extension + "_" + concat + ext;
//                    concat_pos = filename_without_extension.indexOf("_");
//                    filename_without_counter = util_Files.remove_substring(filename_without_extension, "_");
//
//                    //If filename without counter is NOT same as filename_without_extension
//                    if(!(filename_without_counter.equals(filename_without_extension))) {
//                        concat_pos += 1;
//                        concat = filename_without_extension.substring(concat_pos);
//                        counter = Integer.parseInt(concat);
//                        counter += 1;
//                    }
//                    output_path_name = filename_without_counter + "_" + counter + ext;
//
//                    //util_Toast.show_message(c, output_path_name,1);
//                }
            }
            // Export Database
            //util_db.export_Database(c, const_db.dbFullName, public_external_directory_root + "/" + const_db.dbFullName);
            util_db.export_Database(c, output_file_name, output_path_name);
            //util_Files.create_new_file(c, public_external_directory_root + "/" + const_db.dbFullName, 1);
            //util_Files.copy_File(c, db_Path_Alt, public_external_directory_root + "/" + const_db.dbFullName);

            // check if file exists
            File file = new File(output_path_name);
            // if external memory exists
            if (file.exists()) {
                out_msg = String.format("Successfully Exported [%s] to [%s]",output_file_name, output_file_path);
                export_success = true;
            }
            else
            {
                out_msg = String.format("Exporting of [%s] to [%s] Failed",output_file_name, output_file_path);
            }
            Toast.makeText(c, out_msg, Toast.LENGTH_LONG).show();

            util_Toast.show_message(c, output_path_name, 1);
        } catch (Exception ex) {
            Toast.makeText(c, "Exception for [Export Database]: \n" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return export_success;
    }

    public boolean import_db(String output_file_path, String output_file_name)
    {
        boolean imported = false;
        String dbDir = c.getDatabasePath(output_file_name).toString();
        String output_path_name = output_file_path + output_file_name;
        //Copy File into database folder from outside folder (Import)
        //Check if folder exists
        boolean exists = util_Files.check_folder_exists(dbDir);
        try {
            if(!exists) {
                util_db.import_Database(c, output_file_name, output_path_name);
                //util_Files.create_new_file(c, public_external_directory_root + "/" + const_db.dbFullName, 1);
                //util_Files.copy_File(c, db_Path_Alt, public_external_directory_root + "/" + const_db.dbFullName);

                //Check if folder exists
                exists = util_Files.check_folder_exists(dbDir);
                if(exists) {
                    imported = true;
                }
            }
            else
            {
                util_Toast.show_message(c, String.format("File [%s] already exists..., Replacing with new file", output_file_name), 1);
            }
        }catch (Exception ex)
        {
            Toast.makeText(c, "Exception for [Export Database]: \n" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return imported;
    }

    public boolean delete_db(Context c, String db_file_name)
    {
        boolean deleted = false;
        String dbDir = c.getDatabasePath(db_file_name).toString();

        //Delete File from database folder
        util_db.delete_Database(c, db_file_name);

        //Check if folder exists
        boolean exists = util_Files.check_folder_exists(dbDir);
        if(!exists) {
            deleted = true;
        }
        return deleted;
    }

//    public static void verifyStoragePermissions(Activity activity) {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    activity,
//                    PackageManager.PERMISSIOSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            );
//        }
//    }
}
