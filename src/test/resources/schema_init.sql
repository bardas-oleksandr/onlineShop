CREATE TABLE IF NOT EXISTS public.categories(
id                      INT UNSIGNED    NOT NULL AUTO_INCREMENT,
category_name           VARCHAR(50)     NOT NULL UNIQUE,
category_parent_id      INT UNSIGNED    DEFAULT NULL,
CONSTRAINT pkey_category PRIMARY KEY (id),
CONSTRAINT fkey_category_to_category FOREIGN KEY (category_parent_id) REFERENCES categories (id)
ON DELETE NO ACTION ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS public.manufacturers(
id                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,
manufacturer_name   VARCHAR(50)    NOT NULL UNIQUE,
CONSTRAINT pkey_manufacturer PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS public.products(
id                         INT UNSIGNED    NOT NULL AUTO_INCREMENT,
product_name               VARCHAR(50)     NOT NULL UNIQUE,
product_price              FLOAT           NOT NULL DEFAULT 0.0,
product_available          BOOLEAN         NOT NULL DEFAULT TRUE,
product_description        VARCHAR(255),
product_category_id        INT UNSIGNED    NOT NULL,
product_manufacturer_id    INT UNSIGNED    NOT NULL,
CONSTRAINT pkey_product PRIMARY KEY (id),
CONSTRAINT product_price_check CHECK (product_price >= 0.0),
CONSTRAINT fkey_category_to_product FOREIGN KEY (product_category_id) REFERENCES categories (id)
ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT fkey_manufacturer_to_product FOREIGN KEY (product_manufacturer_id) REFERENCES manufacturers (id)
ON DELETE NO ACTION ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS public.users(
id              INT UNSIGNED    NOT NULL AUTO_INCREMENT,
user_name       VARCHAR(20)     NOT NULL UNIQUE,
user_password   VARCHAR(20)     NOT NULL,
user_email      VARCHAR(320)    NOT NULL UNIQUE,
user_state      SMALLINT        NOT NULL,
CONSTRAINT pkey_user PRIMARY KEY (id),
CONSTRAINT user_state_check CHECK (user_state >= 0 AND user_state <= 2));

CREATE TABLE IF NOT EXISTS public.orders(
id                          INT UNSIGNED    NOT NULL AUTO_INCREMENT,
order_user_id               INT UNSIGNED    NOT NULL,
order_address               VARCHAR(255)    NOT NULL,
order_date                  TIMESTAMP       NOT NULL,
order_payed                 BOOLEAN         NOT NULL DEFAULT FALSE,
order_state                 SMALLINT        NOT NULL,
order_payment_conditions    SMALLINT        NOT NULL,
CONSTRAINT pkey_order PRIMARY KEY (id),
CONSTRAINT order_state_check CHECK (order_state >= 0 AND order_state <= 2),
CONSTRAINT order_payment_conditions_check CHECK (order_payment_conditions >= 0 AND order_payment_conditions <= 2),
CONSTRAINT fkey_user_to_order FOREIGN KEY (order_user_id) REFERENCES users (id)
ON DELETE NO ACTION ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS public.orders_products(
order_id                    INT UNSIGNED    NOT NULL,
product_id                  INT UNSIGNED    NOT NULL,
order_product_quantity      INT UNSIGNED    NOT NULL,
order_product_unit_price    FLOAT           NOT NULL,
CONSTRAINT pkey_orders_products PRIMARY KEY (order_id, product_id),
CONSTRAINT order_product_quantity_check CHECK (order_product_quantity > 0),
CONSTRAINT order_product_unit_price_check CHECK (order_product_unit_price >= 0.0),
CONSTRAINT fkey_orders FOREIGN KEY (order_id) REFERENCES orders (id)
ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT fkey_products FOREIGN KEY (product_id) REFERENCES products (id)
ON DELETE CASCADE ON UPDATE CASCADE);