/*
Utilities class for functions with general use-cases like
    i.e.
        Toast messages
Notes:
    1. use ? if you dont know the type - variable typing
 */
package com.personal.projtestbench;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.FileUtils;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class utilities extends AppCompatActivity {
    public static class utilToast {
        public int LENGTH_LONG = Toast.LENGTH_LONG;
        public int LENGTH_SHORT = Toast.LENGTH_SHORT;

        public int get_toast_length(int toast_Length)
        {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
            */
            int default_Toast_Length = Toast.LENGTH_SHORT;
            switch (toast_Length)
            {
                case 0:
                    toast_Length = Toast.LENGTH_SHORT;
                    break;
                case 1:
                    toast_Length = Toast.LENGTH_LONG;
                    break;
                default:
                    toast_Length = default_Toast_Length;
            }
            return toast_Length;
        }

        public Toast create_message_object(Context c, String msg, int length) {
            Toast toast_obj = Toast.makeText(c, msg, length);
            return toast_obj;
        }

        public void show_message(Context c, String msg, int length) {
            Toast toast_obj = Toast.makeText(c, msg, length);
            toast_obj.show();
        }

        public void show_message(Toast toast_obj) {
            toast_obj.show();
        }
    }
    //====================================================================//
    public static class utilString
    {
        public int string_length(String msg)
        {
            return msg.length();
        }
        public String remove_substring(String full_string, String remove_text)
        {
            String substr = "";
            String new_string = "";
            int pos = full_string.indexOf(remove_text);
            if(pos >= 0) {
                substr = full_string.substring(pos);
                new_string = full_string.replace(substr, "");
            }
            else
            {
                new_string = full_string;
            }
            return new_string;
        }
    }
    //====================================================================//
    public static class utilValidation
    {
        public boolean check_empty_string(String msg)
        {
            /*
            return true if empty
            return false if not empty
             */
            boolean is_empty = false;
            if(msg.length() == 0)
            {
                is_empty = true;
            }
            return is_empty;
        }
    }
    //====================================================================//
    public static class utilHashMap
    {
        public HashMap<Object,Object> create_hashmap(Object starting_key, Object starting_val)
        {
            //Step 1: Define object of Hashmap Class
            HashMap<Object,Object> map = new HashMap<>();

            if(!(starting_key == null))
            {
                map.put(starting_key, starting_val);
            }

            return map;
        }

        public HashMap<Object,Object> append_hashmap(HashMap<Object,Object> existing_hashmap, Object new_key, Object new_value)
        {
            existing_hashmap.put(new_key, new_value);
            return existing_hashmap;
        }
        public void print_hashmap_contents(Context c, String msg_header, String seperator, HashMap<Object, Object> mapper, int Length)
        {
            //Step 3: Displaying key-value pair using for loop;
            String msg = "";
            String default_seperator=":";
            if(msg_header != null)
            {
                msg = msg_header;
                msg += "\n";
            }
            if(seperator == null)
            {
                seperator = default_seperator;
            }

            for(Map.Entry<Object,Object> map : mapper.entrySet())
            {
                //Step 4: Using getKey and getValue methods to retrieve Key and Value respectively
                Object key = map.getKey().toString();
                Object val = map.getValue().toString();

                msg += key + seperator + val;
                msg += "\n";
            }
            Toast.makeText(c, msg, Length).show();
        }
        public void print_hashmap_contents_raw(Context c, HashMap<Object, Object> mapper, int Length)
        {
            Toast.makeText(c, mapper.toString(), Length).show();
        }

        public void hashmap_example_1(Context c, int Length)
        {
            //Step 1: Initialize and create new HashMap (hmap)
            HashMap<Object,Object> hmap = create_hashmap("Test", "Test");
            //Step 2: Add more key and value to existing hashmap
            hmap = append_hashmap(hmap, "Test_2","Test_2");
            //Step 3: Add a different key or type and value to existing hashmap
            hmap = append_hashmap(hmap, 1, "Test 3");
            //Step 4: Print Hashmap
            print_hashmap_contents(c, null, null, hmap, Length);
        }

        public void hashmap_example_2_forloop(Context c, int Length)
        {
            //Step 1: Initialize and create new HashMap (hmap)
            HashMap<Object,Object> hmap = create_hashmap(null, null);
            Object key;
            Object val;
            //Step 2: Add more key and value to existing hashmap
            for(int i=0; i < 5; i++)
            {
                key = i;
                val = "Test" + " " + ((Integer)key+1);
                hmap = append_hashmap(hmap, key,val);
            }
            //Step 4: Print Hashmap
            print_hashmap_contents(c, null, null, hmap, Length);
        }
    }
    //====================================================================//
    public static class utilDebugger
    {
        public HashMap<?, ?> catch_exception(Exception ex)
        {
//            util_Toast.show_message(c,
//                    "StackTrace:" + ex.getStackTrace() + "\n\n" +
//                            "Message:" + ex.getMessage(), Toast.LENGTH_LONG);
            String ex_msg = "Stacktrace:" + " " + ex.getStackTrace() + "\n" + "Message:" + " " + ex.getMessage() + "\n";
            List<String> exception = new ArrayList<>();
            exception.add(ex.getStackTrace().toString());
            exception.add(ex.getMessage());

            /* Create a mapping of key to value (Dictionary in python) */
            //Step 1: Define object of Hashmap Class
            HashMap<String, String> exception_mapping = new HashMap<String, String>();
            //Step 2: Add Key-Value Pair
            exception_mapping.put("Class", ex.getClass().toString());
            exception_mapping.put("Stacktrace", ex.getStackTrace().toString());
            exception_mapping.put("Localized", ex.getLocalizedMessage());
            exception_mapping.put("Message", ex.getMessage());
            return exception_mapping;
        }
        public void print_Exception(Context c, Exception ex, int Length)
        {
            Toast.makeText(c, ex.getMessage(), Length).show();
        }
        public void print_Exception(Context c, HashMap<?, ?> exception_mapping, int Length)
        {
            //Step 3: Displaying key-value pair using for loop
            Object key = null;
            Object val = null;
            String msg = "";
            for(Map.Entry map : exception_mapping.entrySet())
            {
                //Step 4: Using getKey and getValue methods to retrieve Key and Value respectively
                key = map.getKey();
                val = map.getValue();

                msg += key + ":" + val;
                msg += "\n\n";
            }
            Toast.makeText(c, "Exception:" + "\n" + msg, Length).show();
        }
    }
    //====================================================================//
    public static class utilIntents
    {
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

        public void start_intent(Context c, Intent intent)
        {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
            */
            int default_Toast_Length = Toast.LENGTH_SHORT;

            try {
                c.startActivity(intent);
            }
            catch (Exception ex)
            {
                Toast.makeText(c, ex.getMessage(), default_Toast_Length).show();
            }
        }

        public void start_intent(Context c, Intent intent, int toast_Length)
        {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
            */
            int default_Toast_Length = Toast.LENGTH_SHORT;
            switch (toast_Length)
            {
                case 0:
                    toast_Length = Toast.LENGTH_SHORT;
                    break;
                case 1:
                    toast_Length = Toast.LENGTH_LONG;
                    break;
                default:
                    toast_Length = default_Toast_Length;
            }

            try {
                c.startActivity(intent);
            }
            catch (Exception ex)
            {
                Toast.makeText(c, ex.getMessage(), toast_Length).show();
            }
        }
        public void start_intent(Context c, Activity a, Intent intent, int toast_Length, boolean end_previous_activity)
        {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
            */
            int default_Toast_Length = Toast.LENGTH_SHORT;
            switch (toast_Length)
            {
                case 0:
                    toast_Length = Toast.LENGTH_SHORT;
                    break;
                case 1:
                    toast_Length = Toast.LENGTH_LONG;
                    break;
                default:
                    toast_Length = default_Toast_Length;
            }

            try {
                if(end_previous_activity)
                {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                a.startActivity(intent);
                a.finish();
            }
            catch (Exception ex)
            {
                Toast.makeText(c, ex.getMessage(), toast_Length).show();
            }
        }

        public Intent package_and_start_Intent(Context c, Activity curr_activity, Class<?> target_class, String uInput_exception)
        {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
             */
            int default_Toast_Length = Toast.LENGTH_SHORT;

            if(uInput_exception == null)
            {
                //Enter default exception message here
                uInput_exception = "Exception:";
                uInput_exception += "\n";
            }

            //Design Intent
            Intent this_Intent = create_intent(curr_activity, target_class);

            try {
                start_intent(c, this_Intent, default_Toast_Length);
            }
            catch (Exception ex)
            {
                Toast.makeText(c, ex.getMessage(), default_Toast_Length).show();
            }
            return this_Intent;
        }

        public Intent package_and_start_Intent(Context c, Activity curr_activity, Class<?> target_class, String uInput_exception, int toast_Length)
        {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
             */
            int default_Toast_Length = Toast.LENGTH_SHORT;
            boolean default_set_finish = false;

            if(uInput_exception == null)
            {
                //Enter default exception message here
                uInput_exception = "Exception:";
                uInput_exception += "\n";
            }

            switch (toast_Length)
            {
                case 0:
                    toast_Length = Toast.LENGTH_SHORT;
                    break;
                case 1:
                    toast_Length = Toast.LENGTH_LONG;
                    break;
                default:
                    toast_Length = default_Toast_Length;
            }

            //Design Intent
            Intent this_Intent = create_intent(curr_activity, target_class);

            try {
                start_intent(c, this_Intent, toast_Length);
            }
            catch (Exception ex)
            {
                Toast.makeText(c, ex.getMessage(), toast_Length).show();
            }
            return this_Intent;
        }
        public Intent package_and_start_Intent(Context c, Activity curr_activity, Class<?> target_class, String uInput_exception, int toast_Length, boolean end_previous_activity)
        {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
             */
            int default_Toast_Length = Toast.LENGTH_SHORT;
            boolean default_set_finish = false;

            if(uInput_exception == null)
            {
                //Enter default exception message here
                uInput_exception = "Exception:";
                uInput_exception += "\n";
            }

            switch (toast_Length)
            {
                case 0:
                    toast_Length = Toast.LENGTH_SHORT;
                    break;
                case 1:
                    toast_Length = Toast.LENGTH_LONG;
                    break;
                default:
                    toast_Length = default_Toast_Length;
            }

            //Design Intent
            Intent this_Intent = create_intent(curr_activity, target_class);

            try {
                start_intent(c, curr_activity, this_Intent, toast_Length, end_previous_activity);
            }
            catch (Exception ex)
            {
                Toast.makeText(c, ex.getMessage(), toast_Length).show();
            }
            return this_Intent;
        }
    }
    //====================================================================//
    public static class utilFiles {
        public boolean create_folder(String dir, String folder_name)
        {
            // check if folder exists
            File root = new File(dir, folder_name);
            // if external memory exists and folder with name Notes
            if (!root.exists()) {
                root.mkdirs(); // this will create folder.
            }

            //Check if successfully created
            boolean created = false;
            if(root.exists())
            {
                created = true;
            }
            return created;
        }
        public boolean create_folder(String dir)
        {
            // check if folder exists
            File root = new File(dir);
            // if external memory exists and folder with name Notes
            if (!root.exists()) {
                root.mkdirs(); // this will create folder.
            }

            //Check if successfully created
            boolean created = false;
            if(root.exists())
            {
                created = true;
            }
            return created;
        }

        public boolean check_folder_exists(String dir, String folder_name)
        {
            boolean exists = false;

            // check if folder exists
            File root = new File(dir, folder_name);
            // if external memory exists and folder with name Notes
            if (root.exists()) {
                exists = true;
            }
            return exists;
        }
        public boolean check_folder_exists(String dir)
        {
            boolean exists = false;

            // check if folder exists
            File root = new File(dir);
            // if external memory exists and folder with name Notes
            if (root.exists()) {
                exists = true;
            }
            return exists;
        }

        public void create_and_write_file(Context c, String msg, String output_folder_name, String output_path_name)
        {
            /*
            params:
                Context c  : Context for Toast and related
                String msg : Message to write into file
                String output_folder_name : To check if folder exists
                String output_path_name : To check if the full file path - path to file + file name itself - to create file
             */
            if(msg.length() == 0) /* if message is empty, convert message to "No Message"*/
            {
                msg = "No Message";
            }
            String h;

            try {
                h = DateFormat.format("MM-dd-yyyyy-h-mmssaa", System.currentTimeMillis()).toString();
                // this will create a new name everytime and unique
                File root = new File(output_folder_name);
                //File root = new File(Environment.getExternalStorageDirectory(), output_folder_name);
                // if external memory exists and folder with name Notes
                if (!root.exists()) {
                    root.mkdirs(); // this will create folder.
                }

                //Contents
                File filepath = new File(output_path_name);  // file path to save
                FileWriter writer = new FileWriter(filepath);
                writer.append(msg);
                writer.flush();
                writer.close();
                String m = "File generated with name " + h + ".txt";
                Toast.makeText(c, m, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        public void create_new_file(Context c, String out_path, int toast_Length) {
            /*
            0 for Toast.LENGTH_SHORT
            1 for Toast.LENGTH_LONG
            */
            int default_Toast_Length = Toast.LENGTH_SHORT;
            switch (toast_Length) {
                case 0:
                    toast_Length = Toast.LENGTH_SHORT;
                    break;
                case 1:
                    toast_Length = Toast.LENGTH_LONG;
                    break;
                default:
                    toast_Length = default_Toast_Length;
            }

            Toast.makeText(c, "Out Path:\n" + out_path, toast_Length).show();

            //Create File Object, prepare
            File file = new File(out_path);
            //Create File
            try {
                file.createNewFile();
            } catch (IOException e) {
                Toast.makeText(c, e.getMessage(), toast_Length).show();
            } catch (Exception ex) {
                Toast.makeText(c, ex.getMessage(), toast_Length).show();
            }

            //Output File
            try {
                FileOutputStream fileos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                Toast.makeText(c, e.getMessage(), toast_Length).show();
            } catch (Exception ex) {
                Toast.makeText(c, ex.getMessage(), toast_Length).show();
            }
        }

        public boolean check_copy_success(Context c, String file_name, String dst_Path)
        {
            boolean copy_success = false;
            File target_File = new File(file_name);
            File target_Path = new File(dst_Path);
            copy_success = target_Path.exists();
            return copy_success;
        }

        public void copy_File(Context c, String src, String dst)
        {
            //if folder does not exist
            Path src_path = Paths.get(src);
            Path out_path = Paths.get(dst);
            try {
                Files.copy(src_path, out_path);
            } catch (Exception ioe)
            {
                Toast.makeText(c, ioe.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        public List<List<String>> get_entire_Directory(Context c, String src, String dst, List<File> files, List<File> dir, List<String> file_Paths, List<String> dir_Paths)
        {
            //if folder does not exist
            Path src_path = Paths.get(src);
            File src_Dir = new File(src);
            Path out_path = Paths.get(dst);

            File[] all_Files = src_Dir.listFiles();
            if(all_Files != null)
            {
                for (File file : all_Files) {
                    copy_File(c,src_path.toString(), dst);
                    if (file.isFile()) {
                        files.add(file);
                        file_Paths.add(file.getAbsolutePath());
                    } else if (file.isDirectory()) {
                        dir.add(file);
                        dir_Paths.add(file.getAbsolutePath());
                    }
                    get_entire_Directory(c, file.getAbsolutePath(), dst, files, dir, file_Paths, dir_Paths);
                }
            }

            List<List<String>> ret_list = new ArrayList<>();
            ret_list.add(file_Paths);
            ret_list.add(dir_Paths);
            return ret_list;

//            try {
//                FileUtils.copyDirectory(src_path, out_path);
//            } catch (Exception ioe)
//            {
//                Toast.makeText(c, ioe.getMessage(), Toast.LENGTH_LONG).show();
//            }
        }

        public List<List<String>> get_entire_Directory(Context c, String src, String dst)
        {
            //if folder does not exist
            Path src_path = Paths.get(src);
            File src_Dir = new File(src);
            Path out_path = Paths.get(dst);

            File[] all_Files = src_Dir.listFiles();
            List<File> files = new ArrayList<>();
            List<File> dir = new ArrayList<>();
            List<String> file_Paths = new ArrayList<>();
            List<String> dir_Paths = new ArrayList<>();
            List<List<String>> ret_List = new ArrayList<>();
            ret_List = get_entire_Directory(c, src, dst, files, dir, file_Paths, dir_Paths);
            return ret_List;
        }

        /* ============== Override ==============================================================*/
        public void print_all_files(Context c, String src, String dst, int toast_Length, String additional_messages)
        {
            //if folder does not exist
            Path src_path = Paths.get(src);
            File src_Dir = new File(src);
            Path out_path = Paths.get(dst);

            File[] all_Files = src_Dir.listFiles();
            List<File> files = new ArrayList<>();
            List<File> dir = new ArrayList<>();
            List<String> file_Paths = new ArrayList<>();
            List<String> dir_Paths = new ArrayList<>();
            List<List<String>> ret_List = new ArrayList<>();
            ret_List = get_entire_Directory(c, src, dst, files, dir, file_Paths, dir_Paths);
            int ret_list_size = ret_List.size();
            List<String> files_list = ret_List.get(0);
            int files_list_size = files_list.size();
            List<String> dir_list = ret_List.get(1);
            int dir_list_size = dir_list.size();

            String msg = "";
            msg = "Files:" + "\n";
            for (int i = 0; i < files_list_size; i++) {
                msg += files_list.get(i) + "\n";
            }

            msg += "Directories:" + "\n";
            for(int j=0; j < dir_list_size; j++) {
                msg += dir_list.get(j) + "\n";
            }

            msg += additional_messages;
            Toast.makeText(c, msg, toast_Length).show();
        }

        public void print_all_files(Context c, String src, String dst, String additional_messages)
        {
            //if folder does not exist
            Path src_path = Paths.get(src);
            File src_Dir = new File(src);
            Path out_path = Paths.get(dst);

            File[] all_Files = src_Dir.listFiles();
            List<File> files = new ArrayList<>();
            List<File> dir = new ArrayList<>();
            List<String> file_Paths = new ArrayList<>();
            List<String> dir_Paths = new ArrayList<>();
            List<List<String>> ret_List = new ArrayList<>();
            ret_List = get_entire_Directory(c, src, dst, files, dir, file_Paths, dir_Paths);
            int ret_list_size = ret_List.size();
            List<String> files_list = ret_List.get(0);
            int files_list_size = files_list.size();
            List<String> dir_list = ret_List.get(1);
            int dir_list_size = dir_list.size();

            String msg = "";
            msg = "Files:" + "\n";
            for (int i = 0; i < files_list_size; i++) {
                msg += files_list.get(i) + "\n";
            }

            msg += "Directories:" + "\n";
            for(int j=0; j < dir_list_size; j++) {
                msg += dir_list.get(j) + "\n";
            }
            msg += additional_messages;
            Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
        }

        public void print_all_files(Context c, String src, String dst)
        {
            //if folder does not exist
            Path src_path = Paths.get(src);
            File src_Dir = new File(src);
            Path out_path = Paths.get(dst);

            File[] all_Files = src_Dir.listFiles();
            List<File> files = new ArrayList<>();
            List<File> dir = new ArrayList<>();
            List<String> file_Paths = new ArrayList<>();
            List<String> dir_Paths = new ArrayList<>();
            List<List<String>> ret_List = new ArrayList<>();
            ret_List = get_entire_Directory(c, src, dst, files, dir, file_Paths, dir_Paths);
            int ret_list_size = ret_List.size();
            List<String> files_list = ret_List.get(0);
            int files_list_size = files_list.size();
            List<String> dir_list = ret_List.get(1);
            int dir_list_size = dir_list.size();

            String msg = "";
            msg = "Files:" + "\n";
            for (int i = 0; i < files_list_size; i++) {
                msg += files_list.get(i) + "\n";
            }

            msg += "Directories:" + "\n";
            for(int j=0; j < dir_list_size; j++) {
                msg += dir_list.get(j) + "\n";
            }
            Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
        }
        /* =======================================================================================*/

        public boolean delete_File(Context c, String target_File_path)
        {
            File filepath = new File(target_File_path);
            boolean deleted = false;
            try
            {
                deleted = filepath.delete();
            }
            catch (Exception ex)
            {
                Toast.makeText(c, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            return deleted;
        }

        public int get_position_of_file_extension_dot(String file_name)
        {
            int lastDot = file_name.lastIndexOf('.'); //Index containing the dot for the extension
            return lastDot;
        }

        public String get_filename_without_path_and_extension(String file_name)
        {
            String delimiter = File.separator;
            String filename_without_extension = get_filename_without_extension(file_name);
            int pos_last_backslash = filename_without_extension.lastIndexOf(delimiter);
            String fn_no_path_no_extension = filename_without_extension.substring(filename_without_extension.lastIndexOf(delimiter)+1);;
            return fn_no_path_no_extension;
        }

        public String get_file_extension(String file_name)
        {
            String delimiter_dot = ".";
            int lastDot = file_name.lastIndexOf(delimiter_dot); //Index containing the dot for the extension
            String ext = file_name.substring(lastDot); //Extension of file
            return ext;
        }

        public String get_filename_without_extension(String file_name)
        {
            String delimiter_dot = ".";
            int lastDot = file_name.lastIndexOf(delimiter_dot); //Index containing the dot for the extension
            String filename_without_extension = file_name.substring(0,lastDot); //File name WITHOUT extension
            return filename_without_extension;
        }
    }
}
