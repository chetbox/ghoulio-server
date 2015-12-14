var debug = require('debug')('server');
var body_parser = require('body-parser');
var ghoulio = require('./ghoulio');

module.exports = function(app) {
  app.use(body_parser.urlencoded({ extended: false }));
  app.use(body_parser.json());

  app.get('/', function(req, res) {
    debug(req.path);
    res.send('Boo.\n');
  });

  app.post('/open', function(req, res) {
    debug(req.path, req.body.url);
    try {
      if (!req.body.url) throw ('"url" is required');
      if (!req.body.script) throw ('"script" is required');
    } catch (e) {
      res.status(400).send(e.toString() + '\n');
    }
    try {
      var page = ghoulio.open(req.body.url, req.body.script);
      res.send(page.pid.toString());
    } catch (e) {
      res.status(500).send(e.toString() + '\n');
    }
  });

  return app;
}
