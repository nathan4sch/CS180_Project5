<h2> Login Frame tests </h2>

<h3>Test 1: Create account for Seller</h3>
Steps:

<ol>
<li> User launches application
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the Seller option.
<li> User selects the "Create Account" button.
</ol>

Expected result: "Account Created"
JOptionPane is displayed, login credentials
stored into FMCredentials.csv, and Seller
dashboard loaded for the user.

Test Status: Passed.
<br><br>

<h3>Test 2: Create account for Seller</h3>
Steps:

<h6>If the email used already has an account associated with it</h6>
<ol>
<li> User launches application
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the Seller option.
<li> User selects the "Create Account" button.
</ol>

Expected result: "Account Created"
JOptionPane is displayed, login credentials
stored into FMCredentials.csv, and Seller
dashboard loaded for the user.

Test Status: Failed; "This user already owns an account" JOptionPane is displayed.
<br><br>

<h3>Test 3: Create account for Buyer</h3>
Steps:

<ol>
<li> User launches application
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the Buyer option.
<li> User selects the "Create Account" button.
</ol>

Expected result: "Account Created"
JOptionPane is displayed, login credentials
stored into FMCredentials.csv, and Seller
dashboard loaded for the user.

Test Status: Passed.
<br><br>

<h3>Test 4: Create account for Buyer</h3>
Steps:
<h6>If the email used already has an account associated with it</h6>
<ol>
<li> User launches application
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the Buyer option.
<li> User selects the "Create Account" button.
</ol>

Expected result: "Account Created"
JOptionPane is displayed, login credentials
stored into FMCredentials.csv, and Buyer
dashboard loaded for the user.

Test Status: Failed; "This user already owns an account" JOptionPane is displayed.
<br><br>

<h3>Test 5: Seller log in</h3>
Steps:

<ol>
<li> User launches application.
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the "SignIn" button.
</ol>

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Seller dashboard is loaded.

Test Status: Passed.
<br><br>

<h3>Test 6: Buyer log in</h3>
Steps:

<ol>
<li> User launches application.
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the "SignIn" button.
</ol>

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Buyer dashboard is loaded.

Test Status: Passed.
<br><br>

<h3>Test 7: Seller and Buyer log in</h3>
Steps:
<h6>If the account already exists</h6>
<ol>
<li> User launches application.
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the "SignIn" button.
</ol>

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Seller dashboard is loaded.

Test Status: Failed; "No account found or Account already logged in" JOptionPane
is displayed and user is not logged in.
<br><br>

<h3>Test 8: Seller and Buyer log in</h3>
Steps:
<h6>If the account is already logged in</h6>
<ol>
<li> User launches application.
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the "SignIn" button.
</ol>

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Buyer dashboard is loaded.

Test Status: Failed; "No account found or Account already logged in" JOptionPane
is displayed and user is not logged in.
<br><br><br>

<h2>Main Seller Frame Tests</h2>

<h3>Test 9: Create Store</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller types the name of the store
   in the "Input Store Name" text box and
   selects "Create Store".
</ol>

Expected result: Store created and
"Store Created" JOptionPane is displayed.

Test Status: Passed.
<br><br>

<h3>Test 10: Create Store</h3>
Steps:
<h6>If there already is a store with the same name</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller types the name of the store
   in the "Input Store Name" text box and
   selects "Create Store".
</ol>

Expected result: Store created and
"Store Created" JOptionPane is displayed.

Test Status: Failed; "Store name already exists" JOptionPane is displayed and store
is not created.
<br><br>

<h3>Test 11: Change Password</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Account" option.
<li> The Seller inputs the new desired password in the "Input
New Password" text box.
<li> The Seller selects the "Change Password" option.
</ol>

Expected result: "Password Changed" JOptionPane is displayed
and the password for the account is changed.

Test Status: Passed.
<br><br>

<h3>Test 12: Delete Account</h3>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Account" option.
<li> The Seller selects the "Delete Account" option.
</ol>

