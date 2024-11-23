insert into fleet_management.delivery_points (id, name)
values (1, 'Branch');
insert into fleet_management.delivery_points (id, name)
values (2, 'Distribution Centre');
insert into fleet_management.delivery_points (id, name)
values (3, 'Transfer Centre');

insert into fleet_management.packages (desi, status, delivery_point_id, barcode, sack_barcode)
values (5, 1, 1, 'P7988000121', ''),
       (5, 1, 1, 'P7988000122', ''),
       (9, 1, 1, 'P7988000123', ''),
       (33, 1, 2, 'P8988000120', ''),
       (17, 1, 2, 'P8988000121', ''),
       (26, 1, 2, 'P8988000122', 'C725799'),
       (35, 1, 2, 'P8988000123', ''),
       (1, 1, 2, 'P8988000124', ''),
       (200, 1, 2, 'P8988000125', ''),
       (50, 1, 2, 'P8988000126', 'C725799'),
       (15, 1, 3, 'P9988000126', ''),
       (16, 1, 3, 'P9988000127', ''),
       (55, 1, 3, 'P9988000128', 'C725800'),
       (28, 1, 3, 'P9988000129', 'C725800'),
       (17, 1, 3, 'P9988000130', '');

insert into fleet_management.sacks (status, delivery_point_id, barcode)
values (1,2,'C725799'),
       (1,3,'C725800');
