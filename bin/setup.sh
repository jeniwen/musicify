#!/bin/bash
db2 connect to cs421;
db2 -t < ../sql/create_tables.sql;
cd ../data
db2 -t < ../sql/import_data.sql;
cd ../src
cd src
db2 -t < ../sql/inserts.sql;
db2 -t < ../sql/inserts2.sql;
