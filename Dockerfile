FROM chetbox/ghoulio:1.1.1
MAINTAINER chetbox

# Install Leiningen
RUN mkdir -p /opt/bin && \
    wget -q https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein -O /opt/bin/lein && \
    chmod +x /opt/bin/lein
ENV PATH $PATH:/opt/bin

ADD . /server
WORKDIR /server

# Build
RUN lein uberjar

ENV PORT 80
CMD java -jar target/ghoulio-server-*-standalone.jar
EXPOSE 80
