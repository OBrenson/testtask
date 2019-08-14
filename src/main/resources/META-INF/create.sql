CREATE TABLE Doctor (
    id INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(15),
    surname VARCHAR(15),
    patronymic VARCHAR(15),
    specialization VARCHAR(20)
);
CREATE TABLE Patient (
    id INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(15),
    surname VARCHAR(15),
    patronymic VARCHAR(15),
    telephone VARCHAR(20)
);
CREATE TABLE Recipe (
    id INTEGER IDENTITY PRIMARY KEY,
    description VARCHAR(15),
    patientId INTEGER NOT NULL,
    doctorId INTEGER NOT NULL,
    dateRecipeCreation DATE,
    validityDays INTEGER,
    priority VARCHAR(20),
    FOREIGN KEY (doctorId) REFERENCES Doctor(id),
    FOREIGN KEY (patientId) REFERENCES Patient(id)
);