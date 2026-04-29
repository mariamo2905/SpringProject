-- Insert Regions
INSERT INTO regions (region_id, region_name) VALUES (1, 'Europe');
INSERT INTO regions (region_id, region_name) VALUES (2, 'Americas');
INSERT INTO regions (region_id, region_name) VALUES (3, 'Asia');

-- Insert Countries
INSERT INTO countries (country_id, country_name, region_id) VALUES ('US', 'United States of America', 2);
INSERT INTO countries (country_id, country_name, region_id) VALUES ('UK', 'United Kingdom', 1);
INSERT INTO countries (country_id, country_name, region_id) VALUES ('GE', 'Georgia', 1);
INSERT INTO countries (country_id, country_name, region_id) VALUES ('JP', 'Japan', 3);

-- Insert Locations
INSERT INTO locations (location_id, street_address, city, state_province, postal_code, country_id) VALUES (1, '1297 Via Cola di Rie', 'Seattle', 'Washington', '98199', 'US');
INSERT INTO locations (location_id, street_address, city, state_province, postal_code, country_id) VALUES (2, '93091 Calle della Testa', 'London', NULL, 'SW1A 1AA', 'UK');
INSERT INTO locations (location_id, street_address, city, state_province, postal_code, country_id) VALUES (3, 'Rustaveli Ave 12', 'Tbilisi', NULL, '0108', 'GE');
INSERT INTO locations (location_id, street_address, city, state_province, postal_code, country_id) VALUES (4, '2014 Jabberwocky Rd', 'San Francisco', 'California', '94105', 'US');
INSERT INTO locations (location_id, street_address, city, state_province, postal_code, country_id) VALUES (5, '1-5-2 Hitotsubashi', 'Tokyo', 'Tokyo Prefecture', '100-0003', 'JP');

-- Insert Departments (without managers first)
INSERT INTO departments (department_id, department_name, location_id, manager_id) VALUES (1, 'Administration', 1, NULL);
INSERT INTO departments (department_id, department_name, location_id, manager_id) VALUES (2, 'Marketing', 2, NULL);
INSERT INTO departments (department_id, department_name, location_id, manager_id) VALUES (3, 'IT', 3, NULL);
INSERT INTO departments (department_id, department_name, location_id, manager_id) VALUES (4, 'Sales', 4, NULL);
INSERT INTO departments (department_id, department_name, location_id, manager_id) VALUES (5, 'Engineering', 5, NULL);

-- Insert Employees (managers)
INSERT INTO employees (email, first_name, last_name, hire_date, phone_number, salary, department_id) VALUES ('jennifer@example.com', 'Jennifer', 'Whalen', '2003-09-17', '515-123-4444', 4400, 1);
INSERT INTO employees (email, first_name, last_name, hire_date, phone_number, salary, department_id) VALUES ('michael@example.com', 'Michael', 'Hartstein', '2004-02-17', '515-123-5555', 13000, 2);
INSERT INTO employees (email, first_name, last_name, hire_date, phone_number, salary, department_id) VALUES ('alexander@example.com', 'Alexander', 'Hunold', '2006-01-03', '590-423-4567', 9000, 3);
INSERT INTO employees (email, first_name, last_name, hire_date, phone_number, salary, department_id) VALUES ('john@example.com', 'John', 'Russell', '2004-10-01', '011-44-1344', 14000, 4);
INSERT INTO employees (email, first_name, last_name, hire_date, phone_number, salary, department_id) VALUES ('karen@example.com', 'Karen', 'Partners', '2005-01-05', '515-123-8888', 13500, 5);

-- Update departments with managers
UPDATE departments SET manager_id = 1 WHERE department_id = 1;
UPDATE departments SET manager_id = 2 WHERE department_id = 2;
UPDATE departments SET manager_id = 3 WHERE department_id = 3;
UPDATE departments SET manager_id = 4 WHERE department_id = 4;
UPDATE departments SET manager_id = 5 WHERE department_id = 5;
