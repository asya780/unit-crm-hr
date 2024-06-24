CREATE TABLE Cabinet (
  id               INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name             varchar(50) NOT NULL UNIQUE,
  floor            integer(2) NOT NULL,
  creation_time    timestamp,
  last_update_time timestamp);

CREATE TABLE Department (
  id               INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name             varchar(50) NOT NULL UNIQUE,
  creation_time    timestamp NOT NULL,
  last_update_time timestamp NOT NULL);

CREATE TABLE Employee (
  id                     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name                   varchar(50) NOT NULL,
  surname                varchar(50) NOT NULL,
  middle_name            varchar(255) NOT NULL,
  birth_date             date NOT NULL,
  multiplier             real(10) NOT NULL,
  active                 integer(1) NOT NULL,
  dismissal_date         integer(10),
  creation_time          timestamp NOT NULL,
  last_update_time       timestamp NOT NULL,
  Positionid             integer(10) NOT NULL,
  Personal_informationid integer(10) NULL,
  Departmentid           integer(10) NOT NULL,
  Cabinetid              integer(10) NOT NULL,
  FOREIGN KEY(Cabinetid) REFERENCES Cabinet(id),
  FOREIGN KEY(Departmentid) REFERENCES Department(id),
  FOREIGN KEY(Personal_informationid) REFERENCES Personal_information(id),
  FOREIGN KEY(Positionid) REFERENCES Position(id));

CREATE TABLE Employee_Manager (
  manager  integer(10) NOT NULL,
  employee integer(10) NOT NULL,
  PRIMARY KEY (manager,
  employee),
  FOREIGN KEY(employee) REFERENCES Employee(id),
  FOREIGN KEY(manager) REFERENCES Employee(id));

CREATE TABLE Personal_information (
  id                   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  registration_address varchar(255),
  address              varchar(255),
  personal_number      varchar(20),
  creation_time        timestamp NOT NULL,
  last_update_time     timestamp NOT NULL);

CREATE TABLE Position (
  id               INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name             integer(10) NOT NULL UNIQUE,
  salary           integer(10) NOT NULL,
  creation_time    timestamp NOT NULL,
  last_update_time timestamp NOT NULL);

CREATE TABLE Vacation (
  id               INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "start"          date NOT NULL,
  "end"            date NOT NULL,
  approved         integer(1) NOT NULL,
  creation_time    timestamp NOT NULL,
  last_update_time timestamp NOT NULL,
  Employeeid       integer(10) NOT NULL,
  FOREIGN KEY(Employeeid) REFERENCES Employee(id));
