# Network Shield

This is a simplified DNS server to filter requests according to blocklists selected by the user.

When a request is received, the program consults a data structure (to define) to check if the host is blocked. If its not, the program forwards the request to the DNS server of the local network.
