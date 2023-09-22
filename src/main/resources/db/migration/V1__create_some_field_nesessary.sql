ALTER TABLE `employee`.`employee`
    ADD COLUMN `joining_date` DATE NULL AFTER `department_code`,
    ADD COLUMN `designation` VARCHAR(255) NULL AFTER `joining_date`,
    ADD COLUMN `quarter` VARCHAR(255) NULL AFTER `designation`,
    ADD COLUMN `avatar` VARCHAR(255) NULL AFTER `quarter`,
    ADD COLUMN `salary` DOUBLE NULL AFTER `avatar`;