create table peer_review_2021.statuses
(
    status_id int auto_increment
        primary key,
    name      varchar(30) not null
);

create table peer_review_2021.teams
(
    team_id int auto_increment
        primary key,
    name    varchar(30) not null,
    owner   int         not null,
    constraint teams_name_uindex
        unique (name)
);

create table peer_review_2021.users
(
    user_id      int auto_increment
        primary key,
    username     varchar(20) not null,
    password     varchar(50) not null,
    email        varchar(50) not null,
    phone_number int         not null,
    photo        blob        null,
    team_id      int         null,
    constraint users_email_uindex
        unique (email),
    constraint users_phone_number_uindex
        unique (phone_number),
    constraint users_teams_team_id_fk
        foreign key (team_id) references peer_review_2021.teams (team_id)
);

create table peer_review_2021.notifications
(
    notification_id int auto_increment
        primary key,
    user_id         int                                    not null,
    description     varchar(50)                            not null,
    seen            tinyint(1) default 0                   not null,
    time            timestamp  default current_timestamp() not null on update current_timestamp(),
    constraint notifications_users_user_id_fk
        foreign key (user_id) references peer_review_2021.users (user_id)
);

create table peer_review_2021.team_invitations
(
    team_invitation_id int auto_increment
        primary key,
    user_id            int not null,
    team_id            int not null,
    constraint team_invitations_teams_team_id_fk
        foreign key (team_id) references peer_review_2021.teams (team_id),
    constraint team_invitations_users_user_id_fk
        foreign key (user_id) references peer_review_2021.users (user_id)
);

alter table peer_review_2021.teams
    add constraint teams_users_user_id_fk
        foreign key (owner) references peer_review_2021.users (user_id);

create table peer_review_2021.workitems
(
    work_item_id int auto_increment
        primary key,
    name         varchar(30) not null,
    title        varchar(80) null,
    description  longtext    not null,
    created_by   int         null,
    team_id      int         null,
    constraint workitems_teams_team_id_fk
        foreign key (team_id) references peer_review_2021.teams (team_id),
    constraint workitems_users_user_id_fk_2
        foreign key (created_by) references peer_review_2021.users (user_id)
);

create table peer_review_2021.comments
(
    comment_id   int auto_increment
        primary key,
    user_id      int  not null,
    work_item_id int  not null,
    content      text not null,
    constraint comments_users_user_id_fk
        foreign key (user_id) references peer_review_2021.users (user_id),
    constraint comments_workitems_work_item_id_fk
        foreign key (work_item_id) references peer_review_2021.workitems (work_item_id)
);

create table peer_review_2021.reviewers
(
    user_id      int not null,
    work_item_id int not null,
    status_id    int null,
    constraint reviewers_statuses_status_id_fk
        foreign key (status_id) references peer_review_2021.statuses (status_id),
    constraint reviewers_users_user_id_fk
        foreign key (user_id) references peer_review_2021.users (user_id),
    constraint reviewers_workitems_work_item_id_fk
        foreign key (work_item_id) references peer_review_2021.workitems (work_item_id)
);

