services=['unbound-site-inverters-sink','raw-site-processor','unbound-site-inverters-processor','inverter-aggregator','site-aggregator','site-sink',
 'inverter-sink','inverter-logger-processor' 'inverter-data-enricher' 'logger-site-processor' 's3-key-producer' 's3-key-processor'
  'sensor-data-extractor' 'site-time-zone-processor' 'unarchive-processor' 'nightvision-api' 'kinesis-producer' 'mds-source-connector'
]
for i in services: 
    print (i)