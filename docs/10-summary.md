# 10 - Summary & Key Takeaways

Congratulations! You've journeyed from understanding fundamental thread structures to building and comparing fully functional Blocking (Spring MVC) and Reactive (Spring WebFlux) applications side-by-side.

## Key Takeaways

1. **Reactive Programming is about Non-Blocking I/O:** The entire purpose is to prevent threads from idling while waiting for the network, file system, or database to respond.
2. **Nothing happens until you Subscribe:** `Mono` and `Flux` are just recipes. The data stream doesn't start flowing until `.subscribe()` is called (which Spring handles for you in Controller layers).
3. **Hardware Efficiency:** WebFlux enables applications to handle Massive Concurrency (10k+ requests) with extreme memory and CPU efficiency, saving cloud infrastructure costs.
4. **Choose the Right Tool for the Job:**
   * If throughput, resilience under load, and high-concurrency are your biggest bottlenecks, **use Reactive**.
   * If your app is simple, low-traffic, heavily reliant on a relational database ecosystem, or developer velocity is critical, **stick to Blocking (MVC + JPA)**.

### What's Next for your Journey?
To achieve true mastery, try extending the code you generated today:
* **Add Validation:** Learn how to throw reactive exceptions natively.
* **Add Spring Security:** Learn how `SecurityWebFilterChain` manages authorization reactively compared to blocking filters.
* **Testing:** Create an integration test using `WebTestClient` and `StepVerifier` for your reactive endpoints.

Keep practicing, start applying `map` and `flatMap`, and master the stream!
