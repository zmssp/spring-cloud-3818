# spring-cloud-3818
Minimal sample to reproduce https://github.com/spring-cloud/spring-cloud-netflix/issues/3818

Step to reproduce the bug:

- Build a custom define DiscoveryClient
- Using refresh context to refresh endpoint
