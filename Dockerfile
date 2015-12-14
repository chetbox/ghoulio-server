FROM chetbox/ghoulio
MAINTAINER chetbox

# Install NodeJS
RUN curl -sL https://deb.nodesource.com/setup_4.x | bash -
RUN apt-get install -y nodejs
RUN apt-get autoremove -y && apt-get clean all

ADD ./app /app
WORKDIR /app
RUN npm install

ENV PORT 80
ENTRYPOINT ["/bin/bash"]
CMD ["/usr/bin/node", "index.js"]
