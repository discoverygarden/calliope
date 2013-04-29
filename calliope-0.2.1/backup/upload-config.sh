#!/bin/bash
echo "uploading config"
if [ -z $PASSWORD ]; then
    read -p "Password:" PASSWORD
fi
# delete database
curl -X DELETE http://admin:$PASSWORD@localhost:5984/config
# create database
curl -X PUT http://admin:$PASSWORD@localhost:5984/config
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/splitter%2Fdefault --data-binary @config/splitter/default.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/splitter%2Ftei --data-binary @config/splitter/tei.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/stripper%2Fplay%2Fitalian%2Fcapuana --data-binary @config/stripper/play/italian/capuana.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/stripper%2Fpoetry%2Fenglish%2Fharpur --data-binary @config/stripper/poetry/english/harpur.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/stripper%2Fplay%2Fitalian%2Fdefault --data-binary @config/stripper/play/italian/default.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/stripper%2Fdefault --data-binary @config/stripper/default.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/xslt%2Fdefault --data-binary @config/xslt/default.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/text%2Fempty%2Fdefault --data-binary @config/text/empty/default.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/text%2Fplay%2Fdefault --data-binary @config/text/play/default.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/text%2Fpoem%2Fdefault --data-binary @config/text/poem/default.json
curl -s -X PUT http://admin:$PASSWORD@localhost:5984/config/text%2Fdefault --data-binary @config/text/default.json
