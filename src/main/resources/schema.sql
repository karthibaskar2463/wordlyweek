create table if not exists writer(
    writerId int AUTO_INCREMENT PRIMARY KEY,
    writerName varchar(255),
    bio varchar(255)
); 

create table if not exists magazine(
    magazineId int AUTO_INCREMENT primary key,
    magazineName varchar(255),
    publicationDate varchar(255)
); 

create table if not exists writer_magazine(
    writerId int, 
    magazineId int, 
    primary key(writerId, magazineId), 
    foreign key(writerId) references writer(writerId), 
    foreign key(magazineId) references magazine(magazineId)
);