Expected Result: "Account Deleted" JOptionPane is displayed and the account is
deleted.

Test Status: Passed.
<br><br>

<h3>Test 13: Sign Out</h3>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Sign Out" option.
</ol>

Expected result: Seller signed out, and program displays the
Login Frame.

Test Status: Passed.
<br><br>

<h3>Test 14: View Current Carts</h3>
<h6>If there is at least one Buyer account created</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "View Current Carts" option.
</ol>

Expected result: The carts of every buyer is shown in a JOptionPane

Test Status: Passed.
<br><br>

<h3>Test 15: View Current Carts</h3>
<h6>If there are no Buyer accounts created</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "View Current Carts" option.
</ol>

Expected result: Carts that contain the seller's items are displayed.

Test Status: Failed; "No Customers" JOptionPane is displayed.
<br><br>

<h3>Test 16: Change Password</h3>
Steps:

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Manage Account" option.
<li> The Seller inputs a password containing a comma in the change password text box
<li> The Buyer selects the "Change Password" option.
</ol>

Expected result: "Password Changed" JOptionPane is displayed
and the password for the account is changed.

Test Status: Failed; "Password can not contain commas" JOptionPane is displayed.
<br><br><br>

<h2> Main Buyer Frame Tests </h2>

<h3>Test 17: Change Password</h3>
Steps:

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Manage Account" option.
<li> The Buyer inputs the new desired password in the "Input
New Password" text box.
<li> The Buyer selects the "Change Password" option.
</ol>

Expected result: "Password Changed" JOptionPane is displayed
and the password for the account is changed.

Test Status: Passed.
<br><br>

<h3>Test 18: Change Password</h3>
Steps:

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Manage Account" option.
<li> The Buyer selects the "Change Password" option while leaving the text box blank.
</ol>

Expected result: "Password Changed" JOptionPane is displayed
and the password for the account is changed.

Test Status: Failed; "Input a new Password" JOptionPane is displayed.
<br><br>

<h3>Test 19: Delete Account</h3>
Steps:

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Manage Account" option.
<li> The Buyer selects the "Delete Account" option.
</ol>

Expected Result: "Account Deleted" JOptionPane is displayed and the account is
deleted.

Test Status: Passed.
<br><br>

<h3>Test 20: Sign Out</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Logout" option.
</ol>

Expected result: Buyer signed out, and program displays the
Login Frame.

Test Status: Passed.
<br><br>

<h3>Test 21: View Cart</h3>
Steps:
<h6>If Buyer has items in cart</h6>

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "View Cart" option.
</ol>

Expected result: Program displays the
Cart Frame.

Test Status: Passed.
<br><br>

<h3>Test 22: View Cart</h3>
Steps:
<h6>If Buyer has no items in cart</h6>

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "View Cart" option.
</ol>

Expected result: Program displays the
Cart Frame.

Test Status: Failed; "You have no items in your cart" JOptionPane is
displayed.
<br><br>

<h3>Test 23: Sort Dashboard</h6>
Steps:

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Sort Dashboard" option.
<li> The Buyer selects either the "Sort by Price" or "Sort by Quantity" 
option.
</ol>

Expected result: Program sorts the items available based on the criteria
provided.

Test Status: Passed.
<br><br>

<h3>Test 24: Purchase History</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Purchase History" option.
</ol>

Expected result: Program displays the Purchase History frame.

Test Result: Passed.
<br><br>

<h3>Test 25: Show Statistics Frame</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Statistics" option.
</ol>

Expected result: Program displays the Buyer Statistics frame.

Test Status: Passed.
<br><br>

<h3>Test 26: Search for Product</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer types a String into the 
Search for Product text box and then selects the "Search for Product" 
option.
<li> A popup emerges where the Buyer selects to search for products by name, description, or store
</ol>

Expected result: Program searches the product list for stores names, item names, or descriptions that
contain the searched String and only displays those specific items.

Test Status: Passed.
<br><br>

