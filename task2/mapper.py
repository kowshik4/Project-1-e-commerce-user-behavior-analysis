#!/usr/bin/env python3

import sys
import csv

# Initialize CSV reader for standard input
input_reader = csv.reader(sys.stdin)

# Read each line from standard input
for row in input_reader:
    if len(row) == 5:  # User activity log format: LogID, UserID, ActivityType, ProductID, ActivityTimestamp
        # Assuming the format of user_activity.csv
        log_id, user_id, activity_type, product_id, activity_timestamp = row
        # Emit (ProductID, Interaction) for user activities
        print(f"{product_id}\tInteraction")
        
    elif len(row) == 7:  # Transaction log format: TransactionID, UserID, ProductCategory, ProductID, QuantitySold, RevenueGenerated, TransactionTimestamp
        # Assuming the format of transactions.csv
        transaction_id, user_id, product_category, product_id, quantity_sold, revenue_generated, transaction_timestamp = row
        # Emit (ProductID, Purchase) for transactions
        print(f"{product_id}\tPurchase")