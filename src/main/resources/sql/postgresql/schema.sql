CREATE TABLE Cabinet (
  id               SERIAL NOT NULL,
  name             varchar(50) NOT NULL UNIQUE,
  floor            int4 NOT NULL,
  creation_time    timestamp,
  last_update_time timestamp,
  PRIMARY KEY (id));

CREATE TABLE Department (
  id               SERIAL NOT NULL,
  name             varchar(50) NOT NULL UNIQUE,
  creation_time    timestamp NOT NULL,
  last_update_time timestamp NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE Employee (
  id                     SERIAL NOT NULL,
  name                   varchar(50) NOT NULL,
  surname                varchar(50) NOT NULL,
  middle_name            varchar(255) NOT NULL,
  birth_date             date NOT NULL,
  multiplier             float4 NOT NULL,
  active                 bool NOT NULL,
  dismissal_date         int4,
  creation_time          timestamp NOT NULL,
  last_update_time       timestamp NOT NULL,
  Positionid             int4 NOT NULL,
  Personal_informationid int4 NULL,
  Departmentid           int4 NOT NULL,
  Cabinetid              int4 NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE Employee_Manager (
  manager  int4 NOT NULL,
  employee int4 NOT NULL,
  PRIMARY KEY (manager,
  employee));

CREATE TABLE Personal_information (
  id                   SERIAL NOT NULL,
  registration_address varchar(255),
  address              varchar(255),
  personal_number      varchar(20),
  creation_time        timestamp NOT NULL,
  last_update_time     timestamp NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE Position (
  id               SERIAL NOT NULL,
  name             int4 NOT NULL UNIQUE,
  salary           int4 NOT NULL,
  creation_time    timestamp NOT NULL,
  last_update_time timestamp NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE Vacation (
  id               SERIAL NOT NULL,
  "start"          date NOT NULL,
  "end"            date NOT NULL,
  approved         bool NOT NULL,
  creation_time    timestamp NOT NULL,
  last_update_time timestamp NOT NULL,
  Employeeid       int4 NOT NULL,
  PRIMARY KEY (id));


ALTER TABLE Employee ADD CONSTRAINT FKEmployee543604 FOREIGN KEY (Cabinetid) REFERENCES Cabinet (id);
ALTER TABLE Employee ADD CONSTRAINT FKEmployee700449 FOREIGN KEY (Departmentid) REFERENCES Department (id);
ALTER TABLE Employee_Manager ADD CONSTRAINT FKEmployee_M489642 FOREIGN KEY (employee) REFERENCES Employee (id);
ALTER TABLE Employee_Manager ADD CONSTRAINT FKEmployee_M699281 FOREIGN KEY (manager) REFERENCES Employee (id);
ALTER TABLE Employee ADD CONSTRAINT FKEmployee762893 FOREIGN KEY (Personal_informationid) REFERENCES Personal_information (id);
ALTER TABLE Vacation ADD CONSTRAINT FKVacation423125 FOREIGN KEY (Employeeid) REFERENCES Employee (id);
ALTER TABLE Employee ADD CONSTRAINT FKEmployee105044 FOREIGN KEY (Positionid) REFERENCES Position (id);
