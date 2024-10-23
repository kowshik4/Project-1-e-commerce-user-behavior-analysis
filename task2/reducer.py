#!/usr/bin/env python
import sys

current_product_id = None
interaction_count = 0
purchase_count = 0

# Read from standard input
for line in sys.stdin:
    product_id, count_type = line.strip().split("\t")
    
    if current_product_id == product_id:
        if count_type == "interaction":
            interaction_count += 1
        elif count_type == "purchase":
            purchase_count += 1
    else:
        if current_product_id:
            conversion_rate = purchase_count / interaction_count if interaction_count > 0 else 0
            print(f"{current_product_id}\t{conversion_rate:.2f}")
        
        current_product_id = product_id
        interaction_count = 1 if count_type == "interaction" else 0
        purchase_count = 1 if count_type == "purchase" else 0

# Output the last product
if current_product_id:
    conversion_rate = purchase_count / interaction_count if interaction_count > 0 else 0
    print(f"{current_product_id}\t{conversion_rate:.2f}")