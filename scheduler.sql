-- schedule 테이블 생성
create table schedule
(
    id        bigint       not null primary key auto_increment,
    title     varchar(255) not null,
    user_id   varchar(255) not null,
    content   longtext,
    create_at datetime,
    modify_at datetime
)

-- user 테이블 생성
create table user
(
    id        bigint       not null primary key auto_increment,
    username  varchar(255) not null,
    email     varchar(255),
    create_at datetime,
    modify_at datetime,
)
