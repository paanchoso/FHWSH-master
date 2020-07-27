package de.fhws.international.fhwsh.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.fhws.international.fhwsh.models.Post;

public class AdminDao {
    // static variable single_instance of type Singleton
    private static AdminDao single_instance = null;
    public static boolean currentUserIsAdmin = false;

    // variable of type String
    public Set<String> db;

    // private constructor restricted to this class itself
    @RequiresApi(api = Build.VERSION_CODES.O)
    private AdminDao() {
        db = new HashSet<>();
        db.add("grp_fiw_fiwis_teststaff");
    }

    // static method to create instance of Singleton class
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AdminDao getInstance() {
        if (single_instance == null)
            single_instance = new AdminDao();

        return single_instance;
    }
}