<h3>Test 27: Add to Cart</h3>
<h6>If there is at least one item displayed on the dashboard</h6>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects an item and 
right-clicks it.
<li> The Buyer selects the "Add to Cart" option.
<li> The Buyer inputs a quantity less or equal to what is available and selects
the "OK" option.
</ol>

Expected result: Product is added to cart and the quantity requested is
subtracted from the quantity available.

Test Status: Passed.
<br><br>

<h3>Test 28: Add to Cart</h3>
<h6>If there is at least one item displayed on the dashboard</h6>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects an item and 
right-clicks it.
<li> The Buyer selects the "Add to Cart" option.
<li> The Buyer inputs a quantity greater than what is available and selects
the "OK" option.
</ol>

Expected result: Product is added to cart and the quantity requested is
subtracted from the quantity available.

Test Status: Failed; "Not enough in stock to match quantity requested"
JOptionPane is displayed.
<br><br>

<h3>Test 29: View More Details</h3>
<h6>If there is at least one item displayed on the dashboard</h6>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects an item and 
right-clicks it.
<li> The Buyer selects the "See More Details" option.
</ol>

Expected result: More details about the item are displayed in the 
bottom-left corner of the frame.

Test Status: Passed.
<br><br><br>

<h2> Manage Catalogue Frame Tests </h2>
<h6>For each test, it is assumed that the Seller owns at least one store.</h6>

<h3>Test 30: Create new product</h3>
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

<h3>Test 31: Edit product</h3>
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
<li> The Seller modifies one of the fields with a valid input.
<li> The Seller selects the "Edit Selected Item" option.
</ol>

Expected result: "Product Successfully Changed" JOptionPane
is displayed, and the field/s edited are modified.

Test Status: Passed.
<br><br>

<h3>Test 32: Delete product</h3>
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
<li> The Seller selects the "Yes" option for if they are sure they want to delete the product
</ol>

Expected result: "Product Deleted" JOptionPane is displayed,
and the product is deleted from the store.

Test Status: Passed.
<br><br>

<h3>Test 33: Delete product</h3>
Steps:
<h6>If there is at least one item in the store:</h6>
<h6>No item is selected from the store by the Buyer:</h6>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li> The Seller selects the "Delete Selected Item" option.
<li> The Seller selects the "Yes" option for if they are sure they want to delete the product
</ol>

Expected result: "Product Deleted" JOptionPane is displayed,
and the product is deleted from the store.

Test Status: Failed; "No Item Selected" JOptionPane is displayed
<br><br>

<h3>Test 34: Export Product File</h3>
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

<h3>Test 35: Return to Dashboard</h3>
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

<h3>Test 36: Create new product</h3>
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

<h3>Test 37: Create new product</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li>The Seller selects an item to modify and changes the quantity field to either a negative integer 
or a number other than an integer ex: -4, 4.02, -8.99
<li>The Seller selects the "Add Product" option.
</ol>

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Failed; "Inputted Quantity must be a Positive Integer" JOptionPane
is displayed.
<br><br>

<h3>Test 38: Create new product</h3>
Steps:

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
<li>The Seller selects an item to modify and changes the price field to either a negative integer 
or a number with more than two decimal places ex: -4, 4.0244, -8.99
<li>The Seller selects the "Add Product" option.
</ol>

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store.

Test Status: Failed; "Inputted Price must be Positive and have Two
Decimal Places" JOptionPane is displayed.
<br><br>

<h3>Test 39: Edit product</h3>
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
<li> The Seller changes more than one of the fields: name, description, quantity, or price
<li> The Seller selects the "Edit Selected Item" option.
</ol>

Expected result: "Product Successfully Changed" JOptionPane
is displayed, and the field/s edited are modified.

Test Status: Failed; "Change one of the Input Fields" JOptionPane is displayed.
<br><br>

<h3>Test 40: Edit product</h3>
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

<h3>Test 41: Export Product File</h3>
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
<br><br><br>

<h2> Manage Store Frame Tests </h2>

<h3>Test 42: Modify Products</h3>
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

<h3>Test 43: Modify Products</h3>
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

