-- schedule 테이블 생성
create table schedule
(
    id        bigint       not null primary key auto_increment,
    title     varchar(255) not null,
    writer    varchar(255) not null,
    content   longtext,
    create_at datetime,
    modify_at datetime
)
