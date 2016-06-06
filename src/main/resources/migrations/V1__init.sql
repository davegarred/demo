CREATE SCHEMA demo;

CREATE TABLE demo.employee (
  name varchar(36) NOT NULL,
  data clob
);

INSERT INTO demo.employee(name, data) VALUES ('Joe', '{ "name" : "Joe", "age" : 36 }');
