package com.scc.app.firebase.database;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.concurrent.ExecutionException;

public interface FbDatabase<Entity> {
	@Nullable
	Entity getById(@NonNull String id) throws ExecutionException, InterruptedException;
	@Nullable
	Entity create(@NonNull Entity newEntry) throws ExecutionException, InterruptedException;
	@Nullable
	Entity update(@NonNull Entity entry) throws ExecutionException, InterruptedException;
	boolean delete(@NonNull Entity entry) throws ExecutionException, InterruptedException;
	boolean delete(@NonNull String id) throws ExecutionException, InterruptedException;
}