<h3>Test 44: Import Product File</h3>
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

Expected result: JOptionPane with the number of items added is displayed. If the Seller selects the store, they will be able
to see the newly imported items.

Test Status: Passed.
<br><br>

<h3>Test 45: Delete Store</h3>
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

<h3>Test 46: Return to Dashboard</h3>
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

<h3>Test 47: Statistics of Store</h3>
Steps:
<h6> If there is at least one store that had sales</h6>
<h6> The selected store has sold at least one product to a customer</h6>

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

<h3>Test 48: Import Product File</h3>
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

Expected result: JOptionPane with the number of items added is displayed. If the Seller selects the store, they will be able
to see the newly imported items.

Test Status: Failed; "No Products were Added" JOptionPane is displayed.
<br><br>

<h3>Test 49: Statistics of Store</h3>
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

<h3>Test 50: Statistics of Store</h3>
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

<h3>Test 51: View Sales</h3>
Steps:
<h6> If the store selected has sales</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
<li> The Seller selects the "Sales List" option.
</ol>

Expected Result: The Sales are displayed.

Test Status: Passed.
<br><br>

<h3>Test 52: View Sales</h3>
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

<h3>Test 53: View Sales</h3>
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
<br><br><br>

<h2> Cart Frame Tests </h2>
<h6>For each Test, it is assumed that Buyer has at least one item in cart.</h6>

<h3>Test 54: Remove Item from Cart</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "View Cart" option.
<li> The Buyer selects an item and then selects the "Remove Item from Cart"
option.
</ol>

Expected result: "Item successfully removed from cart" JOptionPane is
displayed, and item is removed.

Test Status: Passed.
<br><br>

<h3>Test 55: Remove Item from Cart</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The seller modifies either the name or price of the item to be removed from cart
<li> The Buyer selects the "Remove Item from Cart"
option.
</ol>

Expected result: "Item successfully removed from cart" JOptionPane is
displayed, and item is removed.

Test Status: Failed; "Item not removed : Please refresh page" JOptionPane is displayed.
<br><br>

<h3>Test 56: Return to Dashboard</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Return to Dashboard" option.
</ol>

Expected result: The program displays the Main Buyer Frame.

Test Status: Passed.
<br><br>

<h3>Test 57: Checkout</h3>
Steps:
<h6>If there is at least one item in the cart</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Checkout" option.
</ol>

Expected result: "Checkout Successful" JOptionPane is displayed.

Test Status: Passed.
<br><br><br>

<h3>Test 58: Checkout</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> The buyer adds all but one of an item on the marketplace to their cart
<li> Another buyer adds two of that same item to their shopping cart and checks out 
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Checkout" option.
</ol>

Expected result: "Checkout Successful" JOptionPane is displayed.

Test Status: Failed; "Checkout Failed" JOptionPane opens on screen and the item is removed from the cart
<br><br><br>

<h3>Test 59: Checkout</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> The current Buyer adds all but one of an item on the marketplace to their cart
<li> Another buyer adds two of that same item to their shopping cart and checks out
<li> The current Buyer adds two of an item named "Office Chair" to their cart
<li> The current Buyer adds one of an item named "Dining Chair" to their cart
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Seller who sells the dining chair deletes that product from their store
<li> The Buyer selects the "Checkout" option.
</ol>

Expected result: "Checkout Successful" JOptionPane is displayed.

Test Status: Failed; "Partial Success" JOptionPane opens on screen. All items that were successfully 
checked out are listed and all the items that failed to check out are listed with the reason why next to the item. 
Ex: "Not enough in stock to fulfill order", "Item No longer exists". All items are removed from the cart
<br><br><br>

<h2> Purchase History Frame Tests </h2>

<h3>Test 60: View Purchase History</h3>
Steps:
<h6>If Buyer has at least one item in the Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Purchase History" option.
<li> The Buyer selects the "View Purchase History" option.
</ol>

Expected Result: The Purchase History of the Buyer is displayed.

Test Status: Passed.
<br><br>

