export class Cabinet {
  constructor(id = 0, name = '', floor = 0, creationTime = new Date().toISOString(), lastUpdateTime = new Date().toISOString()) {
    this.id = id;
    this.name = name;
    this.floor = floor;
    this.creationTime = creationTime;
    this.lastUpdateTime = lastUpdateTime;
  }
}

export class Department {
  constructor(id = 0, name = '', creationTime = new Date().toISOString(), lastUpdateTime = new Date().toISOString()) {
    this.id = id;
    this.name = name;
    this.creationTime = creationTime;
    this.lastUpdateTime = lastUpdateTime;
  }
}

export class Employee {
  constructor(
    id = 0,
    name = '',
    surname = '',
    middleName = '',
    birthDate = new Date().toISOString().split('T')[0],
    multiplier = 1.0,
    active = true,
    dismissalDate = null,
    creationTime = new Date().toISOString(),
    lastUpdateTime = new Date().toISOString(),
    position = new Position(),
    personalInformation = new PersonalInformation(),
    department = new Department(),
    cabinet = new Cabinet()
  ) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.middleName = middleName;
    this.birthDate = birthDate;
    this.multiplier = multiplier;
    this.active = active;
    this.dismissalDate = dismissalDate;
    this.creationTime = creationTime;
    this.lastUpdateTime = lastUpdateTime;
    this.position = position;
    this.personalInformation = personalInformation;
    this.department = department;
    this.cabinet = cabinet;
  }
}

export class PersonalInformation {
  constructor(id = 0, registrationAddress = '', address = '', personalNumber = '', creationTime = new Date().toISOString(), lastUpdateTime = new Date().toISOString()) {
    this.id = id;
    this.registrationAddress = registrationAddress;
    this.address = address;
    this.personalNumber = personalNumber;
    this.creationTime = creationTime;
    this.lastUpdateTime = lastUpdateTime;
  }
}

export class Position {
  constructor(id = 0, name = '', salary = 0, creationTime = new Date().toISOString(), lastUpdateTime = new Date().toISOString()) {
    this.id = id;
    this.name = name;
    this.salary = salary;
    this.creationTime = creationTime;
    this.lastUpdateTime = lastUpdateTime;
  }

}

export class Vacation {
  constructor(id = 0, start = new Date().toISOString().split('T')[0], end = new Date().toISOString().split('T')[0], approved = false, creationTime = new Date().toISOString(), lastUpdateTime = new Date().toISOString(), employee = new Employee()) {
    this.id = id;
    this.start = start;
    this.end = end;
    this.approved = approved;
    this.creationTime = creationTime;
    this.lastUpdateTime = lastUpdateTime;
    this.employee = employee;
  }


}
