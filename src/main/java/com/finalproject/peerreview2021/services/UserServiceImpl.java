package com.finalproject.peerreview2021.services;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.models.User;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.repositories.contracts.CommentRepository;
import com.finalproject.peerreview2021.repositories.contracts.ReviewerRepository;
import com.finalproject.peerreview2021.repositories.contracts.UserRepository;
import com.finalproject.peerreview2021.services.contracts.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String MODIFY_USER_ERROR_MESSAGE = "You are not allowed to change other user information";

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReviewerRepository reviewerRepository;

    public UserServiceImpl(UserRepository userRepository, CommentRepository commentRepository,
                           ReviewerRepository reviewerRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.reviewerRepository = reviewerRepository;
    }


    @Override
    public void create(User user) {
        if (!duplicateExist(user)) {
            userRepository.create(user);
        }
    }

    public void update(User user) {
        if (!duplicateExist(user)) {
            userRepository.update(user);
        }

    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public <V> User getByField(String name, V value) {
        return userRepository.getByField(name, value);
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }


    @Override
    public void delete(int id) {
        commentRepository.deleteUserComments(id);
        userRepository.delete(id);
    }

    @Override
    public List<User> filter(Optional<String> username, Optional<String> email, Optional<Integer> phone) {
        try {
            return userRepository.filter(username, email, phone);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Filter", "with this name or", "value");
        }
    }

    @Override
    public List<User> getPossibleAssignees(WorkItem workItem) {
        List<User> possibleAssignees = new ArrayList<>(workItem.getTeam().getMembers());
        possibleAssignees.remove(workItem.getCreatedBy());
        possibleAssignees.removeAll(reviewerRepository.getAllReviewersForWorkItemAsUsers(workItem));
        return possibleAssignees;
    }

    private boolean duplicateExist(User user) {
        boolean duplicateUsernameExists = true;
        boolean duplicateEmailExists = true;
        boolean duplicatePhoneNumberExists = true;

        try {
            User existingUser = userRepository.getByField("username", user.getUsername());
            if (Objects.equals(existingUser.getId(), user.getId())) {
                duplicateUsernameExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateUsernameExists = false;
        }

        try {
            User existingUser = userRepository.getByField("email", user.getEmail());
            if (Objects.equals(existingUser.getId(), user.getId())) {
                duplicateEmailExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateEmailExists = false;
        }

        try {
            User existingUser = userRepository.getByField("phoneNumber", user.getPhoneNumber());
            if (existingUser.getId() == user.getId()) {
                duplicatePhoneNumberExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicatePhoneNumberExists = false;
        }

        if (duplicateUsernameExists) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }

        if (duplicateEmailExists) {
            throw new DuplicateEntityException("Email", "value", user.getEmail());
        }

        if (duplicatePhoneNumberExists) {
            throw new DuplicateEntityException("Phone", "number", user.getPhoneNumber().toString());
        }
        return false;
    }
}
