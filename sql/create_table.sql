CREATE TABLE `dbattendance_client_1_0`.`attendance` (
  `attendance_id` BIGINT NOT NULL AUTO_INCREMENT,
  `machine_id` VARCHAR(30) NOT NULL,
  `pin` VARCHAR(30) NOT NULL,
  `attendance_date_time` TIMESTAMP NULL,
  `verified` INT(1) NULL,
  `status` INT(1) NULL,
  `work_code` INT(1) NULL,
  `is_sent` char(1) NOT NULL DEFAULT 'F',
  `sent_counter` INT(3) NULL,
  PRIMARY KEY (`attendance_id`));
  
  CREATE TABLE `dbattendance_client_1_0`.`attendance_history` (
  `attendance_id` BIGINT NOT NULL,
  `machine_id` VARCHAR(30) NOT NULL,
  `pin` VARCHAR(30) NOT NULL,
  `attendance_date_time` TIMESTAMP NULL,
  `verified` INT(1) NULL,
  `status` INT(1) NULL,
  `work_code` INT(1) NULL,
  `is_sent` char(1) NULL,
  `sent_counter` INT(3) NULL,
  PRIMARY KEY (`attendance_id`));
  
  
  