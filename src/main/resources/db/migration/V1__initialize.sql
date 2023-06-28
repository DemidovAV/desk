CREATE TABLE departments (
    id serial PRIMARY KEY,
    title varchar,
    description varchar);

INSERT INTO departments (title, description)
VALUES ('Department of 3D printing', 'we print some stuff here'),
       ('Department of turning machine', 'we make some metal stuff here');

CREATE TABLE products (
    id serial PRIMARY KEY,
    art varchar(100),
    title varchar(100),
    description varchar);

INSERT INTO products (art, title, description)
VALUES ('pA001', 'ProductA', 'product'),
       ('pB001', 'ProductB', 'product');

CREATE TABLE elements (
    id serial PRIMARY KEY,
    art varchar(100),
    title varchar(100),
    department_id int references departments,
    description varchar);

INSERT INTO elements (art, title, department_id, description)
VALUES ('el0001','Element1', 1, 'just element 1'),
       ('el0002','Element2', 1, 'just element 2'),
       ('el0003','Element3', 2, 'just element 3'),
       ('el0004','Element4', 2, 'just element 4');

CREATE TABLE products_elements (
    product_id int references  products,
    element_id int references elements,
    element_quantity int,
    primary key (product_id, element_id));

INSERT INTO products_elements (product_id, element_id, element_quantity)
VALUES (1, 1, 4),
        (1, 2, 3),
        (1, 3, 2),
        (2, 1, 5),
        (2, 2, 6),
        (2, 4, 8),
        (2, 3, 9);


CREATE TABLE tickets (
    id serial PRIMARY KEY,
    title varchar,
    creation_date timestamp,
    close_date timestamp,
    status varchar,
    product_id int references products,
    quantity int,
    priority int,
    expiration_date timestamp,
    comment varchar);

CREATE TABLE employees (
    id serial PRIMARY KEY,
    name varchar,
    department_id int references departments,
    status varchar);

INSERT INTO employees (name, department_id, status)
VALUES ('Mihail', 1, 'Workable'),
       ('Aleksey', 2, 'Workable'),
       ('Vasya', 2, 'Ill'),
       ('Elena', 1, 'Workable');

CREATE TABLE tasks (
    id serial PRIMARY KEY,
    executor_id int references employees,
    expiration_date timestamp,
    priority int,
    status varchar,
    ticket_id int references tickets);



