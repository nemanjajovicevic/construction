# Construction (Tender bidder project)

# What's it about:
The goal of this application is to create a RESTful API that contains structural design needed for construction tenders bidding.
For this project, an issuer can create a tender describing the work which needs to be
done on the construction site for which a bidder can hand in one or more offers. The issuer can
then accept out of the given offers only one which suits best. All the other offers are getting
rejected immediately. Once an offer gets accepted, it cannot be rejected anymore.

# How to run:
For development of this application IntelliJ IDEA was used. ConstructionApplication.java contains main executable.
Since application is self-contained, after project is imported in IDEA (checked out from Git repository), click on run ConstructionApplication button.
Just don't forget to do a mvn clean install in IDEA terminal window before, just to see those tests running successfully.
Don't worry about a database, it's embedded in application itself with in memory H2.
There are default configurations regarding port: 8080, H2 credentials (username: sa, password is not set), JDBC URL: jdbc:h2:mem:construction.

# What's inside:
Take a peek at pom.xml to see all the dependencies used to construct this application.

# How to test:
Since application uses REST API for communication, Swagger UI is implemented to give a better experience when testing.
Take a look at: http://localhost:8080/swagger-ui.html
H2 database structure can be inspected on: http://localhost:8080/h2/
These payloads will help you to create tender:
{
  "issuer": {
    "id": 1
  },
  "name": "Tender 1"
}
And this one to create offers:
[
  {
    "amount": 99,
    "bidder": {
      "id": 3
    },
    "name": "My offer",
    "tender": {
      "id": 6
    }
  }
]
Application has initial data inserted in database when project is run.
There are 2 issuers and 3 bidders preloaded.