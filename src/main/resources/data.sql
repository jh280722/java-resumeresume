INSERT INTO USER (id, email, password, name)
VALUES (1, 'emailData@email.com', 'password', 'user');
INSERT INTO SORTATION (id, name, user_id)
VALUES (1, 'sortationData', 1);
INSERT INTO DOCUMENT (id, name, sortation_id)
VALUES (1, 'documentData', 1);
INSERT INTO BOX (id, name, document_id)
VALUES (1, 'boxData', 1);

INSERT INTO ITEM (id, box_id, seq, type, name, value)
VALUES (1, 1, 1, 'text', '이름', '준호');
INSERT INTO ITEM (id, box_id, seq, type, name, value)
VALUES (2, 1, 0, 'textArea', '소개', '준호\nHi');
