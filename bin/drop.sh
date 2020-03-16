#!/bin/bash
db2 connect to cs421;
db2 -t < ../sql/drop_tables.sql;
