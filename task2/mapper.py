#!/usr/bin/env python
import sys
import csv

# Read from standard input
for line in sys.stdin:
    fields = line.strip().split(",")
    
    if len(fields) == 5 and fields[1] in ['AddToCart', 'Browse']:
        # Emit interaction
        product_id = fields[3]
        print(f"{product_id}\tinteraction")
    
    elif len(fields) == 7:  # Assuming this structure for transactions
        product_id = fields[3]
        print(f"{product_id}\tpurchase")