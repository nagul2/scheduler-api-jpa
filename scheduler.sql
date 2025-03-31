-- schedule 테이블 생성
create table schedule
(
    id        bigint       not null primary key auto_increment,
    user_id   bigint,
    title     varchar(255) not null,
    content   longtext,
    create_at datetime,
    modify_at datetime,
    foreign key (user_id) references users (id)
)

-- user 테이블 생성
create table users
(
    id        bigint       not null primary key auto_increment,
    username  varchar(255) not null unique,
    password  varchar(255),
    email     varchar(255) unique,
    create_at datetime,
    modify_at datetime
)

-- comment 테이블 생성
create table comment
(
    id bigint not null primary key auto_increment,
    schedule_id bigint,
    user_id bigint,
    content varchar(255) not null,
    create_at datetime,
    modify_at datetime,
    foreign key (user_id) references users (id),
    foreign key (schedule_id) references schedule (id)

)