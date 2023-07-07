# Order Management System

This Order Management System is a simple web application built using Spring Boot and follows the principles of 
Test Driven Development (TDD). It allows users to handle basic order management functionalities such as creating, 
reading, updating, and deleting orders.

## Functionality

The Order Management System provides the following functionalities:

1. Creating a New Order: Users create a new order by providing the order details; customer name, order 
date, shipping address, and total order amount. Upon successful creation, the order gets saved in the H2 database.

2. Reading Order Details: Users can retrieve the details of a specific order by providing the order ID. The application
will return the order details if the order is found in the database.

3. Updating an Existing Order: Users can update the details of an existing order by specifying the order ID and 
providing the updated order details. The application will validate the input and update the order in the database.

4. Deleting an Order: Users can delete an order by specifying the order ID. The application will remove the order from
the database.

## API Endpoints

The application exposes the following RESTful API endpoints:

1. POST /orders: Create a new order by providing the order details in the request body. Returns the created order with a 
generated ID.

2. GET /orders: Retrieve all orders. Returns a list of all orders in the system.

3. GET /orders/{id}: Retrieve the details of a specific order by providing the order ID in the path. Returns the order 
details if found, or a 404 error if the order is not found.

4. PUT /orders/{id}: Update the details of an existing order by providing the order ID in the path and the updated order 
details in the request body. Returns the updated order details if the order is found, or a 404 error if the order is not found.

5. DELETE /orders/{id}: Delete an order by providing the order ID in the path. Returns a success message if the order is 
deleted successfully, or a 404 error if the order is not found.

## Validation

The application applies validation on the order entity to ensure data integrity. The following validations are implemented:

1. The customerName field should not be empty.

2. The orderDate field is required.

3. The shippingAddress field should not be empty.

4. The total field should be a positive value.

## Error Handling

The application handles errors and exceptions. It provides proper error responses in case of invalid requests or failed 
operations. The following scenarios are handled:

1. If an order is not found, the application returns a 404 error with an appropriate error message.
2. If there are validation errors, the application returns a 400 error with a detailed error message.
3. For any other unexpected errors, the application returns a 500 error with an error message.


