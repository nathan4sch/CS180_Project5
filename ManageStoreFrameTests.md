<h2> Manage Store Frame Tests </h2>

<h3>Test 1: Modify Products</h3>
Steps:
<h6>If the Seller owns at least one store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
would like to manage, and then selects the
"Modify Products" option.
</ol>

Expected result: The Manage Catalogue frame is displayed.

Test Status: Passed.
<br><br>

<h3>Test 2: Modify Products</h3>
Steps:
<h6>If the Seller owns at least one store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the
"Modify Products" option.
</ol>

Expected result: The Manage Catalogue frame is displayed.

Test Status: Failed; "No Store Selected" JOptionPane is displayed.
<br><br>

<h3>Test 3: Import Product File</h3>
Steps:
<h6>If the Seller owns at least one store:</h6>

<ol>
<li> User creates a csv file with items formatted the following:
storeName,itemName,description,quantity,price
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects a store, and then selects the "Import Product File" 
option.
<li> The Seller types the filename in the text box displayed.
</ol>

Expected result: JOptionPane with the number of items added displayed, and 
items added to the store. If the Seller selects the store, they will be able 
to see the newly imported items.

Test Status: Passed.
<br><br>

<h3>Test 4: Delete Store</h3>
Steps:
<h6>If the Seller owns at least one store:</h6>

<ol>
<li> User creates a csv file with items formatted the following:
storeName,itemName,description,quantity,price
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects a store, and then selects the "Delete Store" option.
</ol>

Expected result: "Store Deleted" JOptionPane is displayed and store selected
is deleted.

Test Status: Passed.
<br><br>

<h3>Test 5: Return to Dashboard</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Return to Dashboard" option.
</ol>

Expected result: The program returns to the Main Seller Dashboard.

Test Status: Passed.
<br><br>

<h3>Test 6: Statistics of Store</h3>
Steps:
<h6> If there is at least one store that had sales</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects store they would like to see, and then selects
the "Statistics of Store" option.
<li> The Seller selects the type of statistics the would like to see.
</ol>

Expected Result: The desired Statistics of Store are displayed

Test Status: Passed.
<br><br>

<h3>Test 7: Import Product File</h3>
Steps:
<h6>If the Seller owns at least one store, if the product file to import does
not exist, or if no store is selected</h6>

<ol>
<li> User creates a csv file with items formatted the following:
storeName,itemName,description,quantity,price
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects a store, and then selects the "Import Product File" 
option.
<li> The Seller types the filename in the text box displayed.
</ol>

Expected result: JOptionPane with the number of items added displayed, and
items added to the store. If the Seller selects the store, they will be able
to see the newly imported items.

Test Status: Failed; "No Products were Added" JOptionPane is displayed.
<br><br>

<h3>Test 8: Statistics of Store</h3>
Steps:
<h6>If the store selected has no sales yet</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Statistics of Store" option.
<li> The Seller selects the type of statistics the would like to see.
</ol>

Expected Result: The desired Statistics of Store are displayed.

Test Status: Failed; "No Statistics" JOptionPane is displayed.
<br><br>

<h3>Test 9: Statistics of Store</h3>
Steps:
<h6>If no store is selected</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Statistics of Store" option.
</ol>

Expected Result: The Statistics selector is displayed.

Test Status: Failed; "No Store Selected" JOptionPane is displayed.
<br><br>

<h3>Test 10: View Sales</h3>
Steps:
<h6> If there is at least one store that had sales</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Sales List" option.
</ol>

Expected Result: The Sales are displayed.

Test Status: Passed.
<br><br>

<h3>Test 10: View Sales</h3>
Steps:
<h6> If the store selected had no sales</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Sales List" option.
</ol>

Expected Result: The Sales are displayed.

Test Status: Failed; "No Sales" JOptionPane is displayed.
<br><br>

<h3>Test 10: View Sales</h3>
Steps:
<h6> If no store is selected</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Sales List" option.
</ol>

Expected Result: The Sales are displayed.

Test Status: Failed; "No Store Selected" JOptionPane is displayed.
<br><br>

