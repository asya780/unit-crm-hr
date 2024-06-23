export class Cabinet {
  constructor(id, name, floor, creationTime, lastUpdateTime) {
      this.id = id;
      this.name = name;
      this.floor = floor;
      this.creationTime = creationTime;
      this.lastUpdateTime = lastUpdateTime;
  }
}

export class Department {
  constructor(id, name, creationTime, lastUpdateTime) {
      this.id = id;
      this.name = name;
      this.creationTime = creationTime;
      this.lastUpdateTime = lastUpdateTime;
  }
}

export class Employee {
  constructor(id, name, surname, middleName, birthDate, multiplier, active, dismissalDate, creationTime, lastUpdateTime, position, personalInformation, department, cabinet) {
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
  constructor(id, registrationAddress, address, personalNumber, creationTime, lastUpdateTime) {
      this.id = id;
      this.registrationAddress = registrationAddress;
      this.address = address;
      this.personalNumber = personalNumber;
      this.creationTime = creationTime;
      this.lastUpdateTime = lastUpdateTime;
  }
}

export class Position {
  constructor(id, name, salary, creationTime, lastUpdateTime) {
      this.id = id;
      this.name = name;
      this.salary = salary;
      this.creationTime = creationTime;
      this.lastUpdateTime = lastUpdateTime;
  }
}

export class Vacation {
  constructor(id, start, end, approved, creationTime, lastUpdateTime, employee) {
      this.id = id;
      this.start = start;
      this.end = end;
      this.approved = approved;
      this.creationTime = creationTime;
      this.lastUpdateTime = lastUpdateTime;
      this.employee = employee;
  }
}
