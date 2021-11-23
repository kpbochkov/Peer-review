INSERT INTO peer_review_2021.statuses (status_id, name)
VALUES (1, 'Pending');
INSERT INTO peer_review_2021.statuses (status_id, name)
VALUES (2, 'Under Review');
INSERT INTO peer_review_2021.statuses (status_id, name)
VALUES (3, 'Change Requested');
INSERT INTO peer_review_2021.statuses (status_id, name)
VALUES (4, 'Accepted');
INSERT INTO peer_review_2021.statuses (status_id, name)
VALUES (5, 'Rejected');

START TRANSACTION;
INSERT INTO peer_review_2021.users (user_id, username, password, email, phone_number, photo, team_id)
VALUES (1, 'georgi', 'pass', 'georgi.marinov@gmail.com', 0886234753, null, 1);

INSERT INTO peer_review_2021.teams (team_id, name, owner)
VALUES (1, 'IT', 1);
COMMIT ;