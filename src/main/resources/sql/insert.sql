INSERT INTO users (id, user_name, user_password, user_email, user_state)
VALUES (1, 'Admin', '$2a$10$m0puVcm.XzTvSQ31uajEYumc73fIDaGJG3/RTFPpDMZ3BQYtmMnrG', 'admin@gmail.com', 0);
INSERT INTO users (id, user_name, user_password, user_email, user_state)
VALUES (2, 'John Doe', '$2a$10$gPCFfSC7vYvN1.NOIyI7deMU1QOL6kPICmzKU4O75EJK0Akr.nBEe', 'active@gmail.com', 1);
INSERT INTO users (id, user_name, user_password, user_email, user_state)
VALUES (3, 'Dr. Evil', '$2a$10$gPCFfSC7vYvN1.NOIyI7deMU1QOL6kPICmzKU4O75EJK0Akr.nBEe', 'blocked@gmail.com', 2);

INSERT INTO categories (id, category_name, category_parent_id) VALUES (1,'Carp',NULL);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (2,'Predator',NULL);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (3,'Sea',NULL);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (4,'Reels',NULL);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (5,'Rods',NULL);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (6,'Carp Fishing Accessories',1);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (7,'Carp Fishing Baits',1);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (8,'Carp Fishing Hooks',1);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (9,'Predator Fishing Accessories',2);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (10,'Lures',2);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (11,'Pike Fishing Floats',2);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (12,'Sea Fishing Accessories',3);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (13,'Sea Fishing Hooks',3);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (14,'Sea Fishing Lures and Spinners',3);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (15,'Baitcasting Reels',4);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (16,'Baitrunner/Freespin Reels',4);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (17,'Closed Face Reels',4);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (18,'Bass Rods',5);
INSERT INTO categories (id, category_name, category_parent_id) VALUES (19,'Boat Rods',5);

