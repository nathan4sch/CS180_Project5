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
<li> User selects the Seller option.
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
<li> User selects the Buyer option.
<li> User selects the "SignIn" button.
</ol>

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Buyer dashboard is loaded.

Test Status: Passed.
<br><br>

<h3>Test 7: Seller log in</h3>
Steps:
<h6>If the account already exists</h6>
<ol>
<li> User launches application.
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the Seller option.
<li> User selects the "SignIn" button.
</ol>

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Seller dashboard is loaded.

Test Status: Failed; "No account found or Account already logged in" JOptionPane
is displayed and user is not logged in.
<br><br>

<h3>Test 7: Buyer log in</h3>
Steps:
<h6>If the account already exists</h6>
<ol>
<li> User launches application.
<li> User selects the email text box.
<li> User enters email via the keyboard.
<li> User selects the password text box.
<li> User enters password via the keyboard.
<li> User selects the Buyer option.
<li> User selects the "SignIn" button.
</ol>

Expected result: Application verifies the
user's email and password, "Sign in
Successful" JOptionPane is displayed, and
the Buyer dashboard is loaded.

Test Status: Failed; "No account found or Account already logged in" JOptionPane
is displayed and user is not logged in.
<br><br>