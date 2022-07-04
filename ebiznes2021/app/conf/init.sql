CREATE TABLE "category" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "name" VARCHAR NOT NULL
);

CREATE TABLE "product" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "name" VARCHAR NOT NULL,
 "description" TEXT NOT NULL,
 "category" INT NOT NULL,
 FOREIGN KEY(category) references category(id)
);

CREATE TABLE "user" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "name" VARCHAR NOT NULL,
  "nickname" VARCHAR NOT NULL,
  "age" INT NOT NULL
)

CREATE TABLE "dog"
(
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL,
    "animal_type" VARCHAR NOT NULL,
    "age" INT NOT NULL,
    "price" NUMERIC not null
);
CREATE TABLE "cat"
(
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL,
    "animal_type" VARCHAR NOT NULL,
    "age" INT NOT NULL,
    "price" NUMERIC not null
);
CREATE TABLE "snake"
(
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL,
    "animal_type" VARCHAR NOT NULL,
    "age" INT NOT NULL,
    "price" NUMERIC not null
);
CREATE TABLE "parrot"
(
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL,
    "animal_type" VARCHAR NOT NULL,
    "age" INT NOT NULL,
    "price" NUMERIC not null
);
CREATE TABLE "pig"
(
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL,
    "animal_type" VARCHAR NOT NULL,
    "age" INT NOT NULL,
    "price" NUMERIC not null
);
CREATE TABLE "hamster"
(
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL,
    "animal_type" VARCHAR NOT NULL,
    "age" INT NOT NULL,
    "price" NUMERIC not null
);
CREATE TABLE "spider"
(
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL,
    "animal_type" VARCHAR NOT NULL,
    "age" INT NOT NULL,
    "price" NUMERIC not null
);

DROP TABLE "category"
DROP TABLE "product"
DROP TABLE "user"
DROP TABLE "dog"
DROP TABLE "cat"
DROP TABLE "snake"
DROP TABLE "parrot"
DROP TABLE "pig"
DROP TABLE "hamster"
DROP TABLE "spider"
