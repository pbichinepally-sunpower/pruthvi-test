
services=''
services='unbound-site-inverters-sink raw-site-processor unbound-site-inverters-processor inverter-aggregator site-aggregator 
site-sink inverter-sink inverter-logger-processor inverter-data-enricher logger-site-processor 
s3-key-producer s3-key-processor sensor-data-extractor site-time-zone-processor unarchive-processor 
nightvision-api kinesis-producer mds-source-connector'

for element in $services;
do 
    echo "cd ./services/$element/ > /dev/null && skaffold deploy -p edp-dev --namespace=nightvision-dev --filename=skaffold.yml --build-artifacts=$element-${commit}.json && cd ../../ > /dev/null"
    #pwd
    #cd ./services/$element/ > /dev/null && skaffold deploy -p edp-dev --namespace=nightvision-dev --filename=skaffold.yml --build-artifacts=$element-${commit}.json && cd ../../ > /dev/null
    #pwd