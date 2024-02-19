INSERT INTO school (name, hour_price) VALUES
                                          ('Oakridge', 10.20),
                                          ('Maplewood', 25.00),
                                          ('Sunnydale', 30.50);

INSERT INTO parent (first_name, last_name) VALUES
                                             ('John', 'Smith'),
                                             ('Emily', 'Johnson'),
                                             ('Michael', 'Williams');

INSERT INTO child (first_name, last_name, parent_id, school_id) VALUES
                                                                  ('Emma', 'Smith', 1, 1),
                                                                  ('Liam', 'Johnson', 2, 2),
                                                                  ('Olivia', 'Williams', 3, 1),
                                                                  ('Noah', 'Smith', 1, 3),
                                                                  ('Ava', 'Johnson', 2, 2),
                                                                  ('William', 'Williams', 3, 1);

INSERT INTO attendance (child_id, entry_date, exit_date) VALUES
                                                             (1, '2024-02-17 05:00:00', '2024-02-17 16:00:00'),
                                                             (2, '2024-02-17 06:30:00', '2024-02-17 15:30:00'),
                                                             (3, '2024-02-17 04:00:00', '2024-02-17 15:00:00'),
                                                             (4, '2024-02-17 06:15:00', '2024-02-17 16:15:00'),
                                                             (5, '2024-02-17 08:45:00', '2024-02-17 16:45:00'),
                                                             (6, '2024-02-17 09:30:00', '2024-02-17 16:30:00'),
                                                             (1, '2024-02-18 08:00:00', '2024-02-18 16:00:00'),
                                                             (2, '2024-02-18 08:30:00', '2024-02-18 15:30:00'),
                                                             (3, '2024-02-18 09:00:00', '2024-02-18 15:00:00'),
                                                             (4, '2024-02-18 08:15:00', '2024-02-18 16:15:00'),
                                                             (5, '2024-02-18 08:45:00', '2024-02-18 16:45:00'),
                                                             (6, '2024-02-18 09:30:00', '2024-02-18 16:30:00');