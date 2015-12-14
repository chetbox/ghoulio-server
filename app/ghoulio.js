var debug = require('debug')('ghoulio');
var fs = require('fs');
var spawn = require('child_process').spawn;

var SLIMERJS_PATH = process.env.SLIMERJS_PATH
  || '/usr/bin/slimerjs';
var GHOULIO_PATH = process.env.GHOULIO_PATH
  || '/app/ghoulio.js';
var PAGE_TIMEOUT = parseInt(process.env.PAGE_TIMEOUT)
  || 60 * 1000;

// Ensure the required files exist
fs.statSync(SLIMERJS_PATH);
fs.statSync(GHOULIO_PATH);

/*
 * Killing the process with SIGTERM/SIGKILL leaves the SlimerJS window open, so
 * we manually inject a "fail()" which causes the window to close and the
 * browser process to exit.
 */
var timeout_script = "setTimeout(function(){ fail('Timed out') }, " + PAGE_TIMEOUT + ");";

exports.open = function(url, user_script) {
  var ghoulio = spawn(SLIMERJS_PATH, [GHOULIO_PATH, url, timeout_script + '\n\n' + user_script]);
  debug('[' + ghoulio.pid + ' -> ' + url + ']');
  ghoulio.stdout.on('data', function(data) {
    debug('[' + ghoulio.pid + '] ' + data.toString());
  });
  ghoulio.stderr.on('data', function(data) {
    debug('[' + ghoulio.pid + '] Error: ' + data.toString());
  });
  ghoulio.on('close', function(code, signal) {
    debug('[' + ghoulio.pid + ' closed with code ' + code + ']');
  });
  return ghoulio;
}
