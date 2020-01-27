# AWS Lambda - Java 11 and New AWS Lambda Features
A comparison of AWS Lambda Java 11 with Java 8 (and NodeJS for reference).

#### Comparing Languages

Results from k6/Graphana for load test runs:
![k6-results](k6-run.png)

Conclusions:
 
Java 8 and Java 11 are almost identical in runtime performance once warm.

Java 11 seems to be worse for cold starts (perhaps due to less usage at time of writing).

Java (both 8 and 11) have better runtime performance than NodeJS once warm (15% better at the median).

However Java suffers greatly on cold start times. 

Java 8 cold start times were 2-4.6s. 
![k6-results-java8](k6-run-java8.png)

Java 11 cold starts were all between 4-4.84s. 
![k6-results-java11](k6-run-java11.png)

NodeJS cold starts were significantly better at 256ms-1.06s.
![k6-results-node12](k6-run-node12.png)

##### Auto-scaling and provisioned concurrency
In December 2019 AWS announced another feature that's worth mentioning which I think
brings Java lambda more into the realm of a reasonable choice for an API: 
[Provisioned Concurrency](https://aws.amazon.com/about-aws/whats-new/2019/12/aws-lambda-announces-provisioned-concurrency/)
