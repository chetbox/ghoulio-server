# Ghoulio Server

A docker image with a web service to automate interaction with web pages.
Uses Gecko-based [SlimerJS](https://slimerjs.org/) under the hood.

See [chetbox/ghoulio](https://github.com/chetbox/ghoulio) for the standalone version.

## Usage

```shell
$ docker run chetbox/ghoulio-server -e PORT=8000
```

Then send a `POST` to `http://localhost:8000/open` with a `url` and a `script` to run, and an optional `callback` URL.
e.g.

```shell
# Print Google search results
$ docker run -e PORT=8000 -p 8000:8000 chetbox/ghoulio-server
$ curl --data "url=https%3A%2F%2Fwww.google.com%2Fsearch%3Fq%3Dboo&script=Array.prototype.slice.call(document.querySelectorAll('h3.r'))%0A.forEach(function(a)%20%7B%0A%20%20console.log(a.textContent)%3B%0A%7D)%3B%0Aresolve()%3B" http://localhost:8000/open
```

See [chetbox/ghoulio](https://github.com/chetbox/ghoulio) for more scripting information.

## Environment variables

- `PORT` - The port to run the web server on.
- `GHOULIO_PAGE_TIMEOUT` - The number of milliseconds before closing the page.
- `GHOULIO_PATH` - Path to ghoulio.js
- `SLIMERJS_PATH` - Path to slimerjs executable
- `GHOULIO_MAX_QUEUE_SIZE` - Maximum number of jobs to queue before blocking requests
- `GHOULIO_WORKERS` - Maximum number of parallel jobs

## License

Distributed under the MIT License.

