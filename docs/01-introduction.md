# 01 - Introduction to Reactive Programming

Welcome to the journey of mastering Reactive Programming! In this project, we'll build the same application twice: once using the traditional (blocking) approach, and once using the reactive (non-blocking) approach. This side-by-side comparison will help you truly understand *why* and *how* reactive programming works.

## What is Reactive Programming?

At its core, **Reactive Programming** is an asynchronous programming paradigm concerned with data streams and the propagation of change. 

Imagine you are at a restaurant:
* **Traditional (Blocking) Approach:** You place an order with a waiter. The waiter goes to the kitchen, waits for the chef to cook your meal, and brings it back. While waiting, the waiter cannot serve any other tables. If all waiters are waiting at the kitchen, new customers can't even place an order!
* **Reactive (Non-blocking) Approach:** You place an order. The waiter gives the order to the kitchen and immediately returns to serve another table. When your food is ready, the kitchen "pushes" a notification (rings a bell), and whichever waiter is free brings you the food.

In technical terms, reactive programming uses **event-driven, non-blocking I/O** to handle requests. Instead of dedicating a thread to wait for a database query or a network call to finish, the thread is released back to a pool so it can do other work. When the database is done, it triggers an event, and a thread picks up the result to continue processing.

## Why does it exist?

As applications grew to serve millions of users, traditional architectures started heavily struggling with scalability:
1. **The C10K Problem:** Handling 10,000 concurrent connections used to be a massive hurdle. In a blocking model, 10k connections imply up to 10k threads if they are all active simultaneously.
2. **Resource Exhaustion:** Threads are expensive. They consume memory (usually 1MB per thread stack) and CPU cycles (context switching). If you have 500 threads waiting for a slow database, you are wasting 500MB of RAM doing absolutely nothing.
3. **Cloud Costs:** In the cloud era, scaling out means paying for more servers. If servers are mostly idle (waiting on I/O), you are burning money.

Reactive programming aims to solve these by doing more with less. A reactive framework like Spring WebFlux can handle thousands of concurrent requests using just a handful of threads (often matching the number of CPU cores).

## Problems with Traditional Programming

Before we dive into reactive, let's understand why traditional (Imperative / Blocking) programming can be problematic under high load:

### 1. The Thread-per-Request Model
In standard Spring MVC (using a servlet container like Tomcat), every incoming HTTP request is assigned a dedicated thread from a thread pool.
* If a request needs to fetch data from a database, the thread sends the query and **blocks** (waits) until the database replies.
* If the database takes 2 seconds to reply, that thread is completely paralyzed for 2 seconds.
* If you have 200 threads in your pool and 200 concurrent requests all waiting on the database, the 201st request will be rejected or queued. Your application is "frozen", even though CPU usage might be near 0%!

### 2. Underutilized Resources
Because threads spend most of their time waiting for I/O (Database, REST APIs, File System), your CPU isn't doing actual computation. It's just managing idle threads.

### 3. Cascading Failures
If Service A calls Service B, and Service B becomes slow, Service A's threads will all get blocked waiting for B. Service A then runs out of threads, bringing down the whole system. This is why we often need circuit breakers in traditional microservices.

## The Solution: The Reactive Manifesto

To address these issues, the industry created a set of principles called the [Reactive Manifesto](https://www.reactivemanifesto.org/). It states that reactive systems must be:
1. **Responsive:** The system responds in a timely manner if at all possible.
2. **Resilient:** The system stays responsive in the face of failure.
3. **Elastic:** The system stays responsive under varying workload.
4. **Message Driven:** The system relies on asynchronous message-passing to establish loose coupling and isolation.

In the next section, we'll dive deep into exactly how the thread models differ between blocking and reactive setups.

---

**Next Up:** `02-blocking-vs-reactive.md` - A deep dive into the Thread Model Comparison!
