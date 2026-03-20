# 09 - Real-World Use Cases

Reactive programming is incredibly powerful, but it also comes with a notoriously steep learning curve. The stack traces are deeply nested framework code, debugging is harder, and libraries that block can silently ruin your application's performance. So, when should you *actually* use it?

## 1. Where Reactive Shines 🌟

### API Gateways
API Gateways (like Spring Cloud Gateway, which is built on WebFlux) simply route traffic. They don't do heavy CPU work; they just take an HTTP request, pass it to a microservice, wait for the response, and send it back. Since they sit in front of *everything* and spend 99% of their time waiting on I/O, reactive is the absolute perfect fit.

### Streaming Data (SSE, WebSockets)
If you are building a real-time dashboard that pushes data to the browser, a `Flux` maps perfectly to Server-Sent Events (SSE). Keep thousands of connections open with only a few threads!
```java
@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<StockPrice> streamPrices() {
    return stockService.getLivePrices();
}
```

### Microservices Orchestration (BFF - Backend for Frontend)
If a single client request requires your service to fetch data from 5 different backend APIs concurrently, combining them using `Mono.zip()` is vastly superior to spawning 5 custom threads and mashing them together synchronously.

## 2. Where Reactive Fails ❌

### CPU-Intensive Tasks (Video Processing, ML, Huge Math operations)
Because WebFlux relies on a tiny thread pool (e.g., 4 threads), if you give a Netty thread a task that takes 5 seconds of pure CPU calculation to complete, you have just blocked 25% of your server's total capacity for 5 entire seconds. **Never block an Event Loop thread.**

*If you MUST do CPU heavy work in a reactive app, you have to offload it to a dedicated thread pool:*
```java
Mono.fromCallable(() -> heavyCpuMath())
    .subscribeOn(Schedulers.boundedElastic());
```

### Applications relying on Legacy Blocking Libraries
If you absolutely must use an older third-party SDK that performs blocking HTTP calls, or if you refuse to move away from Hibernate/JPA, you will severely damage your WebFlux application. While you can wrap blocking calls in elastic thread pools, doing it everywhere defeats the point of using WebFlux in the first place.

---
**Next Up:** `10-summary.md` - Final Takeaways!
