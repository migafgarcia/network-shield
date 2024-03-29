# Network Shield

This is a simplified DNS server to filter requests according to blocklists selected by the user.

## Main idea
Local, lightweight and simplified DNS server running in background. Local machine is reconfigured to use the local DNS server as main name server, Network Shield filters incoming requests by querying a blocklist data structure and takes one of two actions:
- If the queried address is blocked, either return localhost or NAME_ERROR
- If the queried address is unblocked, forwards the query to either the network DNS server or one of google's servers

The user gets access to a control panel where he can apply blocklists (provided by Network Shield or other users possibly), block com.mgarcia.hosts manually and view statistics on previously blocked com.mgarcia.hosts. Statistics can be disabled and blocklists can be automatically updated by Network Shield, if the user chooses so.

#### [Sample Output](/sample_output.txt)

## Note

This was mainly a project to help me understand about DNS servers.
