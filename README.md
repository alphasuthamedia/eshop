# Advance Programming Course
This repository contains the modules, tutorials, and exercises for the Advance Programming Course.


### Reflection 1: Clean Code and Secure Coding Practices
#### **Clean Code Principles**
1. **Meaningful Names**
  * Using meaningful name helps to improve readability, clearness. Bad naming function, args, class, etc. can make us unsure is the right method is called, and it can let us missusing a method.
  * Ex:
    ```java
    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        if (service.create(product) != null) {
            return "redirect:list";
        }

        model.addAttribute("product", product);
        model.addAttribute("positive_value_error", "Please input a positive value for quantity");
        return "CreateProduct";
    }
      ```
    * `createProductPost` is a method to handle post method while creating a product, states clearly, only post request

2. **Functions (Methods) Should Do One Thing**
   * Every function has to do a thing, otherwise their uses are mixed

   * Example:
      ```java
     void setUp() {
        Product product = new Product();
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
     }
     ``` 
     * That method will only use as a setup for a product, at each test operation

3. **Use of Objects and Data Structures**
   * Using OOP and  Good data structure, allows us to perform kind hard operation. It is easy, neat, and easy to debug other than not using that principle.
   * Example:
      ```java
     @Setter @Getter
     public class Product {
         private String productID;
         private String productName;
         private int productQuantity;
      
      public void setProductID(String productID) {
        this.productID = productID;
         }
     }
     ```
     
4. **Error Handling**
    *  Issue: If a product ID does not exist, the app can handles it.
    * Improvement:
       ```java
       public String editProductPage(@RequestParam(value = "id", required = false) String ProductID,Model model) {
           Product product = service.getProductByID(ProductID);

           // Prevent IDOR :) if product ID didnt exist
           if (product == null) {
               return "redirect:https://www.youtube.com/watch?v=dQw4w9WgXcQ";
           }

           model.addAttribute("product", product);

           return "EditProduct";
       }
      ```
        * This improvement can handle IDOR attack btw
        * 
#### **Secure Coding Practices**
1. **Input Validation**
   * Proper input validation, sometimes it has to be filtered to prevent malicious attacks
   * Issue: The current code does not validate input when creating a product.
   * Improvement: Add simple validation at the repository, when quantity <= 0, prompt user to type again the correct way
      ```java
      public Product create(Product product) {
        if (product.getProductQuantity() > 0) {
            productData.add(product);
            return product;
        }
        return null;
      }
     ```
2. **Output Data Encoding**
   * Properly encoding output data helps us to prevent XSS attacks.
   * Issue: The current code does not encode product names when displaying them.
   * Improvement: Use Thymeleaf’s `th:text` attribute to encode product names.
      ```html
     <tr th:each="product: ${products}" th:class="${product.getProductName()+','+product.getProductQuantity()}">
        <td th:text="${#strings.escapeXml(product.productName)}"></td>
        <td th:text="${product.productQuantity}"></td>
           <td>
              <div th:object="${product}">
                  <a id="edit-button" class="btn btn-primary btn-sm" style="margin-right: 12px" th:href="@{edit(id = ${product.getProductID()})}">Edit Product</a>
                  <button id="delete-button" class="btn btn-primary btn-sm" style="color: aliceblue" th:attr="onclick=|deleteProduct('*{productID}')|" >Delete Product</button>
              </div>
          </td>
     </tr>
     ```
        * `th:text="${#strings.escapeXml(product.productName)"` encodes the product name.

### Reflection 2: Unit Testing
#### **Unit Testing Principles**
1. **Feelings about Unit Testing**
   * Writing test can be challenging, it required us to do double job, and sometimes its boring to guess the edge cases.
   * But, the essence of unit test is to make us confident enough to say that our code is good.
   * With Unit testing we can avoid something that can be missues or any error happens.
2. **Quantity of Unit Testing**
   * The number of the unit test shall be match with how much functional thing we use on our system.
   * Each test should cover a specific scenario, such as:
     * Expected behavior
     * Edge cases
     * Error conditions
     * Integration with other components
     * Sometimes, we had to test input serealizer to check if there is an open gate that can let attacker to attack our system
3. **Code Coverage and Test Sufficiency**
   * Code coverage is a useful metric to track if our system is covered up.
   * Achieving 100% code coverage does not ensure bug-free code because:
     * Tests may not account for every possible input combination
     * Logic errors can still occur even with complete path coverage
     * Integration problems might go undetected
     * Edge cases could be overlooked even with full line coverage
   * Quality tests should concentrate on:
     * Assessing business logic 
     * Verifying edge cases 
     * Ensuring proper error handling 
     * Validating integration points
4. **Functional Test Code Cleanliness Issues**
   * Code Duplication:
     * Setup procedures that can be used in the whole class
     * Reuse the procedure in other function
   * Reasons for Concern:
     * Violates DRY (Don't Repeat Yourself) principle
     * The inconsistency is so high, it becomes hard to be maintained
     * Reduces code readability
   * Suggested Improvements:
     1. **Create a setup Function that can be called anytime using decorator**
        ```java
        void setUp() {
            Product product = new Product();
            product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
            product.setProductName("Sampo Cap Bambang");
            product.setProductQuantity(100);
            productRepository.create(product);
        }
        ``` 
     2. **Reusing the base setup function**
        ```java
        @Test
        void testEditProduct() {
            Iterator iterator = productRepository.findAll();

            Product selectedProduct = null;
            while (iterator.hasNext()) {
                if (iterator.next().equals("eb558e9f-1c39-460e-8860-71af6af63bd6")) {
                    selectedProduct = (Product) iterator.next();
                    break;
                }
            }

            productRepository.delete(selectedProduct);

            while (iterator.hasNext()) {
                if (iterator.next().equals("eb558e9f-1c39-460e-8860-71af6af63bd6")) {
                    Product testSelectedProduct = (Product) iterator.next();
                    testSelectedProduct.setProductName("Sampo Cap Bambang 2");
                    testSelectedProduct.setProductQuantity(1000);

                    break;
                }
            }

            while (iterator.hasNext()) {
                if (iterator.next().equals("eb558e9f-1c39-460e-8860-71af6af63bd6")) {
                    selectedProduct = (Product) iterator.next();
                    assertEquals("Sampo Cap Bambang 2", selectedProduct.getProductName());
                    assertEquals(1000, selectedProduct.getProductQuantity());
                    break;
                }
            }
        }
        ```
   * These improvements would:
     * Reduce code duplication
     * Improve readibility and maintainability
     * Make it easier to understand and add test cases