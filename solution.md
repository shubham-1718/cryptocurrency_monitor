# Cryptocurrency Monitor - Solution Description
## Project Overview
The Cryptocurrency Monitor is a Java web application that fetches the current localized price of a cryptocurrency. The application allows users to select a cryptocurrency and provides an optional IP address to retrieve the price. The application displays the price with the currency symbol and correct decimal separator. It also includes a login functionality and a conversion history feature to track previous conversions.

### Tech Stack and Dependencies Used
The project uses the following dependencies (mentioned in the pom.xml file):

- MySQL Connector Java 8.0.19
- Spring Web MVC 5.1.0.RELEASE
- Spring Test 5.1.0.RELEASE
- JSTL API 1.2.1
- Standard Taglibs 1.1.2
- Java Servlet API 3.1.0
- JavaServer Pages API 2.3.1
- Gson 2.8.6
- Commons IO 2.5
- JSON 20190722
- Apache Commons Lang3 3.12.0
- JUnit Jupiter API 5.7.2
- JUnit Jupiter Engine 5.7.2
- Mockito Core 3.12.4
- JUnit 4.12

## Implementation Approach
The project consists of several components including JSP files, model classes, helper classes, DAO (Data Access Object), and controllers. Here's a breakdown of the main components:

### JSP Files
1. **userlogin.jsp**: Provides a login page for users to authenticate.
2. **cryptocurrencyPrice.jsp**: The landing page that includes a dropdown to select a cryptocurrency and an input field to provide an optional IP address.
3. **conversionHistory.jsp**: Displays the conversion history of the user.
### Model Classes
1. **ConversionHistory**: Represents a conversion history record and is responsible for storing information such as the date, from price, to price, and the value in which it was converted.
### Helper Classes
1. **StringUtils**: Stores useful strings or data used throughout the application.
2. **StatusReport**: Stores the response from hitting a URL in the controller.
3. **DatabaseInitializer**: Handles the initialization of the database connection.
### DAO (Data Access Object)
1. **ConversionHistoryDAO**: Contains various functions with SQL queries to store or retrieve conversion history records from the database.
### Controllers
1. **CryptoCurrencyRequest**: Handles the request from the user to fetch the current price of a cryptocurrency. It constructs a URL based on the requested cryptocurrency and retrieves the price from the CoinCap API.
2. **CurrencyCodeRequest**: Retrieves the locale information based on the user-provided IP address or the request's IP address. It constructs a URL and retrieves the currency code from the IPData API.
3. **ExchangeRateRequest**: Retrieves the exchange rate based on the currency code. It constructs a URL and retrieves the exchange rate from the Open Exchange Rates API.
### Testing
The application includes unit tests written using JUnit and Mockito. The tests cover various functionalities and ensure the correctness of the application.

### Assumptions or Approach
1. The project assumes that the necessary database (MySQL) and tables are already set up.
2. The APIs used in the project (CoinCap, IPData, Open Exchange Rates) are assumed to be accessible and functional.
### Build and Run Instructions
1. Clone the project repository from GitHub or a similar platform.
2. Ensure that the required dependencies mentioned in the pom.xml file are available.
3. Set up the MySQL database and create the necessary tables using the provided SQL queries.
4. Configure the database connection details in the DatabaseInitializer class.
5. Build the project using Maven or an IDE that supports Maven.
6. Deploy the generated WAR file to a servlet container such as Apache Tomcat.
7. Access the application by navigating to the appropriate URL in a web browser.
### CI/CD
The project includes CI/CD (Continuous Integration/Continuous Deployment) to automate the build and deployment process. Jenkins, a popular CI/CD tool, is used to set up a pipeline that fetches the latest code from the repository, builds the project, runs tests, create docker image and deploys it to DockerHub.

### Conclusion
The Cryptocurrency Monitor is a Java web application that provides localized cryptocurrency prices with a user-friendly interface. It incorporates login functionality, conversion history tracking, and integration with external APIs. The solution leverages JSP, servlets, DAO pattern, and various helper classes to achieve the desired functionality. The inclusion of tests ensures the reliability and correctness of the application.



