# spring-cloud-3818
Minimal sample to reproduce https://github.com/spring-cloud/spring-cloud-netflix/issues/3818

How to run this sample:
Run HelloClientApplication, the refreshscope will refresh every 5 seconds. Then you could see exception thrown from eureka client.
THe bug occurs only when you use the custom define DiscoveryClient


Step to reproduce the bug:

- Build a custom define DiscoveryClient
- Using refresh context to refresh endpoint
