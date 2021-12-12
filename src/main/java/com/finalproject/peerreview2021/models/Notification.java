package com.finalproject.peerreview2021.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Table(name = "notifications")
@Entity
public class Notification implements Comparable<Notification> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Integer id;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @Column(name = "seen", nullable = false)
    private Boolean seen = false;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @Column(name = "time", nullable = false)
    private Instant time;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "notifications_users",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersWithNotification;

    public List<User> getUsersWithNotification() {
        return usersWithNotification;
    }

    public void setUsersWithNotification(List<User> usersWithNotification) {
        this.usersWithNotification = usersWithNotification;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(Notification o) {
        return getTime().compareTo(o.getTime());
    }
}