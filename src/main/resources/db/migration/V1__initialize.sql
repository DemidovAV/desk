CREATE TABLE products (
    id serial PRIMARY KEY,
    art varchar(100),
    title varchar(100),
    description varchar);

INSERT INTO products (art, title, description)
VALUES ('pA001', 'ProductA', 'product'), ('pB001', 'ProductB', 'product');

CREATE TABLE elements (
    id serial PRIMARY KEY,
    art varchar(100),
    title varchar(100),
    docs_id int,
    department_id int,
    description varchar);

INSERT INTO elements (art, title, docs_id, department_id, description)
VALUES ('el0001','Element1', 1, 1, 'just element 1'), ('el0002','Element2', 2, 1, 'just element 2'), ('el0003','Element3', 3, 2, 'just element 3'),
('el0004','Element4', 4, 2, 'just element 4');

CREATE TABLE products_elements (
    product_id int references  products,
    element_id int references elements,
    element_quantity int,
    primary key (product_id, element_id));

CREATE TABLE tickets (
    id serial PRIMARY KEY,
    creation_date timestamp,
    close_date timestamp,
    status varchar,
    product_id int references products,
    quantity int,
    priority int,
    expiration_date timestamp,
    comment varchar);

CREATE TABLE tasks (
    id serial PRIMARY KEY,
    executor_id int references employees,
    expiration_date timestamp,
    priority int,
    status varchar,
    ticket_id int references tickets);

CREATE TABLE departments (
    id serial PRIMARY KEY,
    title varchar,
    description varchar);

CREATE TABLE employees (
    id serial PRIMARY KEY,
    name varchar,
    department_id int references departments,
    status varchar);

