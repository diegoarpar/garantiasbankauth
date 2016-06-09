#!/bin/bash

# -*- ENCODING: UTF-8 -*-

echo "<° Linux es lo mejor"
cd /home/usuario_reportes_ssh/digitalizacion/auth/target

java -jar target/Autentication-1.0-SNAPSHOT.jar server config.yml
exit