drop database `peer_review_2021`;
create database if not exists `peer_review_2021`;
use `peer_review_2021`;

create table statuses
(
    status_id int auto_increment
        primary key,
    name varchar(30) not null
);

create table users
(
    user_id int auto_increment
        primary key,
    username varchar(20) not null,
    password varchar(50) not null,
    email varchar(50) not null,
    phone_number int not null,
    photo longblob null,
    active tinyint(1) default 1 not null,
    constraint users_email_uindex
        unique (email),
    constraint users_phone_number_uindex
        unique (phone_number)
);

create table notifications
(
    notification_id int auto_increment
        primary key,
    description varchar(50) not null,
    seen tinyint(1) default 0 not null,
    time timestamp default current_timestamp() not null on update current_timestamp()
);

create table teams
(
    team_id int auto_increment
        primary key,
    name varchar(30) not null,
    owner int not null,
    active tinyint(1) default 1 not null,
    constraint teams_name_uindex
        unique (name),
    constraint teams_users_user_id_fk
        foreign key (owner) references users (user_id)
);

create table team_invitations
(
    team_invitation_id int auto_increment
        primary key,
    user_id int not null,
    team_id int not null,
    constraint team_invitations_teams_team_id_fk
        foreign key (team_id) references teams (team_id),
    constraint team_invitations_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table users_teams
(
    user_id int not null,
    team_id int not null,
    constraint users_teams_teams_team_id_fk
        foreign key (team_id) references teams (team_id),
    constraint users_teams_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table workitems
(
    work_item_id int auto_increment
        primary key,
    title varchar(80) null,
    description longtext not null,
    created_by int null,
    team_id int null,
    status_id int default 1 not null,
    active tinyint(1) default 1 not null,
    constraint workitems_statuses_status_id_fk
        foreign key (status_id) references statuses (status_id),
    constraint workitems_teams_team_id_fk
        foreign key (team_id) references teams (team_id),
    constraint workitems_users_user_id_fk_2
        foreign key (created_by) references users (user_id)
);

create table comments
(
    comment_id int auto_increment
        primary key,
    user_id int not null,
    work_item_id int not null,
    content text not null,
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint comments_workitems_work_item_id_fk
        foreign key (work_item_id) references workitems (work_item_id)
);

create table reviewers
(
    reviewer_id int auto_increment
        primary key,
    user_id int not null,
    work_item_id int not null,
    status_id int null,
    constraint reviewers_statuses_status_id_fk
        foreign key (status_id) references statuses (status_id),
    constraint reviewers_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint reviewers_workitems_work_item_id_fk
        foreign key (work_item_id) references workitems (work_item_id)
);

create table notifications_users
(
    notification_id int not null,
    user_id int not null,
    constraint users_teams_notifications_notification_id_fk
        foreign key (notification_id) references notifications (notification_id),
    constraint users_teams_users_user_id_fk
        foreign key (user_id) references users (user_id)
);
