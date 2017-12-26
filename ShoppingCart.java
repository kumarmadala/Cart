
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Item {

	private String description;
	private float price;

	public Item(String description, float price) {
		super();
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}
}

/*
 * represents an order line which contains the @link Item and the quantity.
 */
class OrderLine {

	private int quantity;
	private Item item;

	/*
	 * @param item Item of the order
	 * 
	 * @param quantity Quantity of the item
	 */
	public OrderLine(Item item, int quantity) throws Exception {
		if (item == null) {
			System.err.println("ERROR - Item is NULL");
			throw new Exception("Item is NULL");
		}
		assert quantity > 0;
		this.item = item;
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}

class Order {

	public Order() {
		orderLines = new ArrayList<OrderLine>();
	}

	private List<OrderLine> orderLines;

	public void add(OrderLine o) throws Exception {
		if (o == null) {
			System.err.println("ERROR - Order is NULL");
			throw new IllegalArgumentException("Order is NULL");
		}
		orderLines.add(o);
	}

	public int size() {
		return orderLines.size();
	}

	public OrderLine get(int i) {
		return orderLines.get(i);
	}

	public void clear() {
		this.orderLines.clear();
	}
}

class calculator {

	public static double format(double value) {
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		return bd.doubleValue();
	}

	/**
	 * receives a collection of orders. For each order, iterates on the order
	 * lines and calculate the total price which is the item's price * quantity
	 * * taxes.
	 * 
	 * For each order, print the total Sales Tax paid and Total price without
	 * taxes for this order
	 */
	public void calculate(Map<String, Order> o) {

		double grandtotal = 0;

		// Iterate through the orders
		for (Map.Entry<String, Order> entry : o.entrySet()) {
			System.out.println("*******" + entry.getKey() + "*******");

			Order r = entry.getValue();

			double totalTax = 0;
			double total = 0;

			// Iterate through the items in the order
			for (int i = 0; i < r.size(); i++) {

				// Calculate the taxes
				double tax = 0;

				if (r.get(i).getItem().getDescription().toLowerCase()
						.contains("imported")) {
					tax = format(r.get(i).getItem().getPrice() * 0.15); // Extra
																		// 5%
																		// tax
																		// on
					// imported items
				} else {
					tax = format(r.get(i).getItem().getPrice() * 0.10);
				}

				// Calculate the total price
				double totalprice = r.get(i).getItem().getPrice() + format(tax);

				// Print out the item's quantity, description and total price
				System.out.println(r.get(i).getQuantity() + " "
						+ r.get(i).getItem().getDescription() + ": "
						+ format(totalprice));

				// Keep a running total
				totalTax += tax;
				total += r.get(i).getItem().getPrice();
			}

			// Print out the total taxes
			System.out.println("Sales Tax: " + format(totalTax));

			// Print out the total amount
			System.out.println("Total: " + format(total * 100) / 100);
			grandtotal += total;
		}

		System.out.println("Sum of orders: " + format(grandtotal * 100) / 100);
	}
}

public class ShoppingCart {

	public static void main(String[] args) throws Exception {

		Map<String, Order> o = new LinkedHashMap<String, Order>();// LinkedHashMap to preserve the order

		Order c = new Order();

		c.add(new OrderLine(new Item("book", (float) 12.49), 1));
		c.add(new OrderLine(new Item("music CD", (float) 14.99), 1));
		c.add(new OrderLine(new Item("chocolate bar", (float) 0.85), 1));

		o.put("Order 1", c);
		
		c=new Order();

		c.add(new OrderLine(new Item("imported box of chocolate", 10), 1));
		c.add(new OrderLine(new Item("imported bottle of perfume",
				(float) 47.50), 1));

		o.put("Order 2", c);
		
		c=new Order();

		c.add(new OrderLine(new Item("Imported bottle of perfume",
				(float) 27.99), 1));
		c.add(new OrderLine(new Item("bottle of perfume", (float) 18.99), 1));
		c.add(new OrderLine(
				new Item("packet of headache pills", (float) 9.75), 1));
		c.add(new OrderLine(new Item("box of imported chocolates",
				(float) 11.25), 1));

		o.put("Order 3", c);

		new calculator().calculate(o);

	}
}
