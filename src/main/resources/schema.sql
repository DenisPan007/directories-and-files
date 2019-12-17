CREATE TABLE IF NOT EXISTS file(
 path VARCHAR(255) PRIMARY KEY,
 size long,
 parent_path VARCHAR(255),
 foreign key (parent_path) references file(path)
 );
