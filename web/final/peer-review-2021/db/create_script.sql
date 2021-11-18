create table email_addresses
(
    email_id      int auto_increment
        primary key,
    email_address varchar(50) not null,
    constraint email_addresses_email_address_uindex
        unique (email_address)
);

create table item_comments
(
    comment_id          int auto_increment
        primary key,
    comment_description varchar(200) null
);

create table passwords
(
    password_id   int auto_increment
        primary key,
    user_password varchar(8) not null
);

create table phone_numbers
(
    phone_number_id   int auto_increment
        primary key,
    user_phone_number varchar(10) null,
    constraint phone_numbers_user_phone_number_uindex
        unique (user_phone_number)
);

create table review_roles
(
    review_role_id   int auto_increment
        primary key,
    review_role_name varchar(50) not null,
    constraint roles_role_name_uindex
        unique (review_role_name)
);

create table team_invitations
(
    invitation_id int auto_increment
        primary key,
    team_id       int null,
    constraint team_invitations_team_id_uindex
        unique (team_id)
);

create table team_roles
(
    team_role_id   int auto_increment
        primary key,
    team_role_name varchar(50) not null
);

create table usernames
(
    username_id int auto_increment
        primary key,
    username    varchar(20) not null,
    constraint usernames_username_uindex
        unique (username)
);

create table users
(
    user_id         int auto_increment
        primary key,
    first_name      varchar(100) null,
    last_name       varchar(100) null,
    username_id     int          not null,
    password_id     int          not null,
    email_id        int          not null,
    phone_number_id int          null,
    user_photo      varchar(25)  null,
    invitation_id   int          null,
    constraint users_email_uindex
        unique (email_id),
    constraint users_invitation_id_uindex
        unique (invitation_id),
    constraint users_password_id_uindex
        unique (password_id),
    constraint users_phone_number_id_uindex
        unique (phone_number_id),
    constraint users_username_uindex
        unique (username_id),
    constraint users_email_addresses_fk
        foreign key (email_id) references email_addresses (email_id),
    constraint users_passwords_fk
        foreign key (password_id) references passwords (password_id),
    constraint users_phone_numbers_fk
        foreign key (phone_number_id) references phone_numbers (phone_number_id),
    constraint users_team_invitations_fk
        foreign key (invitation_id) references team_invitations (invitation_id),
    constraint users_usernames_fk
        foreign key (username_id) references usernames (username_id)
);

create table review_requests
(
    review_id      int not null
        primary key,
    user_id        int null,
    review_role_id int null,
    constraint review_requests_review_role_id_uindex
        unique (review_role_id),
    constraint review_requests_user_id_uindex
        unique (user_id),
    constraint review_requests_review_roles_fk
        foreign key (review_role_id) references review_roles (review_role_id),
    constraint review_requests_users_fk
        foreign key (user_id) references users (user_id)
);

create table work_item_statutes
(
    item_status_id   int auto_increment
        primary key,
    item_status_name varchar(50) not null,
    constraint work_item_statutes_item_status_name_uindex
        unique (item_status_name)
);

create table work_items
(
    work_item_id     int          not null
        primary key,
    item_title       varchar(80)  not null,
    item_name        varchar(30)  null,
    item_reviewer    varchar(50)  not null,
    comment_id       int          null,
    item_status_id   int          null,
    item_creator     varchar(100) null,
    item_description varchar(200) not null,
    constraint work_items_comment_id_uindex
        unique (comment_id),
    constraint work_items_item_name_uindex
        unique (item_name),
    constraint work_items_item_status_id_uindex
        unique (item_status_id),
    constraint work_items_item_comments_fk
        foreign key (comment_id) references item_comments (comment_id),
    constraint work_items_work_item_statuses_fk
        foreign key (item_status_id) references work_item_statutes (item_status_id)
);

create table teams
(
    team_id      int         not null
        primary key,
    team_name    varchar(30) not null,
    team_role_id int         null,
    work_item_id int         null,
    user_id      int         null,
    constraint teams_team_role_id_uindex
        unique (team_role_id),
    constraint teams_user_id_uindex
        unique (user_id),
    constraint teams_work_item_id_uindex
        unique (work_item_id),
    constraint teams_team_roles_fk
        foreign key (team_role_id) references team_roles (team_role_id),
    constraint teams_users_fk
        foreign key (user_id) references users (user_id),
    constraint teams_work_items_fk
        foreign key (work_item_id) references work_items (work_item_id)
);

alter table team_invitations
    add constraint team_invitations_teams_fk
        foreign key (team_id) references teams (team_id);


