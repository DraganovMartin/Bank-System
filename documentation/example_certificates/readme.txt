To generate a pair of compatible keystore files using the Java keytool provided with the JRE (JDK):

keytool -genkey -alias server -keyalg RSA -keystore server.keystore -keysize 2048
keytool -genkey -alias client -keyalg RSA -keystore client.keystore -keysize 2048

keytool -export -alias server -file server.crt -keystore server.keystore
keytool -export -alias client -file client.crt -keystore client.keystore

keytool -import -alias server -file server.crt -keystore client.keystore
keytool -import -alias client -file client.crt -keystore server.keystore

SERVER PASSWORD USED: "server"
CLIENT PASSWORD USED: "client"