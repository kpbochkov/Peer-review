package com.finalproject.peerreview2021.repositories.contracts;

import com.finalproject.peerreview2021.models.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends BaseCRUDRepository<User>{

    List<User> filter(Optional<String> username, Optional<String> email, Optional<Integer> phone);
}
