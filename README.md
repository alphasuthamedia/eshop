# Advance Programming Course
This repository contains the modules, tutorials, and exercises for the Advance Programming Course.

## Tutorial 1
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

## Tutorial 2
### Reflection 1
1. After integrating sonarcloud and pmd to my github branch, i notice that there were some error happens. Indeed that i have to fix it, but it wasnt the critical erros. Some of them are like :
    * Unused Import
      Sometimes when i code, i have to import some packages. But then, at the end i havent use that package(s), it isnt because i do randomg import but, i found the better library or packages that i confident to use it. That is the biggest reason why there is so much erros because unused import.
    * Wrong Dependency Configuretion
    * At first, i thought i want to go with sonarcloud. The reason is simple, it is widely used in current industrial standard. But then, something unusual happens, my test facing errors and its because the sonarcloud server terminates it. After some review (gsgs : google sana gooogle sini), sonarcloud only allow us (as a free user) to aotomate testing using a single branch. And then, i quickly swap using pmd because i havent get gitlab csui account for sonarcube yet. It became a reason why my build dependency was messed up. I haavent remove the build dependency for sonarcloud yet... now its fixed.

2. Ya. After some review, i hereby decree that my project has qualified as continuous integration and continuous deployment industry standard. Why? the reason is :
    * Continuous Integraion : there is a file named as "ci.yaml" that build (gradle) everytime every push and pull request. ci.yaml also triggers jacoco test by running test (the whole test). So that, my integrations was automaticly triggered by that file.
    * Continuous Deployment : since im using koyeb, it has automatic configuration to let out program deployed on every push (main branch).  Then i was successfully implementing the continuous deployment.

[!https://private-alikee-alphasuthamedia-920b7801.koyeb.app/] : just try, click it. i promise, you wont be disappointed. just click it.

![image](https://github.com/user-attachments/assets/6e130c67-1a6b-49da-b6f0-9ca990453c5d)

## Tutorial 3
### Reflection 1
Before i wrote the reflection, i guess it is better to note what SOLID principle is.
SOLID :
1. Single Responsibility Principle
2. Open/closed Principle
3. Liskov Substitution Principle
4. Interface Segregation Principle
5. Dependency Inversion Principle

Lets inspect to my code... To implement Single Responsibility Principle, we have to know what Single Responsibility Principle is.. Its a principle where defining that A class os any object only responbible for one small task. Here in my code, i noticed that theres CarController class inside Product Controler. Smells weird, because a class should only responsible for one small task.. So then i splitted the Controller (between) Product Controller and Car Controller.
Lets Have a look at Open/closed Princple. Its a princple that we have to maintain or make our clode is implementable or developeable but closed to get modification. By creating a service interface that can be implementable for any types of item, i've already pass this principle. The reason is, with using a single interface (Service.java) it can be implemented for CarService and ProductService so it is so.. developable but with general service function, we dont have to consider to modify that base service class, because it wont be nyangkut for any java object/class that will implemented using that base interface.
Interface that we've been create, is 100% implementable with any service for any item. it has to be implemented 100% because it is only general method and weve ensure that the functionality has to derived aka implemented for any class or object that iimplements that base class. So, by this fact.. Weve already implements Liskov Segregation Princple.
Since the service that weve built in simple enough (not such a large interface) so weve already pass Interface Segregation Principle. it is because our interface isnt the enourmous big and what method(s) that available in that intrface is genuinely required by any other service type that implements them.
Last but not least, Insted of just creating concrete class, ive impelemented service interface<T>. T can be anything, or as a java devs we said that T is an Object.. So T can be Car or a Product. Using this way, weve implemented Dependency Inversion Princple, because high-level modules do not depend on low-level modules.

After implementing SOLID my code is absolutely readable. It is easy to understand the relationship between superclass subclass relationship because with the principle that we can not modify the superclass. The downside of not impelementing solid can be known before im doing solid refactoring. My code is not so readable, because there is unmatched dependency or tendention in my code such as a car controller in product controller, it makes hard to read an even harder to understand the projuct structuer. After im implementing solid, i can tell that the main difference before and after implementingn solid is, my code is absolutely readable (at least more readable) and more easier to understand my code structure.

## Tutorial 4
### Reflection 1
Setelah mengikuti Tutorial Modul 4, saya merasa pendekatan Test-Driven Development (TDD) memberikan banyak keuntungan dalam proses pengembangan perangkat lunak. Dengan memulai dengan menulis unit test untuk skenario positif dan negatif, kita dapat menetapkan ekspektasi yang jelas terhadap logika program. Pendekatan ini membantu mengurangi kemungkinan adanya bug karena setiap bagian kode sudah diuji terlebih dahulu sebelum digabungkan dengan proyek utama, sehingga bug dapat ditemukan lebih awal.

Penerapan TDD yang tepat seharusnya membuat proses pengembangan menjadi lebih efisien. Dengan deteksi dan perbaikan bug di tahap awal, tim pengembang tidak perlu menghabiskan waktu untuk debugging yang intensif selama proses pengembangan. Selain itu, jika TDD diterapkan dengan baik, jumlah bug yang muncul setelah peluncuran aplikasi akan lebih sedikit. Tanpa TDD, kemungkinan besar akan ada lebih banyak bug yang harus ditemukan dan diperbaiki, bahkan oleh pengguna setelah produk diluncurkan. Oleh karena itu, TDD merupakan pendekatan yang sangat efektif untuk membuat pengembangan perangkat lunak menjadi lebih efisien secara keseluruhan.

Prinsip FIRST dalam TDD juga diterapkan dengan baik dalam proses ini:

- **Fast (Cepat):** Proses pengujian berlangsung cepat karena menggunakan mock, yang menggantikan operasi database asli yang cenderung memakan waktu.
- **Independent (Mandiri):** Setiap pengujian dirancang secara independen, memastikan bahwa setiap tes fokus pada satu logika tertentu dengan data yang sudah dipersiapkan sebelumnya, sehingga menghindari ketergantungan antar tes.
- **Repeatable (Dapat Diulang):** Hasil pengujian konsisten berkat penggunaan data pengujian yang tetap dan repositori buatan yang menghindari interaksi langsung dengan codebase asli.
- **Self-validating (Validasi Diri):** Proses penilaian hasil tes menjadi lebih mudah dengan adanya assertion yang jelas, yang menentukan apakah tes tersebut lulus atau gagal.

Meskipun cakupan pengujian sudah cukup baik, masih ada ruang untuk perbaikan, seperti menambah pengujian parameterized dan memperluas cakupan untuk edge cases. Pendekatan TDD tidak hanya membantu mendeteksi bug lebih awal, tetapi juga dapat meningkatkan kualitas kode secara keseluruhan dan mempercepat siklus pengembangan.