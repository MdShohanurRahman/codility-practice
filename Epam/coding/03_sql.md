# EPAM Interview Preparation — Part 3: SQL Queries & Window Functions (15 Problems)

> **Schema Reference for All Queries:**
> 
> ```sql
> -- Employees Table
> employees (id INT, name VARCHAR, department_id INT, salary DECIMAL, manager_id INT, hire_date DATE)
> 
> -- Departments Table
> departments (id INT, name VARCHAR)
> 
> -- Customers Table
> customers (id INT, name VARCHAR, email VARCHAR)
> 
> -- Orders Table
> orders (id INT, customer_id INT, amount DECIMAL, order_date DATE, status VARCHAR)
> ```

---

## 01. Find Employees With Salary > 5000

```sql
SELECT id, name, department_id, salary
FROM employees
WHERE salary > 5000;
```
- **Concepts:** Basic `SELECT`, `WHERE` clause filtering.

---

## 02. Find Second Highest Salary

### Approach A: Subquery with `MAX()`
```sql
SELECT MAX(salary) AS second_highest_salary
FROM employees
WHERE salary < (
    SELECT MAX(salary) 
    FROM employees
);
```

### Approach B: Window Function `DENSE_RANK()` (Production Grade)
```sql
WITH RankedSalaries AS (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk
    FROM employees
)
SELECT DISTINCT salary AS second_highest_salary
FROM RankedSalaries
WHERE rnk = 2;
```
- **Key Note:** Always use `DENSE_RANK()` over `RANK()` for Nth highest salary to correctly handle ties (duplicate salaries).

---

## 03. Find Nth Highest Salary

```sql
SELECT DISTINCT salary
FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk
    FROM employees
) ranked
WHERE rnk = 3; -- Change 3 to any N
```
- **Key Difference:**
  - `ROW_NUMBER()`: Gives `1, 2, 3, 4` (no duplicates, arbitrary tie-breaking).
  - `RANK()`: Gives `1, 1, 3, 4` (leaves gaps after duplicate ranks).
  - `DENSE_RANK()`: Gives `1, 1, 2, 3` (no gaps, ideal for Nth distinct values).

---

## 04. Employees With Salary Above Average

```sql
SELECT id, name, salary
FROM employees
WHERE salary > (
    SELECT AVG(salary)
    FROM employees
);
```
- **Concepts:** Scalar Subquery inside `WHERE` clause.

---

## 05. Count Employees Per Department

```sql
SELECT department_id, COUNT(*) AS employee_count
FROM employees
GROUP BY department_id;
```

---

## 06. Departments Having More Than 5 Employees

```sql
SELECT department_id, COUNT(*) AS total_employees
FROM employees
GROUP BY department_id
HAVING COUNT(*) > 5;
```

### 🚨 Critical Interview Trap: `WHERE` vs `HAVING`
- `WHERE` filters **individual rows BEFORE aggregation** (`GROUP BY`).
- `HAVING` filters **grouped summary rows AFTER aggregation**.
- You cannot use aggregate functions like `COUNT()`, `SUM()`, `AVG()` inside a `WHERE` clause!

---

## 07. Employee Name + Department Name (`INNER JOIN`)

```sql
SELECT 
    e.id AS employee_id,
    e.name AS employee_name,
    d.name AS department_name
FROM employees e
INNER JOIN departments d ON e.department_id = d.id;
```

---

## 08. Employees Without Department (`LEFT JOIN`)

### Approach A: `LEFT JOIN` with `IS NULL`
```sql
SELECT e.id, e.name
FROM employees e
LEFT JOIN departments d ON e.department_id = d.id
WHERE d.id IS NULL;
```

### Approach B: `NOT EXISTS`
```sql
SELECT e.id, e.name
FROM employees e
WHERE NOT EXISTS (
    SELECT 1 
    FROM departments d 
    WHERE d.id = e.department_id
);
```

---

## 09. Highest Paid Employee in Each Department

```sql
WITH RankedEmployees AS (
    SELECT 
        e.*,
        ROW_NUMBER() OVER (
            PARTITION BY department_id 
            ORDER BY salary DESC
        ) AS rn
    FROM employees e
)
SELECT id, name, department_id, salary
FROM RankedEmployees
WHERE rn = 1;
```
- **Concepts:** `PARTITION BY` partitions records into window buckets per department.

---

## 10. Find Duplicate Emails

```sql
SELECT email, COUNT(*) AS duplicate_count
FROM customers
GROUP BY email
HAVING COUNT(*) > 1;
```

---

## 11. Customers Who Never Placed an Order

### Approach A: `LEFT JOIN`
```sql
SELECT c.id, c.name, c.email
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.id IS NULL;
```

### Approach B: `NOT EXISTS`
```sql
SELECT c.id, c.name, c.email
FROM customers c
WHERE NOT EXISTS (
    SELECT 1 
    FROM orders o 
    WHERE o.customer_id = c.id
);
```

---

## 12. Total Order Amount Per Customer

```sql
SELECT 
    c.id AS customer_id,
    c.name AS customer_name,
    COALESCE(SUM(o.amount), 0) AS total_spent
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.name;
```
- **Tip:** Using `LEFT JOIN` + `COALESCE(..., 0)` ensures customers with 0 orders are still included with `$0` total spending instead of being omitted.

---

## 13. Top 3 Customers by Spending

```sql
SELECT 
    c.id, 
    c.name, 
    SUM(o.amount) AS total_spent
FROM customers c
JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.name
ORDER BY total_spent DESC
LIMIT 3;
```

---

## 14. Monthly Sales Report

```sql
SELECT 
    DATE_TRUNC('month', order_date) AS sales_month,
    SUM(amount) AS total_sales,
    COUNT(id) AS total_orders
FROM orders
WHERE status = 'COMPLETED'
GROUP BY DATE_TRUNC('month', order_date)
ORDER BY sales_month ASC;
```

---

## 15. Running Total of Sales

```sql
SELECT 
    id AS order_id,
    order_date,
    amount,
    SUM(amount) OVER (
        ORDER BY order_date ASC
    ) AS running_total
FROM orders
WHERE status = 'COMPLETED';
```
- **Concepts:** `SUM() OVER (ORDER BY order_date)` builds a cumulative running total across time.
