# supermarket-smart-scale

Supermarket Smart Scale is a desktop application imitating a real-life electronic scale for self-service. Since the users can't actually weigh the products, they're required to enter the weight manually.

# Features:

- Csutomers can browse the fruits and vegetabes section to choose a product they wish to buy, enter the weight and click "Get Receipt" which generates a PDF of the receipt with information about the product they've chosen and the total money after calculation.

- Authentication system. 

    - Two types of users: administrator and employee.


    - Admin can add/update/delete products and users info, has access to logbook which keeps track of the changes made to the database.
   

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

Create the **_supermarket_** schema on the database tool, and import the database from the SQL file, or copy the SQL code.

Download the repository files (project) from the download section or clone this project.

Import it in Intellij IDEA or any other Java IDE and let Maven download the required dependencies for you.

Change database access settings in _**src/main/java/com/smartscale/database/DatabaseConnection.java**_ with your credentials.

Run the Program using **_Main.java._**
