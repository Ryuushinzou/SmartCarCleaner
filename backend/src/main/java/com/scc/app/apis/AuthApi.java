package com.scc.app.apis;

import com.scc.app.firebase.models.User;

public interface AuthApi {
	User createUser();
	User updateUser();
	boolean disableUser();
}
