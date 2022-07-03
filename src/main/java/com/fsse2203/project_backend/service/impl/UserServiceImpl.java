package com.fsse2203.project_backend.service.impl;

import com.fsse2203.project_backend.data.user.CreateUserData;
import com.fsse2203.project_backend.data.user.UserDetailsData;
import com.fsse2203.project_backend.data.user.entity.UserEntity;
import com.fsse2203.project_backend.reposotory.UserRepository;
import com.fsse2203.project_backend.security.FirebaseSecurityFilter;
import com.fsse2203.project_backend.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsData createUser(CreateUserData data) {
        UserEntity entity = new UserEntity(data);
        entity = userRepository.save(entity);
        return new UserDetailsData(entity);
    }

    @Override
    public UserDetailsData getUserDetailsByFirebaseUid (String firebaseUid) {
        UserEntity entity = getUserEntityByFirebaseUid(firebaseUid); //find user in database
        if (entity == null ) {
            return null;
        }
        return new UserDetailsData(entity);
    }

    @Override
    public UserEntity getUserEntityByFirebaseUid (String firebaseUid) {
        return userRepository.findByFirebaseUid(firebaseUid);
    }


}
