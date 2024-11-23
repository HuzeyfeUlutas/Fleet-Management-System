insert into fleet_management.delivery_points (id, name)
values (1, 'Branch');
insert into fleet_management.delivery_points (id, name)
values (2, 'Distribution Centre');
insert into fleet_management.delivery_points (id, name)
values (3, 'Transfer Centre');

INSERT INTO fleet_management.sacks (status, delivery_point_id, barcode)
VALUES (1, 2, 'C725799');

INSERT INTO fleet_management.sacks (status, delivery_point_id, barcode)
VALUES (1, 3, 'C725800');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (5, 1, 1, 'P7988000121', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (5, 1, 1, 'P7988000122', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (9, 1, 1, 'P7988000123', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (33, 1, 2, 'P8988000120', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (17, 1, 2, 'P8988000121', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (26, 1, 2, 'P8988000122', 'C725799');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (35, 1, 2, 'P8988000123', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (1, 1, 2, 'P8988000124', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (200, 1, 2, 'P8988000125', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (50, 1, 2, 'P8988000126', 'C725799');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (15, 1, 3, 'P9988000126', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (16, 1, 3, 'P9988000127', '');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (55, 1, 3, 'P9988000128', 'C725800');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (28, 1, 3, 'P9988000129', 'C725800');

INSERT INTO fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
VALUES (17, 1, 3, 'P9988000130', '');