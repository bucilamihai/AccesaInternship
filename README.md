# 🛒 **PriceComparator**
A Spring Boot application for comparing product prices across different shops, tracking discounts and optimizing shopping baskets.
# 📖 Overview
This project is designed to help users find the best prices for products across various shops, track discounts and optimize their shopping baskets based on current prices. It includes features like price history tracking, discount alerts and basket optimization.

---

# 📁 Project Structure Overview

```
price_comparator/
├── controller/     → REST controllers (endpoints)
├── service/        → Business logic
├── repository/     → JPA Repositories
├── domain/         → Entities
├── dto/            → DTOs 
├── mapper/         → Mappers for converting between entities and DTOs  
├── init/           → Initialization logic (e.g., loading catalogs from csv, loading alerts from txt)  
├── utils/          → Utility classes (e.g., for math operations)
```

---

# ⚙️ Build & Run Instructions

## Steps
```bash
git clone https://github.com/bucilamihai/AccesaInternship.git
cd AccesaInternship/price-comparator
./gradlew bootRun
```
## View database in H2 Console (browser)
H2 Console: http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:price_comparator_db`  
Username: `user`

---

# ✅ Implemented Features & Endpoints

## 🔥 `/discounts/new?date={date}`
Returns new discounts added on the specified date.  
**Query param `date`**: Format `yyyy-MM-dd` (e.g., `2025-05-10`).

## 🏆 `/discounts/best?limit={optional}`
Returns the best discounts sorted by percentage, descending.  
If the `limit` query param is present, it limits the result (e.g. `limit=5` for top 5).  
**Query param `limit`**: Optional, if not provided it will return all discounts.

---

## 📊 `/catalog/price-history?productId={id}`
Returns the product’s price history over time.  
Useful for plotting **price evolution charts**.

### Response:
<details>
  <summary>Click to view JSON</summary>

```json
[
    {
        "id": "P003",
        "name": "iaurt grecesc",
        "brand": "Olympus",
        "category": "lactate",
        "unit": "kg",
        "quantity": 0.4,
        "priceHistory": [
            {
                "shopName": "Kaufland",
                "date": "2025-05-01",
                "price": 10.62,
                "currency": "RON",
                "discountPercentage": 10
            },
            {
                "shopName": "Kaufland",
                "date": "2025-05-02",
                "price": 10.62,
                "currency": "RON",
                "discountPercentage": 10
            },
            {
                "shopName": "Kaufland",
                "date": "2025-05-03",
                "price": 10.62,
                "currency": "RON",
                "discountPercentage": 10
            },
            ...
        ]
    },
    {
        "id": "P002",
        "name": "iaurt grecesc",
        "brand": "Lidl",
        "category": "lactate",
        "unit": "kg",
        "quantity": 0.4,
        "priceHistory": [
            {
                "shopName": "Profi",
                "date": "2025-05-01",
                "price": 11.5,
                "currency": "RON",
                "discountPercentage": 0
            },
            {
                "shopName": "Profi",
                "date": "2025-05-02",
                "price": 11.5,
                "currency": "RON",
                "discountPercentage": 0
            },
            {
                "shopName": "Profi",
                "date": "2025-05-03",
                "price": 11.5,
                "currency": "RON",
                "discountPercentage": 0
            },
            ...
        ]
    },
    ...
]
```
</details>  

---

## 🧠 `/catalog/optimise-basket?date={date}`
Optimizes the basket cost by finding the cheapest products on a given date.  
**Query param `date`**: Format `yyyy-MM-dd` (e.g., `2025-05-10`).


### Request body:
<details>
  <summary>Click to view JSON</summary>

```json
{
    "items": [
        { "productName": "ulei floarea-soarelui", "quantity": 2},
        { "productName": "spaghetti nr.5", "quantity": 1},
        { "productName": "ouă mărimea M", "quantity": 3},
        { "productName": "hârtie igienică 3 straturi", "quantity": 2},
        { "productName": "vin alb demisec", "quantity": 1}
    ]
}
``` 
</details>

---

### Response:
<details>
  <summary>Click to view JSON</summary>

```json
{
    "totalPrice": 107.42,
    "items": [
        {
            "productId": "P028",
            "name": "ulei floarea-soarelui",
            "category": "alimente de bază",
            "brand": "Bunica",
            "quantity": 1.0,
            "unit": "l",
            "price": 7.78,
            "currency": "RON",
            "shopName": "Lidl",
            "catalogDate": "2025-05-10"
        },
        {
            "productId": "P020",
            "name": "spaghetti nr.5",
            "category": "paste făinoase",
            "brand": "Barilla",
            "quantity": 500.0,
            "unit": "g",
            "price": 4.85,
            "currency": "RON",
            "shopName": "Lidl",
            "catalogDate": "2025-05-10"
        },
        {
            "productId": "P007",
            "name": "ouă mărimea M",
            "category": "ouă",
            "brand": "Din Ogradă",
            "quantity": 10.0,
            "unit": "buc",
            "price": 11.25,
            "currency": "RON",
            "shopName": "Profi",
            "catalogDate": "2025-05-10"
        },
        {
            "productId": "P045",
            "name": "hârtie igienică 3 straturi",
            "category": "produse de menaj",
            "brand": "Motto",
            "quantity": 10.0,
            "unit": "role",
            "price": 16.28,
            "currency": "RON",
            "shopName": "Profi",
            "catalogDate": "2025-05-10"
        },
        {
            "productId": "P051",
            "name": "vin alb demisec",
            "category": "băuturi",
            "brand": "Cotnari",
            "quantity": 0.75,
            "unit": "l",
            "price": 20.7,
            "currency": "RON",
            "shopName": "Profi",
            "catalogDate": "2025-05-10"
        }
    ]
}
```
</details>

---

## 🚨 `/alerts/triggered`
Returns alerts that were triggered based on pre-loaded preferences from the `price_alerts.txt` file.

### Response:
<details>
  <summary>Click to view JSON</summary>
  
```json
[
    {
        "productName": "iaurt grecesc",
        "targetPrice": 10.65,
        "userEmail": "mihai@gmail.com",
        "pricedProducts": [
            {
                "productId": "P002",
                "name": "iaurt grecesc",
                "category": "lactate",
                "brand": "Lidl",
                "quantity": 0.4,
                "unit": "kg",
                "price": 10.24,
                "currency": "RON",
                "shopName": "Profi",
                "catalogDate": "2025-05-05"
            },
            {
                "productId": "P002",
                "name": "iaurt grecesc",
                "category": "lactate",
                "brand": "Lidl",
                "quantity": 0.4,
                "unit": "kg",
                "price": 10.24,
                "currency": "RON",
                "shopName": "Profi",
                "catalogDate": "2025-05-06"
            },
            {
                "productId": "P002",
                "name": "iaurt grecesc",
                "category": "lactate",
                "brand": "Lidl",
                "quantity": 0.4,
                "unit": "kg",
                "price": 10.24,
                "currency": "RON",
                "shopName": "Profi",
                "catalogDate": "2025-05-07"
            },
            ...
        ]
    },
    {
        "productName": "vin alb demisec",
        "targetPrice": 21.0,
        "userEmail": "alin@gmail.com",
        "pricedProducts": [
            {
                "productId": "P051",
                "name": "vin alb demisec",
                "category": "băuturi",
                "brand": "Cotnari",
                "quantity": 0.75,
                "unit": "l",
                "price": 20.7,
                "currency": "RON",
                "shopName": "Profi",
                "catalogDate": "2025-05-08"
            },
            {
                "productId": "P051",
                "name": "vin alb demisec",
                "category": "băuturi",
                "brand": "Cotnari",
                "quantity": 0.75,
                "unit": "l",
                "price": 20.7,
                "currency": "RON",
                "shopName": "Profi",
                "catalogDate": "2025-05-09"
            },
            ...
        ]
    },
    ...
]
```
</details>

---

# 🧩 Entities & Relationships

## 📦 Entities
**Product** – base information about a product.   
**PricedProduct** – links a product to a specific price in a catalog.   
**Discount** – represents a discount for a priced product within a catalog.   
**Catalog** – contains products and discounts for a specific shop and date.  
**PriceAlert** – user-defined alerts based on target price and email.  

## 🔗 Relationships Summary
A **Product** can appear in many **PricedProduct** entries.  
A **PricedProduct** belongs to one **Product** and one **Catalog**.  
A **Discount** is associated with one **PricedProduct** and one **Catalog**.  
A **Catalog** aggregates multiple **PricedProduct** and **Discount** entries.   

# 🛠️ Improvements

- A new `PricePoint` is saved for each product every day, regardless of price change.  
  - **Improvement**: Only save when price changes.   
- Price history to be filtered by brand or shop.
- `CatalogController` handles more than one responsibility.
  - **Suggestion**: split into multiple controllers.
- Validation and better error handling.
- `optimise-basket` endpoint returns only one best combination.
  - **Improvement**: return all shops if prices are equal.
- Currently, the app assumes all products use the same serving unit.
    - **Improvement**: handle and compare prices accurately across different units.
---
