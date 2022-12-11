# Project 5 - Furniture Marketplace

## CS 18000 GLD Capstone Project

# How to run the project

### Note: After cloning the project from Vocareum follow these steps:

## Step 1 - Clone the project and add to your IDE
- Create a new project in your IDE (IntelliJ, Eclipse, etc.) to run the project.
## Step 2 - Move all the .java files to the src folder and move the .csv files outside the src folder inside the CS180_Project5 folder.
- After doing this, you should be ready to run the project.
## Step 3 - Run Server.java and Client.java
- This will run the server in which multiple clients will be able to access their accounts. 
## Step 4 - Account Login & Creation
- Users will have an option to sign in to an existing account or create a new account.
### Sign In
- Users will be signed in once they enter the correct password and email. 
### Create an account
- Users can either create a Buyer account or a Seller account. They will need to enter an valid email and a password that is at least 6 characters long.

## Step 4 - Main Dashboards
- After sucessfully creating an account or signing in, the user will be able to use the Buyer and Seller Dashboards based on account type.

### Main Buyer Dashboard

- If the account type is a Buyer, the Buyer Dashboard is shown with available options and all available items displayed to purchase.

![Buyer Dashboard](https://github.com/nathan4sch/CS180_Project5/blob/Readme/documents/Furniture%20Marketplace%20Main%20Dashboard.png "Buyer Dashboard")

### Main Seller Dashboard

- If the account type is a Seller, the Seller dasboard is shown will all available items 

![Seller Dashboard](https://github.com/nathan4sch/CS180_Project5/blob/Readme/documents/Create%20And%20Manage%20Stores%20Dashboard.png "Seller Dashboard")

### Description
This is a furniture marketplace where users can buy and sell their items. Users can create an account that is either a Buyer or Seller with their information saved between sessions. To access their account after creation, users just have to log into their account by entering the correct credentials. 

For buyers, the Buyer Dashboard shows a marketplace listing page shows the store, product name, and price of the available goods. 
- This dashboard can be sorted by price and amount of items available, and users can search for specific products. 
- Buyers can add the marketplace items to their cart, remove items from their cart, and checkout their items in cart. 
- Buyers can also view and export a file of their purchase history. 
- Buyers can view statistics of the stores they bought from and all stores by the number of their products sold; these statistics can be sorted by quantity from most to least. 
- Lastly buyers can change their passwords and delete their accounts.

For sellers, the Seller Dashboard has many options including creating stores, creating products for a specific store, and viewing items currently in the buyers' carts. 
- Under managing stores, sellers can view a list of all sales of the selected store, view the statistics of their stores, import product files, and delete their stores. 
- Sellers can then select a store and modify the products sold under the store. This includes adding a new product, editing existing products, deleting a selected product, - and exporting a product file. 
- Lastly, sellers can change their passwords and delete their accounts.

# Project Members & Roles Contributed
### Andrei Deaconescu
- Did most of the Test Cases in Tests.md
- Project 5 Report
- General Bug Fixing
- Methods in Seller.java

### Benjamin Herrington
- Main GUI for MainBuyerFrame.java: the Furniture Marketplace Dashboard, Sort Dashboard button, Search For Product button, and Refresh Dashboard Button
- Server functionality for Buyer Cart: Checkout, Adding items to cart.
- Server functionality for Furniture Marketplace Dashboard: Showing the Dashboard, Sorting Dashboard, Searching for products, and Refreshing Dashboard.
- Editing Project 5 Video

### Colin Wu
- Main GUI for MainBuyerFrame.java: View Cart Button, Purchase History button, Manage Account button, and Buyer Statistics button.
- GUI and Server functionalites for CartFrame.java: Removing items from cart and parts of checking out items
- GUI and Server functionalites for BuyerStatisticsFrame.java: Buyer and viewing Store Statistics
- GUI and Server functionalites for ManageAccountFrame.java for Buyers: Changing password and deleting accounts
- GUI and Server functionalites for PurchaseHistoryFrame.java: Viewing and Exporting Purchase History file

### Dakota Baldwin
- Changing cart to not change item quantity when adding to cart.

### Nathan Schneider
- General Outline for the project: Implemented main functionality in Server.java and Client.java
- Main GUI for MainSellerFrame.java: Create and Manage Stores Dashboard and View Current Buyer Carts
- GUI and Server functionalites for LoginFrame.java: Signing in and Creating new accounts
- GUI and Server functionalites for ManageAccountFrame.java for Sellers: Changing password and deleting accounts
- GUI and Server functionalites for ManageStoreFrame.java: Sales list, Store Statistics (Buyer and Item Statistics), Deleting Store, Importing Product Files for Store
- GUI and Server functionalites for ManageCatalogueFrame.java: Selecting, Adding, Deleting, and Editing Products; Export Product Files for Store

# Class Information
## Client.java
- The client that handles all user functions and sends information to Server.java. 
## Server.java
- The server handles all information sent to it by clients. Whenever a client sucessfully signs in, the server allocates a new thread to the client, allowing for multiple clients to be handled simultaneously. 
## LoginFrame.java
- The JFrame GUI that users use to sign in and create new accounts. If a user sucessfully creates a new account, they will be redirected to the corresponding GUI for either buyers or sellers.
- LoginFrame checks for a valid email input which requires the email to have a "@" and requires the password to be at least 6 characters long. If the sign in credentials are correct, users will be redirected to either MainBuyerFrame.java or MainSellerFrame.java based on their corresponding account type. 
## MainBuyerFrame.java
- Main Buyer Dashboard for all buyer functionalities. 
### The left panel shows the Furniture Marketplace that lists all the products available for purchase.
- Users can select an item by clicking on it. Once highlited, users can right click to either see more details of the product or click add to cart to add the item to cart. Adding to cart will prompt the user to enter the quantity of the product to add. If the amount is equal or less than the amount in stock, the product will sucessfully be added to their cart.
- Users can search for a product by its name, description, or store name using the **Search for Product** button. This will make the Furniture Marketplace dashboard only show the items searched for.
- Users can refresh the dashboard to reflect changes made by Sellers (Adding new items, editing prices, etc.) with the **Refresh Dashboard** button.
### The right panel shows buttons that contain other functions for Buyers.
- **View Cart:** redirects the user to CartFrame.java if the user currently has items in their cart, otherwise an error panel will pop up.
- **Sort Dashboard:** sorts the Furniture Marketplace dashboard by price and quantity available in ascending order.
- **Purchase History:** redirects the user to PurchaseHistoryFrame.java
- **Manage Account:** redirects the user to ManageAccountFrame.java
- **Statistics:** redirects the user to BuyerStatisticsFrame.java
- **Logout:** Logs the current user out, closes the socket, and redirects the user to LoginFrame.java
## MainSellerFrame.java
- Main Seller Dashboard for all seller functionalities.
### The left panel shows the Create and Manage Store dashboard. 
- Users can enter a new store name in the text field and click on tbe **Create Store** button to create a new store.
- To manage all stores owned by the current user and many other functions, users can click on the **Manage Stores** button which redirects the user to ManageStoreFrame.java
### The right panel shows buttons that contain other functions for Sellers.
- **Stores:** returns the user to the Create and Manage Stores dashboard.
- **View Current Carts:** shows all items in the carts of buyers.
- **Manage Account:** changes the left panel to the Manage Account dashboard. 
- Users can enter a new valid password and click on **Change Password** to change their password. 
- The **Delete Your Account** button will have the user to confirm to delete their account. If the account is sucessfully deleted, users will be redirected to LoginFrame.java.
- **Logout:** Logs the current user out, closes the socket, and redirects the user to LoginFrame.java
## CartFrame.java
- Frame that allows users to see all their current items in cart, remove items from cart, and checkout all items from their cart.
### The left panel shows the cart options
- **Checkout:** checks out all items and adds them to the current user's purchase history. If all items are sucessfully checked out users will be redirected back to MainBuyerFrame.java. 
- **Remove Item From Cart:** removes the selected item from cart.
- **Return to Dashboard:** user is redirected back to MainBuyerFrame.java
### The right panel shows all items in the current user's cart
- Each item has the item name, quantity in cart, and total price of item. Users can click on the radio buttons next to each item to select them, which will change the right panel's label to the currently selected item.
## PurchaseHistoryFrame.java
- Allows users to view their purchase history and export a file of their purchase history.
- **View Purchase History:** shows a panel containing a list of all the items purchased by the current user.
- **Export Purchase History:** exports a file containing the purchase history of the current user. The file format is *userEmailPurchaseHistory.csv*.
- **Return to Dashboard:** redirects the user back to MainBuyerFrame.java
## ManageAccountFrame.java**
- Allows users to change their passwords and delete their accounts
- **Change Password:** changes the current user's password to the new entered password if it is valid.
- **Delete Account:** has the current user confirm to delete their account. If the account is sucessfully deleted, users will be redirected to LoginFrame.java.
- **Return to Dashboard:** user is redirected back to MainBuyerFrame.java
## BuyerStatisticsFrame.java
- Alows Buyers see Store and Buyer statistics.
- ***Buyer Statistics*** Buyers can see a list of all stores from where their products were bought from.
- **Show Statistics:** shows a panel of all the stores the current user has bought from.
- **Show Sorted Statistics:** shows a sorted panel of all the stores the current user has bought from by quantity of items bought from most to least.
- ***Store Statistics*** Buyers can also see a list of all stores with the amount of products sold by each store.
- **Show Statistics:** shows a panel of all the stores with the amount of items sold per store.
- **Show Sorted Statistics:** shows a sorted a panel of all the stores with the amount of items sold per store by quantity of items sold from most to least.
- **Return to Dashboard:** user is redirected back to MainBuyerFrame.java
## ManageStoreFrame.java
- Allows users to manage their current stores.
### The left panel shows all store options and buttons for the Manage Stores dashboard
- **Sales List:** shows a panel of all sales of the selected store.
- **Statistics of Store:** shows a panel with a dropdown menu to select different store statistics. **Buyer Statistics** shows all customers that have purchased items from the selected store and the quantity of items bought. **Sorted Buyer Statistics** shows the Buyer Statistics panel sorted by the number of items purchased from most to least. **Item Statistics** shows all items sold by store and the number of sales for each item. **Sorted Item Statistics** shows the Item Statistics sorted by sales from most to least.
- **Modify Products** redirects the current user to ManageCatalogueFrame.java based on the selected store.
- **Delete Store** asks the current user to confirm to delete the selected store. 
- **Import Product File** imports a file of product information and adds the imported information to the corresponding stores. The file must be formatted properly, otherwise an error panel will be shown.
- **Return to Dashboard:** user is redirected back to MainBuyerFrame.java
### The right panel shows all the stores owned by the current user.
- Users can select a specific store by clicking on the radio button next to the store. This will change the label in the left panel to show the currently selected store.
## ManageCatalogueFrame.java
- Allows users to manage products in their store. Users can edit an existing product's information, create a new product, and deleted the selected product. Users can also export a file with a list of all their products under the specific store.
### The left panel contains the Store Products Dashboard
- Creating a new product requires users to input the product name, description, quantity available, and price of the new product in the corresponding text fields. 
- **Edit Selected Item:** changes the selected items information to the new information entered in the text fields.
- **Delete Selected Item:** asks the current user to confirm to delete the selected item.
- **Add Product:** creates a new item based on the information entered in the text fields.
- **Export Product File:** creates a file containing all the product information of the store. The file format is *storeName--Items.csv*.
- **Return to Dashboard:** user is redirected back to MainBuyerFrame.java
## Buyer.java
- Contains all methods the buyers may use. These methods are called in Server.java.
## Seller.java
- Contains all methods the sellers may use. These methods are called in Server.java.
## Store.java
- Contains all methods related to the stores owned by sellers. These methods are called in Server.java
## Item.java
- Contains all methods related to the products sold by stores. These methods are called in Server.java
