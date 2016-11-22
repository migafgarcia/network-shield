# Network Shield

This is a simplified DNS server to filter requests according to blocklists selected by the user.

When a request is received, the program consults a data structure (to define) to check if the host is blocked. If its not, the program forwards the request to the DNS server of the local network.

## TODO list

 - Parse requests
 - Create responses or query local name server to get a response and forward it to the client
 - Create data structure (probably a tree) to store and query blacklisted hostnames (harder) OR use SQL database (simpler)
 - Client management
 - Read RFC to find problems with this architecture
 - Investigate best library to make a GUI (JavaFX, Swing, a web page, etc) (this client would have to be a separate program that communicates with the main program)
 - Store blocked hosts for local statistics
