# Java Inheritance + Polymorphism example

This repo shows an example of how to use Inheritance + Polymorphism to cache data in memory from a web request.

# How to run

There is not a main method, but you can check if the project is ok by running the tests:

`````shell
./gradlew test
`````

The command above will actually fail. Checkout the Challenge section below for more details on how to fix this.

## Requirements

- Java 19

# Context

We have to make a small library that will make requests to an endpoint and return to the client that uses the lib the raw json response.

The client might call our library multiple times in a row, so we want to implement a simple in memory cache strategy to prevent unnecessary API calls.

Inheritance and Polymorphism are good options to extend the class that make requests to add the cache behavior.

Note that inheritance has its pros and cons, I recommend [checking this stackoverflow discussion](https://softwareengineering.stackexchange.com/questions/260343/why-is-inheritance-generally-viewed-as-a-bad-thing-by-oop-proponents) to know more about it. The general rule is to not consider inheritance as your go-to solution for everything.

# Inheritance

In practice inheritance happens when you use the ``extends`` keyword on a class.

````java

public class Human extends Animal {
    
}

````

Basically this means that the ``Human`` class ``extends`` the behavior (methods and attributes) of ``Animal`` class. This is useful when you want to extend the functionality of a class without having to change its source code. 

# Polymorphism

As in inheritance is possible to a class behave in multiple ways, with polymorphism a method can behave in multiple ways. If I have this class:

````java

public class Animal {
    
    public String beYourself() {
        return "Exists";
    }
    
}

````

then we can use of polymorphism alongside inheritance to change this behavior:

````java

public class Human extends Animal {

    @Override
    public String beYourself() {
        return "Complain";
    }
    
}

````

we can also use another form of polymorphism (method overloading) to add some more behavior to the ``beYourslef`` method.

````java

public class Human extends Animal {

    @Override
    public String beYourself() {
        return "Complain";
    }

    //just change its signature (parameters) and we are overloading it
    public String beYourself(String subject) {
        return "Complain about " + subject;
    }
    
}

````

# Challenge

There is still a problem with this code. On test suite ``src/test/java/dev/degallant/cache/CachedResourcesTests.java`` there is still a failing test case that you need to fix by updating the implementation at `src/main/java/dev/degallant/cache/CachedResourceServer.java` and `src/main/java/dev/degallant/cache/ResourceServer.java`.

Right now we only provide support to calling an endpoint that returns a single resource. We need now to add the option to make a request that returns a list of resources. On ``ResourcesServer`` class you need to add more methods to make request to the following endpoint:

````shell
https://reqres.in/api/unknown
````

while also giving support to controlling the page and amount of items per page:

````shell
https://reqres.in/api/unknown?page=2
````

````shell
https://reqres.in/api/unknown?per_page=3
````

and this all needs to be cached on the ``CachedResourceServer`` class.

More details about the API here: [https://reqres.in/](https://reqres.in/).

To solve this you will be using method overloading and method overriding to extend what ``CachedResourceServer`` does. Checkout the ``CachedResourcesTests`` test class for more details.

# Solution

In case you got stuck or are just being lazy, checkout the solution in the ``solution`` branch.