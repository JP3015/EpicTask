create table task (
    id bigint not null, 
    description varchar(255), 
    score integer not null, 
    status integer not null, 
    title varchar(255), 
    primary key (id)
);

 insert into task (description, score, status, title, id) 
 values ('modelagem do banco', 100, 0, 'Modelagem', 0);

