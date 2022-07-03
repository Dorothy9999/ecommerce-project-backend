package com.fsse2203.project_backend.service;

import com.fsse2203.project_backend.data.user.CreateUserData;
import com.fsse2203.project_backend.data.user.UserDetailsData;
import com.fsse2203.project_backend.data.user.entity.UserEntity;

public interface UserService {
    UserDetailsData getUserDetailsByFirebaseUid(String firebaseUid);
    UserDetailsData createUser(CreateUserData data);
    UserEntity getUserEntityByFirebaseUid (String firebaseUid);

}
