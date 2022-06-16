# supermarket-smart-scale

Supermarket Smart Scale is a desktop application imitating a real-life electronic scale for self-service. Since the users can't actually weigh the products, they're required to enter the weight manually.

# Features:

- Customers can browse the fruits and vegetabes section to choose a product they wish to buy, enter the weight and click "Get Receipt" which generates a PDF of the receipt with information about the product they've chosen and the total money after calculation.

    https://user-images.githubusercontent.com/100995573/174034689-82fb27a5-0eda-4307-8c74-2203214317b8.mp4


    _File: receipt_050425_PM_15062022.pdf_

    ![Screenshot](https://github.com/yudzhel/supermarket-smart-scale/blob/master/screens/receipt.png)

- Authentication system. 

    - Two types of users: admin and employee.



    https://user-images.githubusercontent.com/100995573/174035067-07789ea5-d9c5-454a-a3bf-a90891b56bff.mp4


    - Admin can add/update/delete products and users info, has access to "Recent Activity" which keeps track of the changes made to the database.
   
   
   
    https://user-images.githubusercontent.com/100995573/174035136-c0648014-d69f-431d-aed5-74b9911ec6ac.mp4

    - Employees can add/update and delete products.

- Data stored in a MySQL database.

# Tools & Technologies:

- Java [JDK 17]
- JavaFX
- iText 7 Core - PDF Library
- MySQL
- CSS
- IntelliJ IDEA
- Scene Builder
- Maven

# Set Up Instructions For Running Application

Create the **_supermarket_** schema on the database tool, and import the database from the SQL file (**_db/supermarket.sql_**), or copy the SQL code.

Download the repository files (project) from the download section or clone this project.

Import it in Intellij IDEA or any other Java IDE and let Maven download the required dependencies for you.

Change database access settings in _**src/main/java/com/smartscale/database/DatabaseConnection.java**_ with your credentials.

Run the Program using **_Main.java._**
