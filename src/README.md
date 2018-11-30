Project Checkout according to requirements from require.pdf file included below.
Project is build with Rest Full architecture with out GUI.

Run project:
mvn spring-boot:run

Run unit tests:
mvn clean test

Checkout API documentation is provided by swagger and available after run project on URL: 
host:port/swagger-ui.html  (e.g: localhost:8080/swagger-ui.html)

Database H2 in memory available after run project on URL:
host:port/h2-console   (e.g: localhost:8080/h2-console)

Service provide some functionality

Price strategy (DEFAULT, PRICE_PER_TYPE ) available via REST API (available for extends)
    get strategy - get present strategy 
    set strategy - set other one strategy
    
    
Price Discounts
    save - save new price discount
    update - update price discount
    delete - delete price discount
    find all discount - find all price discounts 
    
Item 
    save - save new item
    update - update item
    delete - delete item
    find item by type - search item by type (type is unique)
    find all - find all items
    
Basket
    open - open new basket 
    update - update busket
    close - close basket and return total price
    find by id - find basket by basket id
    