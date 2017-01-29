# Network Shield

This is a simplified DNS server to filter requests according to blocklists selected by the user.

## Main idea
Local, lightweight and simplified DNS server running in background. Local machine is reconfigured to use the local DNS server as main name server, Network Shield filters incoming requests by querying a blocklist data structure and takes one of two actions:
- If the queried address is blocked, either return localhost or NAME_ERROR
- If the queried address is unblocked, forwards the query to either the network DNS server or one of google's servers

The user gets access to a control panel where he can apply blocklists (provided by Network Shield or other users possibly), block hosts manually and view statistics on previously blocked hosts. Statistics can be disabled and blocklists can be automatically updated by Network Shield, if the user chooses so.

#### [Sample Output (using an overly prohibitive blocklist](/sample_output.txt)


## Why Java?

 - It is the language I'm most comfortable with ¯\\\_(ツ)_/¯
 - I aim to get a job programming in Java in the future
 - It offers me some robustness for free, which I like very much
 - Big community
