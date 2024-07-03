create table if not exists `user`
(
id bigint not null auto_increment,
org_id bigint not null,
user_id bigint not null,

email varchar(255),
mobile varchar(255),
first_name varchar(255),
last_name varchar(255),
full_name varchar(255),

is_main_user bit,
organization_id varchar(255),
is_wigzo_admin varchar(255),
is_disabled varchar(255),

sr_email_exists bit,
sr_email_user_id varchar(255),
sr_main_user bit,
sr_all_permissions bit,
sr_mobile_exists bit,
sr_mobile_user_id varchar(255),
email_mobile_mismatch bit,

sr_company_id bigint,

is_engage_enabled bit,
engage_marketing_enabled bit,
engage_shop_name varchar(255),
engage_provider varchar(255),
engage_business_phone varchar(255),

error_data varchar(255),

primary key (id)
) engine=InnoDB;
