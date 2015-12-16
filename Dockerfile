FROM chetbox/ghoulio:1.1.1
MAINTAINER chetbox

# Install NodeJS
RUN wget -qO- https://deb.nodesource.com/setup_4.x | bash -
RUN apt-get install -y nodejs
RUN apt-get autoremove -y && apt-get clean all

ADD ./app /server
WORKDIR /server
RUN npm install

ENV PORT 80
ENTRYPOINT ["node"]
CMD ["index.js"]

