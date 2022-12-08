<h2>Main Seller Frame Tests</h2>

<h3>Test 1: Create Store</h3>
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

<h3>Test 2: Create Store</h3>
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

<h3>Test 3: Change Password</h3>
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

<h3>Test 4: Delete Account</h3>

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

<h3>Test 5: Sign Out</h3>

<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "Sign Out" option.
</ol>

Expected result: Seller signed out, and program displays the
Login Frame.

Test Status: Passed.
<br><br>

<h3>Test 6: View Current Carts</h3>
<h6>If buyers have at least one of the seller's items in their cart</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "View Current Carts" option.
</ol>

Expected result: Carts that contain the seller's items are displayed.

Test Status: Passed.
<br><br>

<h3>Test 7: View Current Carts</h3>
<h6>If buyers have none of the seller's items in their cart</h6>
<ol>
<li> Seller launches application and logs in.
<li> From the main Seller dashboard, the
   Seller selects the "View Current Carts" option.
</ol>

Expected result: Carts that contain the seller's items are displayed.

Test Status: Failed; "No Customers" JOptionPane is displayed.
<br><br>

<h3>Test 8: Change Password</h3>
Steps:

<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Manage Account" option.
<li> The Buyer selects the "Change Password" option.
</ol>

Expected result: "Password Changed" JOptionPane is displayed
and the password for the account is changed.

Test Status: Failed; "Input a new Password" JOptionPane is displayed.
<br><br>