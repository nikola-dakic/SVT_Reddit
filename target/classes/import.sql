INSERT INTO user VALUES ('Administrator', 1, 'Profil admina', 'admin_display_name', 'admin@yahoo.com', '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', '2022-09-11', 'admin')
INSERT INTO user VALUES ('User', 2, 'Profil usera', 'user_display_name', 'user@yahoo.com', '$2a$10$APZmkzJfSiMIH0RzO8fb5eOZ0QCe3273LLLDgYzzhJxB7XA5RDPHy','2022-09-11', 'user')
INSERT INTO user VALUES ('User', 3, 'Profil usera 2', 'user_display_name', 'user2@yahoo.com', '$2a$10$APZmkzJfSiMIH0RzO8fb5eOZ0QCe3273LLLDgYzzhJxB7XA5RDPHy', '2022-09-11', 'user2')



INSERT INTO post VALUES (1, '2022-09-01', false, 'Please write it down why are you on reddit at the moment', 'Why are you on reddit right now?', 2)
INSERT INTO post VALUES (2, '2022-09-04', false, 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry', 'Lorem Ipsum', 2)
INSERT INTO post VALUES (3, '2022-09-02', false, 'The purpose of lorem ipsum is to create a natural looking block of text (sentence, paragraph, page, etc.) that doesnt distract from the layout. ', 'Some title', 3)

INSERT INTO community VALUES (1, '2022-09-11', 'Circle of Friends is a community about friends.', false, 'Circle of Friends', NULL)


INSERT INTO community_posts VALUES (1, 1)
INSERT INTO community_posts VALUES (1, 2)
INSERT INTO community_posts VALUES (1, 3)

INSERT INTO moderator VALUES (1, 1, 2);


INSERT INTO vote VALUES (1, '2022-09-01', 'UPVOTE', 1, 2)
INSERT INTO vote VALUES (2, '2022-09-04', 'UPVOTE', 2, 2)
INSERT INTO vote VALUES (3, '2022-09-02', 'UPVOTE', 3, 3)
INSERT INTO vote VALUES (4, '2022-09-11', 'UPVOTE', 1, 3)
