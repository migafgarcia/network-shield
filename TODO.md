# TODO list


 - ~~Parse Header~~

 - ~~Parse Question section~~

 - ~~Parse Answer, Authority and Additional sections~~

 - ~~Generate header message~~

 - ~~Generate questionSection section message~~

 - ~~Create data structure (probably a tree) to store and query blacklisted hostnames OR use SQL database (simpler)~~

 - Generate Answer, Authority and Additional sections message (is this really needed?)

 - ~~Add logging (library added, logging in progress)~~

 - Add options to not output queries (privacy)

 - Clients that query blocked com.mgarcia.hosts tend to re-query many times after

 - ~~Client management~~

 - ~~Create responses or query local name server to get a response and forward it to the client~~

 - Make the program more robust to errors and failures

 - Obtain machine DNS server in first run

 - Add unit testing (library added, logging in progress)

 - Run application as daemon/service

 - Implement whitelist (either have 2 lists and search whitelist first, of remove whitelisted com.mgarcia.hosts from the generated blocklist)

 - Comments in DNS package

 - Deployment
   
     - DEB package

     - Windows installer

     - RPM package

 - Implement caching

 - Test and profiling to make the program faster, much faster

 - Read RFC to find problems with this architecture

 - Investigate best library to make a GUI (JavaFX, Swing, a web page, etc) (this client would have to be a separate program that communicates with the main program)

 - Store blocked com.mgarcia.hosts for local statistics

# DNS details


 - Implement compression into parser

 - Fail over mechanism: Implement TCP

 - Implement packets bigger than 512 bytes into parser

 - IPv6 support