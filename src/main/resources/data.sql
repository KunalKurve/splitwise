-- Reset auto increments
ALTER TABLE users AUTO_INCREMENT=100;
ALTER TABLE user_groups AUTO_INCREMENT=100;
ALTER TABLE expense AUTO_INCREMENT=100;
ALTER TABLE expense_user AUTO_INCREMENT=100;
ALTER TABLE group_member AUTO_INCREMENT=100;
ALTER TABLE group_admin AUTO_INCREMENT=100;


---------------------------------------------------
-- USERS
---------------------------------------------------

INSERT INTO users(created_at,name,password,phone)
VALUES
    (NOW(),'Kunal','$2a$10$dummyEncryptedPassword','9999999991'),
    (NOW(),'Rahul','$2a$10$dummyEncryptedPassword','9999999992'),
    (NOW(),'Amit','$2a$10$dummyEncryptedPassword','9999999993'),
    (NOW(),'Priya','$2a$10$dummyEncryptedPassword','9999999994');


---------------------------------------------------
-- GROUP
---------------------------------------------------

INSERT INTO user_groups
(created_at,group_name,description,created_by_id)
VALUES
    (
        NOW(),
        'GoaTrip',
        'Trip expenses for Goa',
        100
    );


---------------------------------------------------
-- GROUP ADMINS
---------------------------------------------------

INSERT INTO group_admin
(created_at,group_id,admin_id,added_by_id)
VALUES
    (NOW(),100,100,100);


---------------------------------------------------
-- GROUP MEMBERS
---------------------------------------------------

INSERT INTO group_member
(created_at,added_at,group_id,member_id,added_by_id)
VALUES
    (NOW(),NOW(),100,100,100),
    (NOW(),NOW(),100,101,100),
    (NOW(),NOW(),100,102,100),
    (NOW(),NOW(),100,103,100);


---------------------------------------------------
-- EXPENSE 1
-- Kunal paid 2000
---------------------------------------------------

INSERT INTO expense
(
    created_at,
    description,
    amount,
    expense_type,
    created_by_id,
    group_id
)
VALUES
    (
        NOW(),
        'Lunch',
        2000,
        'NORMAL',
        100,
        100
    );

-- PAID
INSERT INTO expense_user
(created_at,amount,currency,expense_user_type,expense_id,user_id)
VALUES
    (NOW(),2000,'INR','PAID',100,100);

-- OWED
INSERT INTO expense_user
(created_at,amount,currency,expense_user_type,expense_id,user_id)
VALUES
    (NOW(),500,'INR','OWED',100,100),
    (NOW(),500,'INR','OWED',100,101),
    (NOW(),500,'INR','OWED',100,102),
    (NOW(),500,'INR','OWED',100,103);



---------------------------------------------------
-- EXPENSE 2
-- Rahul paid 4000
---------------------------------------------------

INSERT INTO expense
(
    created_at,
    description,
    amount,
    expense_type,
    created_by_id,
    group_id
)
VALUES
    (
        NOW(),
        'Hotel',
        4000,
        'NORMAL',
        101,
        100
    );

INSERT INTO expense_user
(created_at,amount,currency,expense_user_type,expense_id,user_id)
VALUES
    (NOW(),4000,'INR','PAID',101,101);

INSERT INTO expense_user
(created_at,amount,currency,expense_user_type,expense_id,user_id)
VALUES
    (NOW(),1000,'INR','OWED',101,100),
    (NOW(),1000,'INR','OWED',101,101),
    (NOW(),1000,'INR','OWED',101,102),
    (NOW(),1000,'INR','OWED',101,103);


---------------------------------------------------
-- EXPENSE 3
-- Amit paid 1000
---------------------------------------------------

INSERT INTO expense
(
    created_at,
    description,
    amount,
    expense_type,
    created_by_id,
    group_id
)
VALUES
    (
        NOW(),
        'Taxi',
        1000,
        'NORMAL',
        102,
        100
    );

INSERT INTO expense_user
(created_at,amount,currency,expense_user_type,expense_id,user_id)
VALUES
    (NOW(),1000,'INR','PAID',102,102);

INSERT INTO expense_user
(created_at,amount,currency,expense_user_type,expense_id,user_id)
VALUES
    (NOW(),250,'INR','OWED',102,100),
    (NOW(),250,'INR','OWED',102,101),
    (NOW(),250,'INR','OWED',102,102),
    (NOW(),250,'INR','OWED',102,103);