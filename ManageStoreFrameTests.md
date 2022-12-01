<h2> Manage Store Frame Tests </h2>

<h3>Test 1: Create new product</h3>
Steps:
<h6>If the Seller owns a store:</h6>
1. Seller launches application and logs in.
2. From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
3. The Seller selects the "Manage
   Catalogue" option.
4. The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
5. The Seller inputs the desired
   name, description, quantity and price of
   the desired item into their respective
   fields, and selects the "Add Product"
   option.

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Passed.
<br><br>

<h3>Test 2: Edit product</h3>
Steps:
<h6>If the Seller owns a store and there is at least one 
item in the store:</h6>
1. Seller launches application and logs in.
2. From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
3. The Seller selects the "Manage
   Catalogue" option.
4. The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
5. The Seller selects the desired product from the list of
products in the store.
6. The Seller modifies one of the fields.
7. The Seller selects the "Edit Selected Item" option.

Expected result: "Product Successfully Changed" JOptionPane
is displayed, and the field/s edited are modified.

Test Status: Passed.
<br><br>

<h3>Test 3: Delete product</h3>
Steps:
<h6>If the Seller owns a store and there is at least one
item in the store:</h6>
1. Seller launches application and logs in.
2. From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
3. The Seller selects the "Manage
   Catalogue" option.
4. The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
5. The Seller selects the desired product from the list of
   products in the store.
6. The Seller selects the "Delete Selected Item" option.

Expected result: "Product Deleted" JOptionPane is displayed,
and the product is deleted from the store.

Test Status: Passed.

<h3>Test 4: Export Product File</h3>
Steps:
<h6>If the Seller owns a store and there is at least one
item in the store:</h6>
1. Seller launches application and logs in.
2. From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
3. The Seller selects the "Manage
   Catalogue" option.
4. The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
5. The Seller selects the "Export Product File" option.

Expected Result: "File Exported" JOptionPane is displayed,
and new file is created in the main folder of the project
titled "storeName—Items.csv" that contains the String 
representation of all the items in the store.

Test Status: Passed.