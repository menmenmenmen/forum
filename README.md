"# forum" 
create table user
(
	id int auto_increment,
	account_id varchar(50),
	name varchar(100),
	token varchar(100),
	gmt_create bigint,
	gmt_modified bigint,
	bio varchar(256),
	constraint user_pk
		primary key (id)
);



create table question
(
	id int auto_increment,
	title varchar(50),
	description varchar(256),
	gmt_create bigint,
	gmt_modified bigint,
	creator_id int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(100),
	constraint question_pk
		primary key (id)
);


