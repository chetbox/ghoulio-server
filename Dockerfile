FROM chetbox/ghoulio
MAINTAINER chetbox

# Install NodeJS
RUN curl -sL https://deb.nodesource.com/setup_4.x | sudo -E bash -
RUN sudo apt-get install -y nodejs
RUN apt-get autoremove -y && apt-get clean all

ADD ./app /app
WORKDIR /app
RUN npm install

ENV PORT 80
ENTRYPOINT ["/usr/bin/node", "index.js"]
