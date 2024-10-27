import csv
from mrjob.job import MRJob
from mrjob.step import MRStep

class ProductProfitability(MRJob):

    def configure_args(self):
        super(ProductProfitability, self).configure_args()
        self.add_passthru_arg(
            '--products', type=str, help='--products = products.csv'
        )

    def load_products(self):
        products = {}
        with open(self.options.products, mode='r', newline='', encoding='utf-8') as csvfile:
            reader = csv.DictReader(csvfile)
            for row in reader:
                products[row['ProductID']] = (row['ProductName'], row['ProductCategory'])
        return products

    def mapper_init(self):
        # Load products for use in the mapper
        self.products = self.load_products()

    def mapper(self, _, line):
        """Process transactions and join with product data"""
        parts = line.strip().split(',')
        if parts[0] != 'TransactionID':  # Skip header
            product_id = parts[3]
            revenue_generated = float(parts[5])

            if product_id in self.products:
                product_name, product_category = self.products[product_id]
                yield (product_category, product_id, product_name), revenue_generated

    def reducer_sum_revenue(self, key, values):
        """Aggregate total revenue per product"""
        total_revenue = sum(values)
        yield key, total_revenue  # Yielding key and total revenue

    def reducer_find_top3(self, category_product_info, total_revenues):
        """Find top 3 products by total revenue for each category"""
        # Accumulate product revenues in a list
        product_revenue_list = []

        # Collect total revenues into the list
        for total_revenue in total_revenues:
            product_revenue_list.append((category_product_info, total_revenue))

        # Sort by revenue in descending order
        sorted_products = sorted(product_revenue_list, key=lambda x: -x[1])

        # Yield only the top 3 products
        top_n = 3  # Limit to top 3 products
        for product_info, revenue in sorted_products[:top_n]:
            yield category_product_info[0], (product_info[1], revenue)  # yield category and (product name, revenue)

    def steps(self):
        return [
            MRStep(mapper_init=self.mapper_init,
                   mapper=self.mapper,
                   reducer=self.reducer_sum_revenue),
            MRStep(reducer=self.reducer_find_top3)
        ]

if __name__ == '__main__':
    ProductProfitability.run()