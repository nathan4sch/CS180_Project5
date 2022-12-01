<h2> Manage Store Frame Tests </h2>

<h3>Test 1: Modify Products</h3>
Steps:
<h6>If the Seller owns at least one store:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Manage
   Catalogue" option.
<li> The Seller selects the store that they
would like to manage, and then selects the
"Modify Products" option.
</ol>

Expected result: The Manage Catalogue frame is displayed.

Test Status: passed
<br><br>

<h3>Test 2: Import Product File</h3>
Steps:
<h6>If the Seller owns at least one store:</h6>

<ol>
<li> User creates a csv file with items formatted the following:
storeName,itemName,description,quantity,price
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Manage
   Catalogue" option.
<li> The Seller selects a store, and then selects the "Import Product File" 
option.
<li> The Seller types the filename in the text box displayed.
</ol>

Expected result: JOptionPane with the number of items added displayed, and 
items added to the store. If the Seller selects the store, they will be able 
to see the newly imported items.

Test Status: Passed.
<br><br>

<h3>Test 3: Delete Store</h3>
Steps:
<h6>If the Seller owns at least one store:</h6>

<ol>
<li> User creates a csv file with items formatted the following:
storeName,itemName,description,quantity,price
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Manage
   Catalogue" option.
<li> The Seller selects a store, and then selects the "Delete Store" option.
</ol>

Expected result: "Store Deleted" JOptionPane is displayed and store selected
is deleted.

Test Status: Passed.
<br><br>

<h3>Test 4: Return to Dashboard</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Manage
   Catalogue" option.
<li> The Seller selects the "Return to Dashboard" option.
</ol>

Expected result: The program returns to the Main Seller Dashboard.

Test Status: Passed.