package com.scc.app.firebase.database;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.scc.app.model.Vehicle;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FbVehiclesDatabase extends FbDatabaseAbsImpl<Vehicle> {
	protected FbVehiclesDatabase() {
		super("vehicles");
	}

	@Override
	public Vehicle getById(@NonNull String id) throws ExecutionException, InterruptedException {
		DocumentReference documentReference = getCollection().document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot snapshot = future.get();

		Vehicle vehicle = null;
		if(snapshot.exists()) {
			vehicle = snapshot.toObject(Vehicle.class);
		}
		return vehicle;
	}

	@Override
	public Vehicle create(@NonNull Vehicle newEntry) throws ExecutionException, InterruptedException {
		DocumentReference document = getCollection().document();
		ApiFuture<WriteResult> future = document.set(newEntry);

		WriteResult result = future.get();
		Timestamp creationTimestamp = result.getUpdateTime();

		//  TODO: return new entry OR update newEntry with generated id and creationTimestamp
		return newEntry;
	}

	@Override
	public Vehicle update(@NonNull Vehicle entry) throws ExecutionException, InterruptedException {
		ApiFuture<WriteResult> future = getCollection().document(String.valueOf(entry.getId())).set(entry);

		WriteResult result = future.get();
		Timestamp lastUpdateTimestamp = result.getUpdateTime();

		//  TODO: return updated entry OR update entry with lastUpdateTimestamp
		return entry;
	}

	@Override
	public boolean delete(@NonNull Vehicle entry) throws ExecutionException, InterruptedException {

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

		return true;
	}
}