<h3>Test 61: View Purchase History</h3>
Steps:
<h6>If Buyer no items in the Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Purchase History" option.
<li> The Buyer selects the "View Purchase History" option.
</ol>

Expected Result: The Purchase History of the Buyer is displayed.

Test Status: Failed; "Purchase History does not exist!" JOptionPane is
displayed.
<br><br>

<h3>Test 62: Export Purchase History</h3>
Steps:
<h6>If Buyer has at least one item in the Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Purchase History" option.
<li> The Buyer selects the "Export Purchase History" option.
</ol>

Expected Result: The Purchase History of the Buyer is exported in a file
named "[email address]PurchaseHistory.csv".

Test Status: Passed.
<br><br>


<h3>Test 63: Export Purchase History</h3>
Steps:
<h6>If Buyer has no items in the Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Purchase History" option.
<li> The Buyer selects the "Export Purchase History" option.
</ol>

Expected Result: The Purchase History of the Buyer is exported in a file
named "[email address]PurchaseHistory.csv".

Test Status: Failed; "Export Failed" JOptionPane is displayed.
<br><br><br>

<h2> Buyer Statistics Frame Tests </h2>

<h3>Test 64: Buyer Statistics—Show Statistics</h3>

<h6>If Buyer has at least one item in the Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Statistics" option under the Buyer 
Statistics section.
</ol>

Expected result: The Statistics of the Buyer are displayed in a JOptionPanel.

Test Status: Passed.
<br><br>

<h3>Test 65: Buyer Statistics—Show Statistics</h3>

<h6>If Buyer has no items in Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Statistics" option under the Buyer 
Statistics section.
</ol>

Expected result: The Sorted Statistics of the Buyer are displayed in a JOptionPanel.

Test Status: Failed; JOptionPane displays on screen saying "You have not purchased any items yet".
<br><br>

<h3>Test 66: Buyer Statistics—Show Sorted Statistics</h3>

<h6>If Buyer has at least one item in the Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Sorted Statistics" option under the Buyer 
Statistics section.
</ol>
Expected result: The Sorted Statistics of the Buyer are displayed in a JOptionPanel.

Test Status: Passed.

<h3>Test 67: Buyer Statistics—Show Sorted Statistics</h3>

<h6>If Buyer has no items in Purchase History</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Sorted Statistics" option under the Buyer 
Statistics section.
</ol>

Expected result: The Sorted Statistics of the Buyer are displayed in a JOptionPanel.

Test Status: Failed; JOptionPane displays on screen saying "You have not purchased any items yet".
<br><br>

<h3>Test 68: Store Statistics—Show Statistics</h3>

<h6>If at least one Store has been created by a Seller
</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Statistics" option under the Store 
Statistics section.
</ol>

Expected result: The Statistics of the Stores are displayed in a JOptionPanel.

Test Status: Passed.
<br><br>

<h3>Test 69: Store Statistics—Show Sorted Statistics</h3>

<h6>If at least one Store has been created by a Seller
</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Stored Statistics" option under the Store 
Statistics section.
</ol>

Expected result: The Sorted Statistics of the Store are displayed in a JOptionPanel.

Test Status: Passed.
<br><br>

<h3>Test 70: Store Statistics—Show Statistics</h3>

<h6>If no stores been created by Sellers
</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Statistics" option under the Store 
Statistics section.
</ol>

Expected result: The Statistics of the Stores are displayed in a JOptionPanel.

Test Status: Failed; JOptionPane displays on screen saying "You have not purchased any items yet".
<br><br>

<h3>Test 71: Store Statistics—Show Sorted Statistics</h3>

<h6>If no stores been created by Sellers
</h6>
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects the "View Cart" option.
<li> The Buyer selects the "Statistics" option.
<li> The Buyer selects the "Show Sorted Statistics" option under the Store 
Statistics section.
</ol>

Expected result: The Sorted Statistics of the Stores are displayed in a JOptionPanel.

Test Status: Failed; JOptionPane displays on screen saying "You have not purchased any items yet".
<br><br>