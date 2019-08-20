CREATE TABLE IF NOT EXISTS Doctor (
    id INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(15),
    surname VARCHAR(15),
    patronymic VARCHAR(15),
    specialization VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS Patient (
    id INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(15),
    surname VARCHAR(15),
    patronymic VARCHAR(15),
    telephone VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS Recipe (
    id INTEGER IDENTITY PRIMARY KEY,
    description VARCHAR(250),
    patientId INTEGER FOREIGN KEY REFERENCES Patient(id),
    doctorId INTEGER FOREIGN KEY REFERENCES Doctor(id),
    dateRecipeCreation DATE,
    validityDays INTEGER,
    priority VARCHAR(20)
);
