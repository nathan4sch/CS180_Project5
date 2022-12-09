<h2> Manage Catalogue Frame Tests </h2>
<h6>For each test, it is assumed that the Seller owns at least one store.</h6>

<h3>Test 1: Create new product</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller inputs the desired
   name, description, quantity and price of
   the desired item into their respective
   fields, and selects the "Add Product"
   option.
</ol>

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Passed.
<br><br>

<h3>Test 2: Edit product</h3>
Steps:
<h6>If there is at least one item in the store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the desired product from the list of
   products in the store.
<li> The Seller modifies one of the fields.
<li> The Seller selects the "Edit Selected Item" option.
</ol>

Expected result: "Product Successfully Changed" JOptionPane
is displayed, and the field/s edited are modified.

Test Status: Passed.
<br><br>

<h3>Test 3: Delete product</h3>
Steps:
<h6>If there is at least one item in the store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the desired product from the list of
   products in the store.
<li> The Seller selects the "Delete Selected Item" option.
</ol>

Expected result: "Product Deleted" JOptionPane is displayed,
and the product is deleted from the store.

Test Status: Passed.
<br><br>

<h3>Test 4: Export Product File</h3>
Steps:

<h6>If there is at least one
item in the store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the "Export Product File" option.
</ol>

Expected Result: "File Exported" JOptionPane is displayed,
and new file is created in the main folder of the project
titled "storeName—Items.csv" that contains the String
representation of all the items in the store.

Test Status: Passed.
<br><br>

<h3>Test 5: Return to Dashboard</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
would like to manage, and then selects the
"Modify Products" option.
<li> The Seller selects the "Return to Dashboard" option.
</ol>

Expected result: The program returns to the Main Seller Dashboard.

Test Status: Passed.
<br><br>

<h3>Test 6: Create new product</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the "Add Product"
   option.
</ol>

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Failed; "Missing Input in Text Fields" JOptionPane is displayed.
<br><br>

<h3>Test 7: Create new product</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller inputs the desired
   name, description, an invalid quantity and price of
   the desired item into their respective
   fields, and selects the "Add Product"
   option.
</ol>

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Failed; "Inputted Quantity must be a Positive Integer" JOptionPane 
is displayed.
<br><br>

<h3>Test 8: Create new product</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller inputs the desired
   name, description, an invalid quantity and price of
   the desired item into their respective
   fields, and selects the "Add Product"
   option.
</ol>

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Failed; "Inputted Quantity must be a Positive Integer" JOptionPane
is displayed.
<br><br>

<h3>Test 9: Create new product</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller inputs the desired
   name, description, quantity and an invalid price of
   the desired item into their respective
   fields, and selects the "Add Product"
   option.
</ol>

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Failed; "Inputted Price must be Positive and have Two 
Decimal Places" JOptionPane is displayed.
<br><br>

<h3>Test 10: Edit product</h3>
Steps:
<h6>If there is at least one item in the store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the desired product from the list of
   products in the store.
<li> The Seller changes one of the fields: name, description, quantity, or price
<li> The Seller selects the "Edit Selected Item" option.
</ol>

Expected result: "Product Successfully Changed" JOptionPane
is displayed, and the field/s edited are modified.

Test Status: Failed; "Change one of the Input Fields" JOptionPane is displayed.
<br><br>

<h3>Test 10: Edit product</h3>
Steps:
<h6>If there is at least one item in the store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the desired product from the list of
   products in the store.
<li> The Seller selects the "Edit Selected Item" option.
</ol>

Expected result: "Product Successfully Changed" JOptionPane
is displayed, and the field/s edited are modified.

Test Status: Failed; "Change one of the Input Fields" JOptionPane is displayed.
<br><br>

<h3>Test 11: Delete product</h3>
Steps:
<h6>If there is at least one item in the store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the "Delete Selected Item" option.
</ol>

Expected result: "Product Deleted" JOptionPane is displayed,
and the product is deleted from the store.

Test Status: Failed; "No Item Selected" JOptionPane is displayed.
<br><br>

<h3>Test 4: Export Product File</h3>
Steps:

<h6>If there are no items in the store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the "Export Product File" option.
</ol>

Expected Result: "File Exported" JOptionPane is displayed,
and new file is created in the main folder of the project
titled "storeName—Items.csv" that contains the String
representation of all the items in the store.

Test Status: Failed; "You have no products in this store" JOptionPane is displayed.
<br><br>

