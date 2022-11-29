<h2> Login Frame tests </h2>

<h3>Test 1: Create account for Seller</h3>
Steps:

1. User launches application
2. User selects the email text box.
3. User enters email via the keyboard.
4. User selects the password text box.
5. User enters password via the keyboard.
6. User selects the Seller option.
7. User selects the "Create Account" button.

Expected result: "Account Created"
JOptionPane is displayed, login credentials
stored into FMCredentials.csv, and Seller
dashboard loaded for the user.

Test Status: Passed.
<br><br>

<h3>Test 2: Create account for Buyer</h3>
Steps:

1. User launches application
2. User selects the email text box.
3. User enters email via the keyboard.
4. User selects the password text box.
5. User enters password via the keyboard.
6. User selects the Buyer option.
7. User selects the "Create Account" button.

Expected result: "Account Created"
JOptionPane is displayed, login credentials
stored into FMCredentials.csv, and Buyer
dashboard loaded for the user.

Test Status: Passed.
<br><br>

<h3>Test 3: Seller log in</h3>
Steps:

1. User launches application.
2. User selects the email text box.
3. User enters email via the keyboard.
4. User selects the password text box.
5. User enters password via the keyboard.
6. User selects the Seller option.
7. User selects the "SignIn" button.

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Seller dashboard is loaded.

Test Status: Passed.
<br><br>

<h3>Test 4: Buyer log in</h3>
Steps:

1. User launches application.
2. User selects the email text box.
3. User enters email via the keyboard.
4. User selects the password text box.
5. User enters password via the keyboard.
6. User selects the Buyer option.
7. User selects the "SignIn" button.

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Buyer dashboard is loaded.

Test Status: Passed.
<br><br>


<h2>Main Seller Frame Tests</h2>

<h3>Test 1: Create Store</h3>
Steps:

1. Seller launches application and logs in.
2. From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
3. The Seller types the name of the store
   in the "Input Store Name" text box and
   selects "Create Store".

Expected result: Store created and
"Store Created" JOptionPane is displayed.

Test Status: Passed.
<br><br>

<h2>Manage Store Frame tests</h2>

<h3>Test 1: Create new product</h3>
Steps:

1. Seller launches application and logs in.
2. From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
3. The Seller selects the "Manage
   Catalogue" option.
4. The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
5. The Seller inputs the desired name,
   description, quantity and price of
   the desired item into their respective
   fields, and select the "Add Product"
   option.

Expected result: "Item Added" JOptionPane
is displayed, and item is added to the store

Test Status: Passed.
<br><br>

<h3>Test 2: Delete item</h3>
Steps:

1. Seller launches application and logs in.
2. From the main Seller dashboard, the
   Seller selects the "Manage Stores" option.
3. The Seller selects the "Manage
   Catalogue" option.
4. The Seller selects the store that they
   would like to manage, and then selects the
   "Modify Products" option.
5. The Seller selects a product, and then 
selects the "Delete Selected Item" option.

Expected result: "Product Deleted" JOptionPane
is displayed, and product is deleted from the
store.


