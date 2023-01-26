-- user 
desc user;
select * from user;
-- join
insert into user values(null,'jeongeunsung','a1@gmail.com',password('1234'),'male',now());

-- login
select no,name from user where email = 'a1@gmail.com' and password = password('1234');

select no,name,email,password,gender from user where no = 1;

update user set name='jeongeunsung1', password = password('1234'),gender='female' where no = 1;
update user set name='eunsung3',gender='female' where no = 2;
update user set name='eunsung3', password = password('123'),gender='female' where no = 2;
update user set name='eunsung2', password = password('1234'),gender='male' where no = 2;