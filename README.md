# Project 5 - Furniture Marketplace

## CS 18000 GLD Capstone Project

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

# How to run the project
## Step 1 - Run Server.java and Client.java
- This will run the server in which multiple clients will be able to access their accounts. 
## Step 2 - Account Login & Creation
- Users will have an option to sign in to an existing account or create a new account.
- **(1) Sign In**
- Users will be signed in once they enter the correct password and email. 
- **(2) Create an account**
- Users can either create a Buyer account or a Seller account. They will need to enter an valid email and a password that is at least 6 characters long.
## Step 3 - Main Dashboard
- If the account type is a Buyer, the Buyer Dashboard is shown with available options and all available items displayed to purchase.

Sample Dashboard

- If the account type is a Seller, the Seller dasboard is shown will all available items 

Sample Dashboard

# Project Members & Roles Contributed
### Andrei Deaconescu
- 
### Benjamin Herrington
-
### Colin Wu
- 
### Dakota Baldwin
- 
### Nathan Schneider
-

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
- Users can search for a product by its name, description, or store name. This will make the Furniture Marketplace dashboard only show the items searched for.
- Users can refresh the dashboard to reflect changes made by Sellers (Adding new items, editing prices, etc.)
### The right panel shows buttons that contain other functions for Buyers.
- **View Cart: ** redirects the user to CartFrame.java
- **Sort Dashboard: ** 
## Buyer.java
- Contains all methods the buyers may use. These methods are called in Server.java.
## Seller.java
- Contains all methods the sellers may use. These methods are called in Server.java.
## Store.java
- Contains all methods related to the stores owned by sellers. These methods are called in Server.java
## Item.java
- Contains all methods related to the products sold by stores. These methods are called in Server.java
