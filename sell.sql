create table `product_info` (
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment 'name of product',
    `product_price` decimal(8,2) not null comment 'price',
    `product_stock` int not null comment 'stock',
    `product_description` varchar(64) comment 'description',
    `product_icon` varchar(512) comment 'icon',
    `product_status` tinyint(3) DEFAULT '0' COMMENT 'on sale:0, offsale:1',
    `category_type` int not null comment 'type',
    `create_time` timestamp not null default current_timestamp comment 'create time',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (`product_id`)
);

create table `product_category` (
    `category_id` int not null auto_increment,
    `category_name` varchar(64) not null comment 'category name',
    `category_type` int not null comment 'type',
    `create_time` timestamp not null default current_timestamp comment 'create time',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (`category_id`)
    unique key 'uqe_category_type' ('category_type')
);

create table `order_master` (
    `order_id` varchar(32) not null,
    `buyer_name` varchar(32) not null comment 'name of buyer',
    `buyer_phone` varchar(32) not null comment 'phone of buyer',
    `buyer_address` varchar(128) not null comment 'address of buyer',
    `buyer_openid` varchar(64) not null comment 'openid of buyer',
    `order_amount` decimal(8,2) not null comment 'total amount of buyer',
    `order_status` tinyint(3) not null default '0' comment 'status of order',
    `pay_status` tinyint(3) not null default '0' comment 'payment status',
    `create_time` timestamp not null default current_timestamp comment 'create time',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (`order_id`),
    key `idx_buyer_openid` (`buyer_openid`)
);

create table `order_detail` (
    `detail_id` varchar(32) not null,
    `order_id` varchar(32) not null,
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment 'product name',
    `product_price` decimal(8,2) not null comment 'price',
    `product_quantity` int not null comment 'amount',
    `product_icon` varchar(512) comment 'icon',
    `create_time` timestamp not null default current_timestamp comment 'create time',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (`detail_id`),
    key `idx_order_id` (`order_id`)
);

-- Seller(Used to login the backend administration system)
create table `seller_info` (
    `id` varchar(32) not null,
    `username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null comment 'openid of wechat',
    `create_time` timestamp not null default current_timestamp comment 'create time',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (`id`)
) comment 'Information of Seller';