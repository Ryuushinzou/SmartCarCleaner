package com.scc.app.config;


import com.scc.app.SmartCarCleanerApplication;
import com.scc.app.firebase.FirebaseInitialize;
import com.scc.app.utils.Constants;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
public class DatabaseInitializer {

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void initDatabase() {

        if (Utils.isFirebaseDatabase()) {

            try {
                context.getBean(FirebaseInitialize.class).init();
            } catch (Exception e) {
                System.out.println("Could not initialize firebase database. Error: " + e.getMessage());
            }
        }
    }
}
