BEGIN TRANSACTION;



DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL UNIQUE,
	first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,first_name,last_name, password_hash,role) VALUES ('user','Erica','Taylor','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,first_name,last_name,password_hash,role) VALUES ('stephenp','Stephen','Patrick','$2a$10$S642m1.InvXJWCKPtWxyeusjyYluV1FwoDZV3J9Uni7RI6gOiBUAC','ROLE_ADMIN');
INSERT INTO users (username,first_name,last_name,password_hash,role) VALUES ('kaitlinj','Kaitlin','Jocke','$2a$10$LEkEzoi39zbIyilGPopFPeXl88/JKRMcanRTWmnuBFhlLFsOCStNe','ROLE_ADMIN');



CREATE TABLE doctor (
doctor_id SERIAL PRIMARY KEY,
user_id int NOT NULL,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
CONSTRAINT fk_doctor_users FOREIGN KEY(user_id) REFERENCES users(user_id)
);

INSERT INTO doctor(user_id, first_name, last_name) VALUES (2,'Stephen','Patrick');
INSERT INTO doctor(user_id, first_name, last_name) VALUES (3,'Kaitlin','Jocke');


CREATE TABLE patient (
patient_id SERIAL PRIMARY KEY,
user_id int NOT NULL,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
CONSTRAINT pk_patient_users FOREIGN KEY(user_id) REFERENCES users(user_id)
);


INSERT INTO patient (user_id,first_name,last_name) VALUES (1,'Erica','Taylor');


CREATE TABLE office (
office_id SERIAL PRIMARY KEY,
phone_number VARCHAR(15) NOT NULL,
office_hours VARCHAR(15) NOT NULL,
doctors VARCHAR(225) NOT NULL,
street_address VARCHAR(225) NOT NULL,
city VARCHAR(50) NOT NULL,
state_abbreviation VARCHAR(2) NOT NULL,
zipcode VARCHAR(5)
);

INSERT INTO office (phone_number,office_hours,doctors,street_address,city,state_abbreviation,zipcode) VALUES ('(267) 123-6547','8:00am-5:00pm','Stephen Patrick','30 S 17th St Suite 217','Philadelphia','PA','19103');
INSERT INTO office (phone_number,office_hours,doctors,street_address,city,state_abbreviation,zipcode) VALUES ('(302) 188-6547','8:00am-5:00pm','Kaitlin Jocke','824 N Market St Suite 102','Wilmington','DE','19801');


CREATE TABLE review (
review_id SERIAL PRIMARY KEY,
rating int,
review_desc TEXT,
response TEXT,
office_id int NOT NULL,
CONSTRAINT fk_review_office FOREIGN KEY(office_id) REFERENCES office(office_id)
);
/*
INSERT INTO review (rating,review_desc,office_id) VALUES (1, 'Negative sample review',1);
INSERT INTO review (rating,review_desc,office_id) VALUES (5, 'Positive sample review',2);
*/
INSERT INTO review (rating,review_desc,office_id) VALUES (3, 'Great service',1);
INSERT INTO review (rating,review_desc,office_id) VALUES (2, 'Long wait for appointment',2);
INSERT INTO review (rating,review_desc,office_id) VALUES (1, 'Rude receptionist',1);
INSERT INTO review (rating,review_desc,office_id) VALUES (4, 'Clean office',2);
INSERT INTO review (rating,review_desc,response,office_id) VALUES (2, 'Never going back again','Sorry for your negative experience',2);
/*
CREATE TABLE doctor_office (
doctor_id int NOT NULL,
office_id int NOT NULL,
CONSTRAINT pk_doctor_office PRIMARY KEY(doctor_id,office_id),
CONSTRAINT fk_doctor_office_doctor FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id),
CONSTRAINT fk_doctor_office_office FOREIGN KEY(office_id) REFERENCES office(office_id)
);

INSERT INTO doctor_office (doctor_id,office_id) VALUES (1,1);
INSERT INTO doctor_office (doctor_id,office_id) VALUES (1,2);
*/
CREATE TABLE appointment (
appointment_id SERIAL PRIMARY KEY,
appointment_date VARCHAR(10),
appointment_time VARCHAR(10),
reason TEXT,
doctor_id int NOT NULL,
user_id int,
office_id int NOT NULL,
available BOOLEAN DEFAULT 'true',
CONSTRAINT fk_appointment_doctor FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id),
CONSTRAINT fk_appointment_users FOREIGN KEY(user_id) REFERENCES users(user_id),
CONSTRAINT fk_appointment_office FOREIGN KEY(office_id) REFERENCES office(office_id) 
);

INSERT INTO appointment (appointment_date,appointment_time,doctor_id,user_id,office_id,available) VALUES ('05-01-2022','8:00am',1,1,1,'false');
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-02-2022','8:00am',2,2);

INSERT INTO appointment (appointment_date,appointment_time,doctor_id,user_id,office_id,available) VALUES ('05-03-2022','8:00am',2,1,2,'false');
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,user_id,office_id,available) VALUES ('05-04-2022','8:30am',2,1,2,'false');

INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-05-2022','9:00am',2,2);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-06-2022','9:30am',2,2);

INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-16-2022','10:00am',2,2);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-16-2022','10:30am',2,2);

INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-16-2022','8:00am',2,2);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-16-2022','8:30am',2,2);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-16-2022','9:00am',2,2);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-16-2022','9:30am',2,2);

INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-05-2022','10:00am',2,2);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-05-2022','10:30am',2,2);

INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-06-2022','8:00am',1,1);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-06-2022','8:30am',1,1);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-06-2022','9:00am',1,1);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-06-2022','9:30am',1,1);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-06-2022','10:00am',1,1);
INSERT INTO appointment (appointment_date,appointment_time,doctor_id,office_id) VALUES ('05-06-2022','10:30am',1,1);



CREATE TABLE patient_appointment(
appointment_id int NOT NULL,
patient_id int NOT NULL,
CONSTRAINT fk_patient_appointment_appointment FOREIGN KEY(appointment_id) REFERENCES appointment(appointment_id),
CONSTRAINT fk_patient_appointment_patient FOREIGN KEY(patient_id) REFERENCES patient(patient_id)
);



COMMIT TRANSACTION;



