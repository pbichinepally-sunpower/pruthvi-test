FROM gcr.io/datadoghq/agent:latest-jmx
RUN mkdir conf.d/kafka.d/
COPY ./kafka.yaml conf.d/kafka.d/
#COPY ./metrics.yaml conf.d/kafka.d/