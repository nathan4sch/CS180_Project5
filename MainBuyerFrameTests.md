<h2> Main Buyer Frame Tests </h2>

<h3>Test 1: Change Password</h3>
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

<h3>Test 2: Change Password</h3>
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

<h3>Test 3: Delete Account</h3>
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

<h3>Test 4: Sign Out</h3>
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

<h3>Test 5: View Cart</h3>
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

<h3>Test 6: View Cart</h3>
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

<h3>Test 7: Sort Dashboard</h6>
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

<h3>Test 8: Purchase History</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Purchase History" option.
</ol>

Expected result: Program displays the Purchase History frame.

Test Result: Passed.
<br><br>

<h3>Test 9: Show Statistics Frame</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the
   Buyer selects the "Statistics" option.
</ol>

Expected result: Program displays the Buyer Statistics frame.

Test Status: Passed.
<br><br>

<h3>Test 10: Search for Product</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer types a String into the 
Search for Product text box and then selects the "Search for Product" 
option.
</ol>

Expected result: Program searches the product list for products that 
contain the searched String and only displays those specific items.

Test Status: Passed.
<br><br>

<h3>Test 11: Add to Cart</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects an item and 
right-clicks it.
<li> The Buyer selects the "Add to Cart" option.
<li> The Buyer inputs a quantity lower than the one available and selects
the "OK" option.
</ol>

Expected result: Product is added to cart and the quantity requested is
subtracted from the quantity available.

Test Status: Passed.
<br><br>

<h3>Test 12: Add to Cart</h3>
Steps:
<ol>
<li> Buyer launches application and logs in.
<li> From the main Buyer dashboard, the Buyer selects an item and 
right-clicks it.
<li> The Buyer selects the "Add to Cart" option.
<li> The Buyer inputs a quantity greater than the one available and selects
the "OK" option.
</ol>

Expected result: Product is added to cart and the quantity requested is
subtracted from the quantity available.

Test Status: Failed; "Not enough in stock to match quantity requested"
JOptionPane is displayed.
<br><br>