create table if not exists `organization`
(
id bigint not null auto_increment,
org_id bigint not null,
org_name varchar(255),
domain varchar(255),
org_token varchar(255),
org_type varchar(255),

channel varchar(255),
shop_name varchar(255),

provider varchar(255),
business_phone varchar(255),
waba_id varchar(255),
primary key (id)
) engine=InnoDB;
