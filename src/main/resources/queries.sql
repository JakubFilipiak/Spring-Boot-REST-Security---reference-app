--/*ROLES*/
--insert into role ("role_id", "role")
--values (1,'ADMIN');
--
--insert into role ("role_id", "role")
--values (2,'USER');
--
----/*USERS*/
----insert into publicuser ("user_id", "active", "name", "password")
----values (1,1,'admin','$2a$10$rAdvqnXYNtpZElbT7PCFw.Nq7g6DTrYEILqgLJ0IGybXKhfSFWBXe');
----
----insert into user ("user_id", "active", "name", "password")
----values (2,1,'user','$2a$10$d/8eSat57epzqTAZ6CBCaee9T9p4s5CKq9mrLBvxS84NdsDksEq76');
--
--/*ADD ROLES TO USER*/
--insert into user_role ("user_id", "role_id") values (1,1);
--insert into user_role ("user_id", "role_id") values (1,2);
--insert into user_role ("user_id", "role_id") values (2,2);