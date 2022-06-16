USE `supermarket`;


-- CREATING TABLE USERS

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `accountID` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `role` enum('admin','employee') NOT NULL,
  `email` VARCHAR(45) NULL,
  `phone` VARCHAR(20) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`accountID`),
  UNIQUE INDEX `accountID_UNIQUE` (`accountID` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);
  
-- POPULATING THE TABLE WITH SAMPLE DATA

INSERT INTO `users`(`accountID`,`firstname`,`lastname`,`role`,`email`,`phone`,`username`,`password`) VALUES
(1, 'John', 'Doe', 'admin','john_doe@mail.com','+359 88 1111 2222', 'admin', 'admin'),
(2, 'Jane', 'Doe', 'employee','janejane@mail.com','+359 88 1234 1234', 'jane', '123456'),
(3, 'Richard', 'Roe', 'employee','rich00@gmail.com','+359 89 4343 8787', 'richie', '0000');


-- CREATING TABLE USERS

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_ID` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(45) NOT NULL,
  `product_category` ENUM('fruit', 'vegetable') NOT NULL,
  `product_price` DOUBLE(4,2) NOT NULL,
  `image_URL` VARCHAR(200) NULL,
  PRIMARY KEY (`product_ID`),
  UNIQUE INDEX `product_ID_UNIQUE` (`product_ID` ASC) VISIBLE,
  UNIQUE INDEX `product_name_UNIQUE` (`product_name` ASC) VISIBLE);
  
  

-- CREATING TABLE RECENT_ACTIVITY

DROP TABLE IF EXISTS `recent_activity`;
CREATE TABLE IF NOT EXISTS `recent_activity` (
  `activity_id` INT NOT NULL AUTO_INCREMENT,
  `activity_datetime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `activity_message` VARCHAR(200) NULL,
  PRIMARY KEY (`activity_id`),
  UNIQUE INDEX `activity_id_UNIQUE` (`activity_id` ASC) VISIBLE);
  




