CREATE SCHEMA demo;

CREATE TABLE demo.employee (
  name VARCHAR(36) PRIMARY KEY,
  data clob
);

INSERT INTO demo.employee(name, data) VALUES ('Joe', '{ "name" : "Joe", "age" : 36 }');
INSERT INTO demo.employee(name, data) VALUES ('Patrick', '{ "name" : "Patrick", "age" : 19 }');


-- rollback
-- DROP SCHEMA demo;