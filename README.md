# api-error-handling-best-practices
There're some best practices of error handling for External API and make you simplify your troubleshooting in production.

# **TEST CASE**

## **1.external-api-error-handling-simple**
- 1.InvalidAuthenticationException
  - Request Method: Get
  - Request URL: http://localhost:8081/quota 
  - Request parameters:
    - Header: api-profile-id:null
  - Result: 
  ```java
  {
    "errorCode": "E4002",
    "errorMessage": "Not authentication for Current API.",
    "traceId": "15cea764-8865-418d-b05f-1c395f6a4d96"
  }
  ```
- 2.Validation Exception
  - Request Method: Post
  - Request URL: http://localhost:8081/quota 
  - Request parameters:
    - Header: api-profile-id:123456
    - Request body:
    ```java
    {"customerId":""}
    ```
  - Result: 
  ```java
  {
    "traceId": "9a97dae0-959b-41a9-90de-70687da390d9",
    "errorCode": "E4001",
    "errorMessage": "Invalid Format.The request format is invalid",
    "errorDetails": [
      "customerId can not be blank.,invalid value: ;",
      "quotaLimit can not be blank.,invalid value: null;",
      "quotaBalance can not be blank.,invalid value: null;"
    ]
  }
  ```
 - 3.FacadeException
   - Request Method: Get
   - Request URL: http://localhost:8081/quota?customerId=123456
   - Request parameters:
     - Header: api-profile-id:123456
   - Result: 
   ```java
   {
   	"traceId": "e173c006-a13a-41fb-bfb8-8cd4bc57ddfb",
   	"errorCode": "E4003",
   	"errorMessage": "Account not found.",
   	"errorDetails": null
   }
   ```
### **Troubleshooting - Use traceId to troubleshoot issue**   
It's very easy to troubleshoot by traceId in your log file now.
![issue1](external-api-error-handling-simple/image/issue.png)

## **2.external-api-error-handling-plus**
- 1.InvalidAuthenticationException
  - Request Method: Get
  - Request URL: http://localhost:8082/quota 
  - Request parameters:
    - Header: api-profile-id:null
  - Result: 
  ```java
  {
    "errorCode": 400,
    "errorDetails": [
        {
            "code": "EQ4002",
            "message": "身份验证失败."
        }
    ],
    "errorMessage": "Bad Request",
    "traceId": "ea8ca2d3-9ee0-4592-a970-262f2e163400"
  }
  ```
- 2.Validation Exception
  - Request Method: Post
  - Request URL: http://localhost:8081/quota 
  - Request parameters:
    - Header: api-profile-id:123456
    - Request body:
    ```java
    {"customerId":""}
    ```
  - Result: 
  ```java
  {
      "traceId": "3281d092-4247-4c2f-87c0-ec626869b593",
      "errorCode": 400,
      "errorMessage": "Bad Request",
      "errorStacks": [
          {
              "code": "EQ4003",
              "message": "请求校验失败."
          }
      ]
  }
  ```
- 3.FacadeException
   - Request Method: Get
   - Request URL: http://localhost:8081/quota?customerId=123456
   - Request parameters:
     - Header: api-profile-id:123456
   - Result: 
   ```java
  {
      "traceId": "f29b09b3-2c05-4aab-b97d-378cd316e548",
      "errorCode": 400,
      "errorMessage": "Bad Request",
      "errorStacks": [
          {
              "code": "EQ4001",
              "message": "无效账号."
          }
      ]
  }
  ```
 ### **Troubleshooting - Use traceId to troubleshoot issue**   
Setup your traceId  ```[%X{api-trace-id}]```  to output pattern in logback file
```xml
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} [%X{api-trace-id}] - %msg%n</pattern>
```
 ![issue1](external-api-error-handling-plus/image/issue.png) 