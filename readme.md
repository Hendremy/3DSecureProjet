PORT_AUTH = 7777

PORT_MONEY = 6666

PORT_HTTPS_ACQ = 8888

# Certificats

3 serveurs, chacun à un jks (key store) dans lequel il doit contenir les certificats de ses amis

Client (app externe) => client.crt

Serveur Https => serveur.crt

ACS => acs.crt

ACQ => acq.crt

# Keystore

Client (app externe) => acs.crt , client.crt + client.key

Serveur https => acq.crt, serveurt.crt + serveur.key

ACS => client.crt, acq.crt, acs.crt + acs.key

ACQ => serveur.crt, acs.crt, acq.crt + acq.key

# Commandes

    keytool -importcert -file certificate.cer -keystore keystore.jks -alias "Alias"

=> importer un certificat dans un keystore

    keytool -exportcert -keystore [keystore] -alias [alias] -file [cert_file]

=> exporter certificat

     keytool -genkey -keyalg RSA -alias selfsigned -keystore server.jks -storepass heplhepl -validity 360 -keysize 2048

=> générer keystore avec certificat