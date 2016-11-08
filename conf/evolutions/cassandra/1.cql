# --- !Ups

CREATE KEYSPACE IF NOT EXISTS "java_play" WITH REPLICATION = { 'class':'org.apache.cassandra.locator.SimpleStrategy','replication_factor':1} and durable_writes = 'true';
CREATE COLUMNFAMILY "java_play"."sessions" ("email" text ,"auth_token" text  , PRIMARY KEY("auth_token"));
CREATE COLUMNFAMILY "java_play"."users" ("password" text ,"name" text ,"branch" text ,"email" text  , PRIMARY KEY("email"));
CREATE COLUMNFAMILY "java_play"."user_branches" ("no_of_user" int ,"branch" text  , PRIMARY KEY("branch"));
CREATE COLUMNFAMILY "java_play"."products" ("price" int ,"product_name" text ,"product_id" text  , PRIMARY KEY("product_id"));


# --- !Downs

DROP KEYSPACE "java_play";