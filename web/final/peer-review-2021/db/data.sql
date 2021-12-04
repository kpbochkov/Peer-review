INSERT INTO statuses (status_id, name)
VALUES (1, 'Pending');
INSERT INTO statuses (status_id, name)
VALUES (2, 'Under Review');
INSERT INTO statuses (status_id, name)
VALUES (3, 'Change Requested');
INSERT INTO statuses (status_id, name)
VALUES (4, 'Accepted');
INSERT INTO statuses (status_id, name)
VALUES (5, 'Rejected');

INSERT INTO users (user_id, username, password, email, phone_number, photo)
VALUES (1, 'georgi', '12345678', 'georgi.marinov@gmail.com', 0886234753, null);
INSERT INTO users (user_id, username, password, email, phone_number, photo)
VALUES (2, 'konstantin', '12345678', 'konstantin@gmail.com', 089839933, null);
INSERT INTO users (user_id, username, password, email, phone_number, photo)
VALUES (3, 'miroslav', '12345678', 'miroslav@gmail.com', 089779933, null);

INSERT INTO teams (team_id, name, owner)
VALUES (1, 'IT', 1);
INSERT INTO teams (team_id, name, owner)
VALUES (2, 'HR', 2);
INSERT INTO teams (team_id, name, owner)
VALUES (3, 'Finance', 3);

INSERT INTO users_teams (user_id, team_id)
VALUES (1, 1);
INSERT INTO users_teams (user_id, team_id)
VALUES (1, 2);
INSERT INTO users_teams (user_id, team_id)
VALUES (2, 1);

INSERT INTO workitems (work_item_id, title, description, created_by, team_id)
VALUES (1, 'testtitle', 'some random description', 1, 1);
INSERT INTO workitems (work_item_id, title, description, created_by, team_id)
VALUES (2, 'testtitle2', 'some random description for testing', 2, 2);
INSERT INTO workitems (work_item_id, title, description, created_by, team_id)
VALUES (3, 'new title', 'some randomness', 3, 3);

INSERT INTO comments (comment_id, user_id, work_item_id, content)
VALUES (1, 1, 1, 'Great work, well done!');
INSERT INTO comments (comment_id, user_id, work_item_id, content)
VALUES (2, 2, 2, 'Shame!');
INSERT INTO comments (comment_id, user_id, work_item_id, content)
VALUES (3, 3, 3, 'Not bad!');
