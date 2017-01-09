# Network Shield

This is a simplified DNS server to filter requests according to blocklists selected by the user.

## Main idea
Local, lightweight and simplified DNS server running in background. Local machine is reconfigured to use the local DNS server as main name server, Network Shield filters incoming requests by querying a blocklist data structure and takes one of two actions:
- If the queried address is blocked, either return localhost or NAME_ERROR
- If the queried address is unblocked, forwards the query to either the network DNS server or one of google's servers

The user gets access to a control panel where he can apply blocklists (provided by Network Shield or other users possibly), block hosts manually and view statistics on previously blocked hosts. Statistics can be disabled and blocklists can be automatically updated by Network Shield, if the user chooses so.
Right now all the code is written in Java. 
## TODO list

 - ~~Parse Header~~
 - ~~Parse Question section~~
 - ~~Parse Answer, Authority and Additional sections~~
 - ~~Generate header message~~
 - ~~Generate questionSection section message~~
 - Generate Answer, Authority and Additional sections message
 - Client management
 - Create responses or query local name server to get a response and forward it to the client
 - Create data structure (probably a tree) to store and query blacklisted hostnames (harder) OR use SQL database (simpler)
 - Make the program more robust to errors and failures
 - Add unit testing
 - Test and profiling to make the program faster, much faster
 - Read RFC to find problems with this architecture
 - Investigate best library to make a GUI (JavaFX, Swing, a web page, etc) (this client would have to be a separate program that communicates with the main program)
 - Store blocked hosts for local statistics
