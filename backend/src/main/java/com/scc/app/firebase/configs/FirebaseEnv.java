package com.scc.app.firebase.configs;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public abstract class FirebaseEnv {
	private final String collectionName;

	protected FirebaseEnv(String collectionName) {
		this.collectionName = collectionName;
	}

	protected CollectionReference getCollection() {
		return FirestoreClient.getFirestore().collection(collectionName);
	}
}
