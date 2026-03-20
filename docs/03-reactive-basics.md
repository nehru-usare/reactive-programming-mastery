# 03 - Reactive Basics: Streams, Mono, and Flux

Reactive programming in Java is standardized by the **Reactive Streams** specification, which provides a standard for asynchronous stream processing with non-blocking backpressure. Spring WebFlux natively uses **Project Reactor**, changing the way we handle data objects compared to traditional programming.

## 1. Publisher, Subscriber, and Streams

In the reactive world, everything is a stream of events. There are four core interfaces in the Reactive Streams specification:

1. **Publisher:** The source of data. It emits items (events) over time.
2. **Subscriber:** The consumer of data that listens to the Publisher.
3. **Subscription:** The link between the Publisher and Subscriber.
4. **Processor:** A component that acts as both Publisher and Subscriber (often used to transform data).

**Crucial Rule:** "Nothing happens until you subscribe." A Publisher is lazy. It won't fetch data from the database or make an HTTP call until a Subscriber explicitly calls `.subscribe()`. In Spring WebFlux, the framework handles the subscription for your controllers automatically.

## 2. Project Reactor: Mono vs. Flux

Since the `Publisher` interface is barely a skeleton, Project Reactor gives us two powerful implementations of a Publisher: `Mono` and `Flux`.

### Mono `<T>`
A `Mono` is a Publisher that emits **0 or 1** item, and then completes (or errors out).
It represents a single async value, like a `CompletableFuture` or an `Optional`.
* **Example:** Fetching a single user by ID.
* Returning: `Mono<User>`

### Flux `<T>`
A `Flux` is a Publisher that emits **0 to N** items, and then completes (or errors out).
It represents an async sequence (a stream) of data, much like a `List` or a Java `Stream`.
* **Example:** Fetching a list of all users.
* Returning: `Flux<User>`

## 3. Backpressure

Backpressure is one of the most important concepts in Reactive Streams. It solves the problem of a fast Publisher overwhelming a slow Subscriber.

**Without Backpressure:** A fast database pushes 10,000 logs per second. The application (Subscriber) can only process 1,000 logs per second. Quickly, the app runs out of memory (OOM) because it has to buffer the unhandled data.

**With Backpressure:** The Subscriber controls the flow. It sends a signal via its `Subscription`: *"Send me exactly 500 items"*. The Publisher sends exactly 500 items and waits. Once the Subscriber processes them, it requests the next batch. This ensures zero buffer overflow and high stability.

## 4. Why wrapped types? (e.g., `Mono<User>` instead of `User`)
In blocking programming, a method `User getUser(id)` blocks until the User is fetched. 
In reactive programming, the thread can't wait. Instead, `Mono<User> getUser(id)` immediately returns a "promise" (the Mono) that the data will be delivered *eventually*. You attach operators (like `.map()` or `.flatMap()`) to this Mono to define what should happen *when* the data arrives.

---
**Next Up:** `04-project-setup.md` - Understanding our dependencies!
