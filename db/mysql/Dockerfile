from mysql:latest

env MYSQL_DATABASE sidecarhealth
env MYSQL_USER demo
env MYSQL_PASSWORD demo
# insecure, just for demo
env MYSQL_ALLOW_EMPTY_PASSWORD yes

COPY ./init.sql /docker-entrypoint-initdb.d/