# java_play_architecture
CRUD architecture based on Java Play framework with Kundera ORM and 
Cassandra DB.

### Abstraction
A CRUD architecture based on Java Play framework with Cassandra as our 
NoSQL database of choice. It is also needed to integrate Kundera ORM for
this.

### library version
JDK 8
play 2.5.8
kundera 3.6
with cassandra-ds-driver 3.1
cassandra 3.9


### Make entity classes available at runtime
When running in development mode, Play uses its own Classloader, that 
don’t read classes from target/scala-2.xx/classes folder. As a result, 
your User entity won’t be loaded at runtime throwing exceptions as a 
consequence. In order to make your entity classes available at run time 
(in development mode, of course), all you’ve to do is to create jar 
file out of them and put this jar file into lib folder under project 
root directory.
➜  fsns_1 git:(patch_cors_issue) cd target/scala-2.11/classes
➜  fsns_1 git:(patch_cors_issue) jar -cvf myEntities.jar models
➜  fsns_1 git:(patch_cors_issue) cp myEntities.jar ../../../lib
➜  fsns_1 git:(patch_cors_issue) cp myEntities.jar ../../../


### Start Cassandra Server and create schema/ tables
Now, create schema and tables for User entity to be written to:

➜  fsns_1 git:(fix_orm_issue) cqlsh
Connected to Test Cluster at 127.0.0.1:9042.
[cqlsh 5.0.1 | Cassandra 3.6 | CQL spec 3.4.2 | Native protocol v4]
Use HELP for help.
cqlsh> create keyspace fsns;
cqlsh> use fsns;
cqlsh> create column family users with comparator=UTF8Type and 
    default_validation_class=UTF8Type and key_validation_class=UTF8Type;
81852270-2374-11e1-0000-242d50cf1fdd
Waiting for schema agreement...
... schemas agree across the cluster

