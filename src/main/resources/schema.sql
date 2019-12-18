CREATE TABLE IF NOT EXISTS file(
 added_date date,
 path VARCHAR(255) PRIMARY KEY,
 size long,
 parent_path VARCHAR(255),
 foreign key (parent_path) references file(path)
 );
