# Test Cases - Automation Exercise

## Test Case 1: Register User
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Signup / Login' button
5. Verify 'New User Signup!' is visible
6. Enter name and email address
7. Click 'Signup' button
8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
9. Fill details: Title, Name, Email, Password, Date of birth
10. Select checkbox 'Sign up for our newsletter!'
11. Select checkbox 'Receive special offers from our partners!'
12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
13. Click 'Create Account button'
14. Verify that 'ACCOUNT CREATED!' is visible
15. Click 'Continue' button
16. Verify that 'Logged in as username' is visible
17. Click 'Delete Account' button
18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button

## Test Case 2: Login User with correct email and password
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Signup / Login' button
5. Verify 'Login to your account' is visible
6. Enter correct email address and password
7. Click 'login' button
8. Verify that 'Logged in as username' is visible
9. Click 'Delete Account' button
10. Verify that 'ACCOUNT DELETED!' is visible

## Test Case 3: Login User with incorrect email and password
1-8 (already implemented)

## Test Case 4: Logout User
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Signup / Login' button
5. Verify 'Login to your account' is visible
6. Enter correct email address and password
7. Click 'login' button
8. Verify that 'Logged in as username' is visible
9. Click 'Logout' button
10. Verify that user is navigated to login page

## Test Case 5: Register User with existing email (already done)

## Test Case 6: Contact Us Form (already done)

## Test Case 7: Verify Test Cases Page
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Test Cases' button
5. Verify user is navigated to test cases page successfully

## Test Case 8: Verify All Products and product detail page
1. Navigate to home → Products page
2. Verify ALL PRODUCTS page
3. Products list is visible
4. Click View Product for first product
5. Verify product detail: name, category, price, availability, condition, brand

## Test Case 9: Search Product
1. Navigate to Products page
2. Enter product name in search and click search
3. Verify 'SEARCHED PRODUCTS'
4. Verify products visible

## Test Case 10: Verify Subscription in home page
1. Navigate to home
2. Scroll to footer
3. Verify 'SUBSCRIPTION'
4. Enter email, click arrow
5. Verify 'You have been successfully subscribed!'

## Test Case 11: Verify Subscription in Cart page
1. Navigate to home
2. Click Cart
3. Scroll to footer
4. Verify 'SUBSCRIPTION'
5. Enter email, click arrow
6. Verify 'You have been successfully subscribed!'

## Test Case 12: Add Products in Cart
1. Navigate to Products
2. Hover first product → Add to cart → Continue Shopping
3. Hover second product → Add to cart → View Cart
4. Verify both products in cart
5. Verify prices, quantity, total

## Test Case 13: Verify Product quantity in Cart
1. Click View Product for any product on home page
2. Verify product detail open
3. Increase quantity to 4
4. Add to cart
5. View Cart
6. Verify quantity = 4

## Test Case 14: Place Order - Register while Checkout
1. Add products to cart
2. Click Cart
3. Proceed to Checkout → click Register/Login
4. Fill signup details, create account, verify ACCOUNT CREATED, Continue
5. Verify Logged in as
6. Click Cart → Proceed To Checkout
7. Verify Address Details and Review Your Order
8. Enter comment → Place Order
9. Enter payment details
10. Pay and Confirm
11. Verify success
12. Delete Account → verify ACCOUNT DELETED

## Test Case 15: Place Order - Register before Checkout
1. Signup/Login → fill signup → create account → verify ACCOUNT CREATED → Continue
2. Verify Logged in
3. Add products to cart → Cart → Proceed to Checkout
4. Verify Address Details + Review
5. Comment → Place Order → Payment → Verify success
6. Delete Account → verify ACCOUNT DELETED

## Test Case 16: Place Order - Login before Checkout
1. Login with existing account
2. Verify Logged in
3. Add products → Cart → Checkout
4. Verify Address + Review
5. Comment → Place Order → Payment → Verify success
6. Delete Account → verify ACCOUNT DELETED

## Test Case 17: Remove Products From Cart
1. Add product to cart
2. Click Cart
3. Click X on product
4. Verify product removed from cart

## Test Case 18: View Category Products
1. Verify categories visible on left sidebar
2. Click Women → Click Tops (sub-category)
3. Verify category page title contains 'WOMEN - TOPS PRODUCTS'
4. Click Men → Click any sub-category
5. Verify navigated to Men category page

## Test Case 19: View & Cart Brand Products
1. Navigate to Products
2. Verify Brands visible on left sidebar
3. Click any brand → verify brand products page
4. Click another brand → verify navigated there

## Test Case 20: Search Products and Verify Cart After Login
1. Navigate to Products
2. Search for product
3. Verify SEARCHED PRODUCTS
4. Add products to cart
5. Go to Cart, verify products
6. Login (Signup/Login)
7. Go to Cart again, verify products still there

## Test Case 21: Add review on product
1. Navigate to Products → View Product
2. Verify 'Write Your Review'
3. Enter name, email, review
4. Submit
5. Verify success 'Thank you for your review.'

## Test Case 22: Add to cart from Recommended items
1. Scroll to bottom of home page
2. Verify RECOMMENDED ITEMS visible
3. Add to cart from recommended
4. View Cart
5. Verify product in cart

## Test Case 23: Verify address details in checkout page
1. Signup and create account
2. Add to cart → Cart → Checkout
3. Verify delivery and billing address matches signup data
4. Delete Account

## Test Case 24: Download Invoice after purchase order
1. Add to cart → Cart → Checkout → Register/Login → create account
2. Cart → Checkout → Place Order → Payment → Verify success
3. Download Invoice → verify downloaded
4. Continue → Delete Account → verify ACCOUNT DELETED

## Test Case 25: Verify Scroll Up using Arrow button
1. Navigate to home
2. Scroll to bottom
3. Verify SUBSCRIPTION visible
4. Click scroll-up arrow button
5. Verify 'Full-Fledged practice website for Automation Engineers' visible at top

## Test Case 26: Verify Scroll Up without Arrow button
1. Navigate to home
2. Scroll to bottom
3. Verify SUBSCRIPTION visible
4. Scroll up (via JavaScript)
5. Verify 'Full-Fledged practice website for Automation Engineers' visible
