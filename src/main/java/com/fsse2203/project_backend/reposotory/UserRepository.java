package com.fsse2203.project_backend.reposotory;

import com.fsse2203.project_backend.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository <UserEntity, Integer> {
    UserEntity findByFirebaseUid(@Param("firebaseUid") String firebaseUid);
}
