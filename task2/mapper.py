#!/usr/bin/env python3
import sys
import csv

# Load product category mappings from products.csv
product_category_map = {}

with open('input/products.csv', 'r') as file:
    reader = csv.reader(file)
    next(reader)  # Skip header
    for row in reader:
        product_id, product_name, category, price = row
        product_category_map[product_id] = category

# Process user_activity.csv and transactions.csv for MapReduce
for line in sys.stdin:
    fields = line.strip().split(',')
    if len(fields) == 5:  # user_activity.csv format
        product_id = fields[3]
        category = product_category_map.get(product_id, "Unknown")
        print(f"{category}\tinteraction")
    elif len(fields) == 7:  # transactions.csv format
        product_id = fields[3]
        category = product_category_map.get(product_id, "Unknown")
        print(f"{category}\tpurchase")
