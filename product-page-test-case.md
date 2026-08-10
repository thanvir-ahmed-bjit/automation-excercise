# Test Cases - Automation Exercise

**Application URL:** http://automationexercise.com  
**Total Test Cases:** 57



**Products, Cart, Checkout & Navigation**

- [Test Case 37: Contact Us Form](#test-case-37-contact-us-form)
- [Test Case 38: Verify Test Cases Page](#test-case-38-verify-test-cases-page)
- [Test Case 39: Verify All Products and Product Detail Page](#test-case-39-verify-all-products-and-product-detail-page)
- [Test Case 40: Search Product](#test-case-40-search-product)
- [Test Case 41: Verify Subscription in Home Page](#test-case-41-verify-subscription-in-home-page)
- [Test Case 42: Verify Subscription in Cart Page](#test-case-42-verify-subscription-in-cart-page)
- [Test Case 43: Add Products in Cart](#test-case-43-add-products-in-cart)
- [Test Case 44: Verify Product Quantity in Cart](#test-case-44-verify-product-quantity-in-cart)
- [Test Case 45: Place Order: Register while Checkout](#test-case-45-place-order-register-while-checkout)
- [Test Case 46: Place Order: Register before Checkout](#test-case-46-place-order-register-before-checkout)
- [Test Case 47: Place Order: Login before Checkout](#test-case-47-place-order-login-before-checkout)
- [Test Case 48: Remove Products From Cart](#test-case-48-remove-products-from-cart)
- [Test Case 49: View Category Products](#test-case-49-view-category-products)
- [Test Case 50: View & Cart Brand Products](#test-case-50-view-cart-brand-products)
- [Test Case 51: Search Products and Verify Cart After Login](#test-case-51-search-products-and-verify-cart-after-login)
- [Test Case 52: Add Review on Product](#test-case-52-add-review-on-product)
- [Test Case 53: Add to Cart from Recommended Items](#test-case-53-add-to-cart-from-recommended-items)
- [Test Case 54: Verify Address Details in Checkout Page](#test-case-54-verify-address-details-in-checkout-page)
- [Test Case 55: Download Invoice After Purchase Order](#test-case-55-download-invoice-after-purchase-order)
- [Test Case 56: Verify Scroll Up using 'Arrow' Button and Scroll Down Functionality](#test-case-56-verify-scroll-up-using-arrow-button-and-scroll-down-functionality)
- [Test Case 57: Verify Scroll Up without 'Arrow' Button and Scroll Down Functionality](#test-case-57-verify-scroll-up-without-arrow-button-and-scroll-down-functionality)




## Test Case 37: Contact Us Form

**Main Screen:** Contact Us Page &nbsp;|&nbsp; **Module:** Contact Us &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ContactUs#userShouldSubmitContactUsFormSuccessfully`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A file is available on the local machine to upload.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Contact Us' button.
4. Enter name, email, subject and message.
5. Upload file.
6. Click 'Submit' button.
7. Click 'OK' button on the confirmation dialog.
8. Click 'Home' button.

**Expected Results**

1. Home page is visible successfully.
2. 'GET IN TOUCH' is visible.
3. Success message 'Success! Your details have been submitted successfully.' is visible.
4. User is navigated back to the home page successfully.

**Notes:** Verified against the live site: the submit handler returns false in both branches, so the form is never actually posted to /contact_us. The 'Success!' banner and the file upload are purely client-side, and no data reaches the server. Confirm this is intended demo behaviour before treating the success message as proof of submission.

---

## Test Case 38: Verify Test Cases Page

**Main Screen:** Test Cases Page &nbsp;|&nbsp; **Module:** Navigation &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `TestCasesPageTest#verifyTestCasesPageIsAccessible`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Test Cases' button.

**Expected Results**

1. Home page is visible successfully.
2. User is navigated to the Test Cases page successfully.

---

## Test Case 39: Verify All Products and Product Detail Page

**Main Screen:** Products Page &nbsp;|&nbsp; **Module:** Products &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductTest#verifyAllProductsAndProductDetailPage`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Click on 'View Product' of the first product.

**Expected Results**

1. Home page is visible successfully.
2. User is navigated to ALL PRODUCTS page successfully.
3. The products list is visible.
4. User is landed on the product detail page.
5. Product detail is visible: product name, category, price, availability, condition, brand.

---

## Test Case 40: Search Product

**Main Screen:** Products Page &nbsp;|&nbsp; **Module:** Products / Search &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductTest#searchProductShouldShowResults`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Enter product name in the search input and click the search button.

**Expected Results**

1. Home page is visible successfully.
2. User is navigated to ALL PRODUCTS page successfully.
3. 'SEARCHED PRODUCTS' is visible.
4. All the products related to the search are visible.

---

## Test Case 41: Verify Subscription in Home Page

**Main Screen:** Home Page (Footer) &nbsp;|&nbsp; **Module:** Subscription &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `SubscriptionTest#verifySubscriptionOnHomePage`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll down to the footer.
4. Enter email address in the input and click the arrow button.

**Expected Results**

1. Home page is visible successfully.
2. Text 'SUBSCRIPTION' is visible.
3. Success message 'You have been successfully subscribed!' is visible.

---

## Test Case 42: Verify Subscription in Cart Page

**Main Screen:** Cart Page (Footer) &nbsp;|&nbsp; **Module:** Subscription &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `SubscriptionTest#verifySubscriptionOnCartPage`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Cart' button.
4. Scroll down to the footer.
5. Enter email address in the input and click the arrow button.

**Expected Results**

1. Home page is visible successfully.
2. Text 'SUBSCRIPTION' is visible.
3. Success message 'You have been successfully subscribed!' is visible.

---

## Test Case 43: Add Products in Cart

**Main Screen:** Products / Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#addProductsToCartAndVerify`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Products' button.
4. Hover over the first product and click 'Add to cart'.
5. Click 'Continue Shopping' button.
6. Hover over the second product and click 'Add to cart'.
7. Click 'View Cart' button.

**Expected Results**

1. Home page is visible successfully.
2. Both products are added to the Cart.
3. Their prices, quantity and total price are displayed correctly.

---

## Test Case 44: Verify Product Quantity in Cart

**Main Screen:** Product Detail / Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#verifyProductQuantityInCart`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'View Product' for any product on the home page.
4. Increase quantity to 4.
5. Click 'Add to cart' button.
6. Click 'View Cart' button.

**Expected Results**

1. Home page is visible successfully.
2. Product detail is opened.
3. Product is displayed on the cart page with the exact quantity (4).

---

## Test Case 45: Place Order: Register while Checkout

**Main Screen:** Cart / Checkout Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#placeOrderRegisterWhileCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Add products to cart.
4. Click 'Cart' button.
5. Click 'Proceed To Checkout'.
6. Click 'Register / Login' button.
7. Fill all details in Signup and create account.
8. Click 'Continue' button.
9. Click 'Cart' button.
10. Click 'Proceed To Checkout' button.
11. Enter description in the comment text area and click 'Place Order'.
12. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
13. Click 'Pay and Confirm Order' button.
14. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. Cart page is displayed.
3. 'ACCOUNT CREATED!' is visible.
4. 'Logged in as username' is visible at the top.
5. Address Details and Review Your Order are displayed.
6. Success message 'Your order has been placed successfully!' is visible.
7. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 46: Place Order: Register before Checkout

**Main Screen:** Signup / Checkout Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#placeOrderRegisterBeforeCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Signup / Login' button.
4. Fill all details in Signup and create account.
5. Click 'Continue' button.
6. Add products to cart.
7. Click 'Cart' button.
8. Click 'Proceed To Checkout'.
9. Enter description in the comment text area and click 'Place Order'.
10. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
11. Click 'Pay and Confirm Order' button.
12. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'ACCOUNT CREATED!' is visible.
3. 'Logged in as username' is visible at the top.
4. Cart page is displayed.
5. Address Details and Review Your Order are displayed.
6. Success message 'Your order has been placed successfully!' is visible.
7. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 47: Place Order: Login before Checkout

**Main Screen:** Login / Checkout Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#placeOrderLoginBeforeCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Signup / Login' button.
4. Fill email, password and click 'Login' button.
5. Add products to cart.
6. Click 'Cart' button.
7. Click 'Proceed To Checkout'.
8. Enter description in the comment text area and click 'Place Order'.
9. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
10. Click 'Pay and Confirm Order' button.
11. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Logged in as username' is visible at the top.
3. Cart page is displayed.
4. Address Details and Review Your Order are displayed.
5. Success message 'Your order has been placed successfully!' is visible.
6. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 48: Remove Products From Cart

**Main Screen:** Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#removeProductFromCart`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Add products to cart.
4. Click 'Cart' button.
5. Click 'X' button corresponding to a particular product.

**Expected Results**

1. Home page is visible successfully.
2. Cart page is displayed.
3. Product is removed from the cart.

---

## Test Case 49: View Category Products

**Main Screen:** Home / Category Page &nbsp;|&nbsp; **Module:** Products / Categories &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CategoryBrandTest#viewCategoryProducts`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Women' category.
4. Click on any category link under 'Women' category (e.g., Dress).
5. On the left side bar, click on any sub-category link of 'Men' category.

**Expected Results**

1. Categories are visible on the left side bar.
2. Category page is displayed and text 'WOMEN - TOPS PRODUCTS' is confirmed.
3. User is navigated to the selected 'Men' category page.

---

## Test Case 50: View & Cart Brand Products

**Main Screen:** Products / Brand Page &nbsp;|&nbsp; **Module:** Products / Brands &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CategoryBrandTest#viewBrandProducts`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Click on any brand name.
5. On the left side bar, click on any other brand link.

**Expected Results**

1. Brands are visible on the left side bar.
2. User is navigated to the brand page and brand products are displayed.
3. User is navigated to the other brand page and can see its products.

---

## Test Case 51: Search Products and Verify Cart After Login

**Main Screen:** Products / Cart Page &nbsp;|&nbsp; **Module:** Cart / Search &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductReviewTest#searchProductsAndVerifyCartAfterLogin`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists for login.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Enter product name in the search input and click the search button.
5. Add those products to cart.
6. Click 'Cart' button.
7. Click 'Signup / Login' button and submit login details.
8. Go to the Cart page again.

**Expected Results**

1. User is navigated to ALL PRODUCTS page successfully.
2. 'SEARCHED PRODUCTS' is visible.
3. All the products related to the search are visible.
4. Products are visible in the cart.
5. Those products are still visible in the cart after login.

---

## Test Case 52: Add Review on Product

**Main Screen:** Product Detail Page &nbsp;|&nbsp; **Module:** Products / Review &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductReviewTest#addReviewOnProduct`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Click on 'View Product' button.
5. Enter name, email and review.
6. Click 'Submit' button.

**Expected Results**

1. User is navigated to ALL PRODUCTS page successfully.
2. 'Write Your Review' is visible.
3. Success message 'Thank you for your review.' is visible.

**Notes:** Verified against the live site: the success banner is displayed for only about 2 seconds before the page re-hides it, and its container renders with zero height. Any verification must target the inner success alert within that window.

---

## Test Case 53: Add to Cart from Recommended Items

**Main Screen:** Home / Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#addToCartFromRecommendedItems`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll to the bottom of the page.
4. Click on 'Add To Cart' on a recommended product.
5. Click on 'View Cart' button.

**Expected Results**

1. 'RECOMMENDED ITEMS' are visible.
2. Product is displayed on the cart page.

---

## Test Case 54: Verify Address Details in Checkout Page

**Main Screen:** Checkout / Address Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#verifyAddressDetailsInCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Signup / Login' button.
4. Fill all details in Signup and create account.
5. Click 'Continue' button.
6. Add products to cart.
7. Click 'Cart' button.
8. Click 'Proceed To Checkout'.
9. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'ACCOUNT CREATED!' is visible.
3. 'Logged in as username' is visible at the top.
4. Cart page is displayed.
5. The delivery address matches the address entered during account registration.
6. The billing address matches the address entered during account registration.
7. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 55: Download Invoice After Purchase Order

**Main Screen:** Checkout / Invoice &nbsp;|&nbsp; **Module:** Checkout / Invoice &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#downloadInvoiceAfterPurchase`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Add products to cart.
4. Click 'Cart' button.
5. Click 'Proceed To Checkout'.
6. Click 'Register / Login' button.
7. Fill all details in Signup and create account.
8. Click 'Continue' button.
9. Click 'Cart' button.
10. Click 'Proceed To Checkout' button.
11. Enter description in the comment text area and click 'Place Order'.
12. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
13. Click 'Pay and Confirm Order' button.
14. Click 'Download Invoice' button.
15. Click 'Continue' button.
16. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. Cart page is displayed.
3. 'ACCOUNT CREATED!' is visible.
4. 'Logged in as username' is visible at the top.
5. Address Details and Review Your Order are displayed.
6. Success message 'Your order has been placed successfully!' is visible.
7. Invoice is downloaded successfully.
8. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 56: Verify Scroll Up using 'Arrow' Button and Scroll Down Functionality

**Main Screen:** Home Page &nbsp;|&nbsp; **Module:** Navigation / UI &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Automated — `ScrollTest#verifyScrollUpUsingArrowButton`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll down the page to the bottom.
4. Click on the arrow at the bottom right side to move upward.

**Expected Results**

1. Home page is visible successfully.
2. 'SUBSCRIPTION' is visible.
3. Page is scrolled up and the text 'Full-Fledged practice website for Automation Engineers' is visible on screen.

---

## Test Case 57: Verify Scroll Up without 'Arrow' Button and Scroll Down Functionality

**Main Screen:** Home Page &nbsp;|&nbsp; **Module:** Navigation / UI &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Automated — `ScrollTest#verifyScrollUpWithoutArrowButton`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll down the page to the bottom.
4. Scroll up the page to the top.

**Expected Results**

1. Home page is visible successfully.
2. 'SUBSCRIPTION' is visible.
3. Page is scrolled up and the text 'Full-Fledged practice website for Automation Engineers' is visible on screen.

---
