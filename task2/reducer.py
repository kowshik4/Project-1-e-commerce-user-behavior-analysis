#!/usr/bin/env python3

import sys

current_product = None
interaction_count = 0
purchase_count = 0

# Read from standard input
for line in sys.stdin:
    line = line.strip()
    product_id, interaction_type = line.split('\t')
    
    if current_product == product_id:
        if interaction_type == "Interaction":
            interaction_count += 1
        elif interaction_type == "Purchase":
            purchase_count += 1
    else:
        if current_product is not None:
            if interaction_count > 0:
                conversion_rate = purchase_count / interaction_count
                print(f"{current_product}\t{conversion_rate:.4f}")
        
        # Reset for new product
        current_product = product_id
        interaction_count = 1 if interaction_type == "Interaction" else 0
        purchase_count = 1 if interaction_type == "Purchase" else 0

# Output the last product
if current_product is not None:
    if interaction_count > 0:
        conversion_rate = purchase_count / interaction_count
        print(f"{current_product}\t{conversion_rate:.4f}")