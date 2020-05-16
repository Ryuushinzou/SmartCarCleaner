package com.scc.app.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitialize {

	private static final String TAG = FirebaseInitialize.class.getSimpleName();

	@Value("${firebase.databaseUrl}")
	private String firebaseDatabaseUrl;

	@Value("${firebase.privateKeyPath}")
	private String firebasePrivateKeyPath;

	public void init() throws IOException {
		FileInputStream serviceAccount =
				new FileInputStream(firebasePrivateKeyPath);

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl(firebaseDatabaseUrl)
				.build();

		FirebaseApp.initializeApp(options);
	}
}
