package com.personal.projtestbench.database;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.personal.projtestbench.database.dbConstants;

public class dbUtilities extends AppCompatActivity {
    dbConstants db_constants;

    public void init()
    {
        db_constants = new dbConstants();
    }

    public void export_database(String db_File_Path, String output_File_Path)
    {
        init();

        File dbFile = new File(db_File_Path);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try
        {
            /* Input File path and name */
            fis = new FileInputStream(dbFile);
            /* Output File path and name */
            fos = new FileOutputStream(output_File_Path);
            while(true)
            {
                int i = fis.read();
                if(i != -1)
                {
                    fos.write(i);
                }
                else
                {
                    break;
                }
            }
            fos.flush();
            Toast.makeText(getApplicationContext(), "DB Dump OK", Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), String.format("DB Dump ERROR:\n [%s]", ex.getMessage()), Toast.LENGTH_LONG).show();
        }
        finally {
            try
            {
                fos.close(); //Important: Close File Output Stream
                fis.close(); //Important: Close File Input Stream
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
                Toast.makeText(getApplicationContext(), String.format("DB Dump ERROR:\n [%s]", ioe.getMessage()), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void export_Database(Context c, String dbFullName, String dst)
    {
        String src = c.getDatabasePath(dbFullName).toString();

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

    public void import_Database(Context c, String dbFullName, String src)
    {
        String dst = c.getDatabasePath(dbFullName).toString();

        Path src_path = Paths.get(src);
        Path out_path = Paths.get(dst);
        try {
            Files.copy(src_path, out_path);
        } catch (Exception ioe)
        {
            Toast.makeText(c, ioe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void delete_Database(Context c, String dbFullName)
    {
        String db_Path = c.getDatabasePath(dbFullName).toString();
        File db = new File(db_Path);
        try
        {
            boolean deleted = db.delete();
        }
        catch (Exception ex)
        {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