INSERT INTO manufacturers (id, manufacturer_name) VALUES (1,'Daiwa');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (2,'Yo-zuri');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (3,'Salmo');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (4,'Lucky John');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (5,'Shimano');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (6,'Shakespeare');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (7,'Drennan');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (8,'Avid Carp');
INSERT INTO manufacturers (id, manufacturer_name) VALUES (9,'Fox');

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (1,'Avid Carp Overnighter Yard Sticks', 16.69, TRUE
, 'The sticks are supplied with a 12ft cord', 6, 8);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (2,'Fox Bait and Glug Tubs LARGE', 13.99, TRUE
, 'Designed for secure storage of all hook baits', 6, 9);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (3,'Cygnet Splash Mats', 14.99, TRUE
, 'Serving a plethora of purposes', 6, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (4,'Avid Carp CORN Stops', 2.75, TRUE
, '', 7, 8);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (5,'Avid Carp MAGGOT Stops', 2.75, TRUE
, 'Available in a variety of different colours', 7, 8);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (6,'Avid Carp MAIZE Stops', 2.75, TRUE
, 'Bright colours to attract fish', 7, 8);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (7,'Drennan Carp BARBLESS Spade HOOKS', 1.19, TRUE
, 'Each pack contains 10 Barbless Carp Spade Hooks', 8, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (8,'Drennan Carp Feeder BARBLESS Spade HOOKS', 1.19, FALSE
, 'Each pack contains 10 Barbless Spade Carp Feeder Hooks', 8, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (9,'Fox Edges Armapoint Zig and Floater Hooks', 4.49, TRUE
, '', 8, 9);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (10,'Shakespeare Covert Dark Chod Hooks', 4.25, FALSE
, '', 8, 6);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (11,'Daiwa Boat Rod Holder', 7.49, TRUE
, 'Always fits system for mounting and a quick release device', 9, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (12,'Shakespeare Pocket Size Line Stripper', 7.99, TRUE
, 'Allows to remove any older lines from reel both quickly and easily', 9, 6);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (13,'Daiwa Capped Plastic Rod Tube Black', 5.99, TRUE
, 'These can easily but cut down to the size that you require', 9, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (14,'Salmo Powerbait Sandeels 12cm', 4.49, TRUE
, 'Available in three different colours', 10, 3);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (15,'Yo-Zuri Kick-S Weedless Minnows', 5.99, TRUE
, 'Two different types are available', 10, 2);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (16,'Lucky John Jell-Ignite Soft Shads', 3.95, TRUE
, 'Each pack has 3 different colours inside', 10, 4);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (17,'Salmo Black Minnow Color Box Bodies', 3.99, TRUE
, 'This pack features 4 different colours', 10, 3);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (18,'Yo-zuri Rage Dropshot Fry', 3.99, FALSE
, 'Four colour options', 10, 2);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (19,'Yo-zuri Rage Dart Head', 4.99, FALSE
, 'Available in a 1/0 size hook', 10, 2);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (20,'Yo-zuri Rage Micro Fry 4cm', 4.49, FALSE
, '10 per pack', 10, 2);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (21,'Drennan Pike Zeppler', 2.95, TRUE
, 'These are an extremely popular pike float', 11, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (22,'Drennan Pike Subfloat', 2.75, TRUE
, 'Ideal for suspending any live baits', 11, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (23,'Drennan Pikebob', 2.75, FALSE
, 'Ideal for smaller baits', 11, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (24,'British Inshore Fish Guide Chart', 2.50, TRUE
, 'A laminated illustrated guide to the sea fish founds', 12, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (25,'Drennan Meat Hooks', 2.50, TRUE
, 'Sharpened to perfection to provide a clean yet precise pierce', 13, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (26,'Drennan Uptide Extra Hooks', 2.00, TRUE
, '', 13, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (27,'Drennan B900c Crab Hook', 2.75, FALSE
, 'Specifically designed for soft peeler crab', 13, 7);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (28,'Daiwa Sea Hunter Z Boat Rods', 52.99, TRUE
, 'IFGA Rating: 12-20lb - Length: 7ft 6in - Guides: A/O', 19, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (29,'Salmo Bass Bugs 5.5cm', 6.49, TRUE
, '', 14, 3);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (30,'Salmo Chubby Darter 4cm', 5.49, TRUE
, 'One of the most effective wobblers ever created!', 14, 3);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (31,'Yo-zuri BX Minnow 10cm', 10.49, TRUE
, 'Running Depth: 0.9-1.5m', 14, 2);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (32,'Yo-zuri BX Swimmer 12cm', 12.19, FALSE
, 'Running Depth: 4 to 6 feets', 14, 2);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (33,'Yo-zuri Countdown Magnum 11cm', 12.99, FALSE
, 'Super Tough Abachi Wood Construction', 14, 2);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (34,'Shimano Citica 201', 104.99, TRUE
, 'Gear Ratio: 7,2:1', 15, 5);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (35,'Shimano Curado K', 159.99, TRUE
, 'Braking system controls spool speed for precision casting', 15, 5);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (36,'Shimano Tranx', 239.99, FALSE
, 'Super Free System', 15, 5);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (37,'Shimano Baitrunner X-AERO 10000RA', 133.99, TRUE
, 'Wider spool than traditional bait runner range', 16, 5);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (38,'Shimano Baitrunner X-AERO 6000RA', 129.99, TRUE
, 'Deeper spool for more line capacity', 16, 5);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (39,'Shimano Baitrunner ST 4000FB', 43.99, FALSE
, 'Line Capacity (mm/m): 0,25-260 / 0,30-180 / 0,35-130', 16, 5);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (40,'Daiwa Regal BR4000', 34.99, TRUE
, '5 ball bearings', 16, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (41,'Fox EOS 5000 Reel', 39.99, TRUE
, 'Gear ratio 5:1', 16, 9);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (42,'Daiwa 506 MKII', 57.99, TRUE
, 'New handle assembly is more secure', 17, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (43,'Daiwa 507 MKII', 62.99, TRUE
, 'Capacity: 100m/0.25mm', 17, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (44,'Daiwa 125M Closed Face', 68.99, TRUE
, 'Designed for float work on rivers', 17, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (45,'Daiwa D Wave Sea Bass 11ft', 43.99, TRUE
, 'Sections: 3', 18, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (46,'Daiwa Sand Storm Bass 11ft', 149.99, TRUE
, 'Casting weight: 2-4oz', 18, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (47,'Daiwa Sea Hunter Z Bass 11.6ft', 56.99, FALSE
, 'Casting Weight: 2-4oz', 18, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (48,'Daiwa Sea Hunter Z Travel Bass 11ft', 64.99, TRUE
, 'Casting Weight: 1-3oz', 18, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (49,'Daiwa Supercast Bass 11.6ft', 159.99, TRUE
, 'Casting weight: 2-4oz', 18, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (50,'Shakespeare Agility 2 Bass 11.6ft', 56.99, TRUE
, 'Lure Rating: 60-120g', 18, 6);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (51,'Shakespeare Agility 2 Expedition Bass', 61.99, FALSE
, 'Lure Rating: 60-120g', 18, 6);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (52,'Daiwa Saltiga Airportable Rods', 314.99, TRUE
, 'Max Casting Weight: 40-120g - Length: 8.5ft', 19, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (53,'Daiwa D Wave Boat 20-30lb', 31.99, FALSE
, 'Line Rating: 20-30lb', 19, 1);

INSERT INTO products (id, product_name, product_price, product_available, product_description
, product_category_id, product_manufacturer_id)
VALUES (54,'Daiwa Saltist Braid Special', 148.99, TRUE
, 'Premium carbon blanks using high grade carbon', 19, 1);

INSERT INTO orders (id, order_user_id, order_address, order_date, order_payed
, order_state, order_payment_conditions)
VALUES (1,2,'15, Yemen road, Yemen','2018-09-03 22:15:38.646',FALSE,0,1);

INSERT INTO orders_products (order_id, product_id, order_product_quantity, order_product_unit_price)
VALUES (1, 50, 1, 56.99);

INSERT INTO orders_products (order_id, product_id, order_product_quantity, order_product_unit_price)
VALUES (1, 15, 2, 5.99);