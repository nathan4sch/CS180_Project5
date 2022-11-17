# CSV Formatting Guide

#### By Benjamin Herrington

*If you need to edit the formatting for ANY REASON, please notify all other team members first because it will affect their code and there is likely another solution.*

### All Credentials are in a single csv, titled "FMCredentials"
B: (Store,item,quantity,price) 
Cart format is identical to Buyer History format
Different Purchases/Sales are separated by "~" meanwhile the details of those Purchases/Sales are separated by "!". This will make the list easy to create
Shopping cart Follows similar convention. Different items separated by "~", details separated by "!"
LOGIN CREDENTIALS: Email,Username,password,buyer/seller,PurchaseHistory if Buyer, shopping cart if user is a Buyer
NOTE: Placeholder for purchase history and shopping cart before the user purchases anything is x; 
> Ex: JohnSmith@gmail.com,SmithChairs17,123456,buyer,x,x

After they add items to shopping cart and purchase item is seen below
NOTE: Also the first index in either the purchase history or shopping cart CAN NOT have ~ before the first value.
For example the example below only has one item in the shopping cart and therefore a ~ is not seen after the , that indicates the shopping cart data has begun
> Ex: JohnSmith@gmail.com,SmithChairs17,123456,buyer,John's Chairs!awesome chair!5!39.99~John's Couchs!Epic Couch!1!399.99,Bob's Tables!solid table!1!79.99


NOTE: same rules for the ~ formatting in FMCredentials apply to this csv as well regarding the purchase listing
### All Stores are in a single CSV, titled "FMStores"
STORES: storeName,ownerID, Purchase Listing(Customer sold to,Store,item,quantity,price)
> Ex: John's Chairs,JohnSmith@gmail.com,ChairLover@gmail.com!awesome chair!5!39.99~ChairLover@gmail.com!awesome chair!5!39.99

### All items stored in a single CSV, titled "FMItems.csv"
PRODUCT: storeName,itemName,description,quantityAvailable,price
> Ex: John's Chairs,awesome chair,the best chair your rear has ever neared!,5,39.99

