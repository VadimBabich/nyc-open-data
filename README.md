# Tree Radius Interview Assignment

Hi there! Congratulations on making it to the next step!

You are given a scaffold application based on Spring Boot to save your time, but you are free to use any other frameworks if you would like to.

Your task is to implement a specific feature as a REST service that uses some 3rd party API.
A service should make an aggregated search of trees (plants, not binary trees) in the circle.

Input:
  - X and Y of the cartesian center point
  - A search radius in meters

Output:
  - Aggregation by "common name" (please see in the documentation on the same page above which field to refer to)

Example of the expected output:
```json
{
    "red maple": 30,
    "American linden": 1,
    "London planetree": 3
}
```

The service should use the data from the 3rd party API (https://data.cityofnewyork.us/Environment/2015-Street-Tree-Census-Tree-Data/uvpi-gqnh): `https://data.cityofnewyork.us/resource/nwxe-4ae8.json`

If you happen to have any questions, please send an email to your HR contact at Holidu.

Good luck and happy coding!

## Getting Start

This section contains instructions on running localy.

### Building and running Application

1. Compile the Application.
    
    `mvn clean install -DskipTests`

1. Start the Application.
    
    `java -jar target/tree-radius-1.0.0-SNAPSHOT.jar`

usage available arguments:
```
    street.tree.base-url - the URL to the base domain for third partry service Street Tree Census
    street.tree.resource-id - The id of the resource to query the third partry service
    street.tree.batch-limit - maximum size of data chunk getting from the third party service
``` 
how to pass arguments to the application:
    
    `java -jar target/tree-radius-1.0.0-SNAPSHOT.jar --street.tree.batch-limit=3000`

example:
```
# request
# endpoint URL - /street-tree/in-circle-area
# radius - search radius in meters (int)
# x - longitude of the center point (double value from -180 to 180)
# y - latitude of the center point (double value from -90 to 90)
GET http://localhost:8080/street-tree/in-circle-area?radius=50&x=-74.18923615&y=40.54093888

# response
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked

{
  "cherry": 1,
  "golden raintree": 1,
  "crimson king maple": 1,
  "hawthorn": 1,
  "sweetgum": 3,
  "silver maple": 2,
  "littleleaf linden": 1,
  "Callery pear": 1,
  "Norway maple": 2,
  "pin oak": 2,
  "red maple": 3,
  "Amur maple": 1
}

Response code: 200; Time: 709ms; Content length: 195 bytes
```
