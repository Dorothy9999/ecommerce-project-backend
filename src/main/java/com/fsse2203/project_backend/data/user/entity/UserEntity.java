package com.fsse2203.project_backend.data.user.entity;

import com.fsse2203.project_backend.data.user.CreateUserData;
import com.fsse2203.project_backend.data.user.UserDetailsData;
import com.google.firebase.auth.FirebaseToken;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;

    @Column(name = "firebase_uid")
    private String firebaseUid;

    @Column(name = "email")
    private String email;

    //entity must need empty constructor
    //why? becoz @entity needs this to work
    public UserEntity () {}

    public UserEntity (CreateUserData data) {
        this.firebaseUid = data.getFirebaseUid();
        this.email = data.getEmail();
        //no id becoz, if null, then it will auto generate one id
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
