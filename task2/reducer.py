#!/usr/bin/env python3
import sys

current_category = None
interaction_count = 0
purchase_count = 0

for line in sys.stdin:
    category, flag = line.strip().split('\t')

    if current_category and current_category != category:
        # Calculate and output conversion rate for the previous category
        conversion_rate = (purchase_count / interaction_count) if interaction_count > 0 else 0
        print(f"{current_category}\t{conversion_rate:.4f}")

        # Reset counters for new category
        interaction_count = 0
        purchase_count = 0

    current_category = category
    if flag == "interaction":
        interaction_count += 1
    elif flag == "purchase":
        purchase_count += 1

# Output last category
if current_category:
    conversion_rate = (purchase_count / interaction_count) if interaction_count > 0 else 0
    print(f"{current_category}\t{conversion_rate:.4f}")