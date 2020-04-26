package com.scc.app.firebase.database;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.scc.app.model.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FbUsersDatabase extends FbDatabaseAbsImpl<User> {
	private static final String TAG = FbUsersDatabase.class.getSimpleName();

	protected FbUsersDatabase() {
		super("users");
	}

	@Override
	public User getById(@NonNull String id) throws ExecutionException, InterruptedException {
		DocumentReference document = getCollection().document(id);
		ApiFuture<DocumentSnapshot> future = document.get();
		DocumentSnapshot snapshot = future.get();

		User user = null;
		if (snapshot.exists()) {
			user = snapshot.toObject(User.class);
		}

		return user;
	}

	@Override
	public User create(@NonNull User newEntry) throws ExecutionException, InterruptedException {
		DocumentReference document = getCollection().document();
		ApiFuture<WriteResult> future = document.set(newEntry);

		WriteResult result = future.get();
		Timestamp creationTimestamp = result.getUpdateTime();

		System.out.println(TAG + " - Created user with creationTimestamp: " + creationTimestamp.toString());

		//  TODO: return new entry OR update newEntry with generated id and creationTimestamp
		return newEntry;
	}

	@Override
	public User update(@NonNull User entry) throws ExecutionException, InterruptedException {
		ApiFuture<WriteResult> future = getCollection().document(String.valueOf(entry.getId())).set(entry);

		WriteResult result = future.get();
		Timestamp lastUpdateTimestamp = result.getUpdateTime();

		System.out.println(TAG + " - Updated user with lastUpdateTimestamp: " + lastUpdateTimestamp.toString());

		//  TODO: return updated entry OR update entry with lastUpdateTimestamp
		return entry;
	}

	@Override
	public boolean delete(@NonNull User entry) throws ExecutionException, InterruptedException {

		return delete(String.valueOf(entry.getId()));
	}

	@Override
	public boolean delete(@NonNull String id) throws ExecutionException, InterruptedException {
		DocumentReference document = getCollection().document(id);
		ApiFuture<DocumentSnapshot> snapshotApiFuture = document.get();
		DocumentSnapshot snapshot = snapshotApiFuture.get();
		if (!snapshot.exists()) {    //  If entry does not exist, don't try deleting it.
			return false;
		}

		ApiFuture<WriteResult> future = document.delete();

		WriteResult result = future.get();
		Timestamp deletionTimestamp = result.getUpdateTime();

		System.out.println(TAG + " - Deleted user at deletionTimestamp: " + deletionTimestamp.toString());

		return true;
	}
}
