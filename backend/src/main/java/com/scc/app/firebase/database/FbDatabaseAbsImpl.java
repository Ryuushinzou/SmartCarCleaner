package com.scc.app.firebase.database;

import com.scc.app.firebase.configs.FirebaseEnv;

abstract class FbDatabaseAbsImpl<Entity> extends FirebaseEnv implements FbDatabase<Entity> {
	protected FbDatabaseAbsImpl(String collectionName) {
		super(collectionName);
	}
}
