// addTimeStamp.js

module.exports = { addTimeStamp };

function addTimeStamp(userContext, events, done) {
  userContext.vars.timeStamp = Date.now().toString();
  return done();
}
