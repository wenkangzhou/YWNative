__d(function(e,t,n,i){"use strict";function r(e,t){a(t,"Must supply set of valid event types");var n=e.prototype||e;a(!n.__eventEmitter,"An active emitter is already mixed in");var i=e.constructor;i&&a(i===Object||i===Function,"Mix EventEmitter into a class, not an instance"),n.hasOwnProperty(E)?babelHelpers.extends(n.__types,t):n.__types?n.__types=babelHelpers.extends({},n.__types,t):n.__types=t,babelHelpers.extends(n,d)}var s=t(55),_=t(343),o=t(344),a=t(22),v=t(345),E=v({__types:!0}),d={emit:function(e,t,n,i,r,s,_){return this.__getEventEmitter().emit(e,t,n,i,r,s,_)},emitAndHold:function(e,t,n,i,r,s,_){return this.__getEventEmitter().emitAndHold(e,t,n,i,r,s,_)},addListener:function(e,t,n){return this.__getEventEmitter().addListener(e,t,n)},once:function(e,t,n){return this.__getEventEmitter().once(e,t,n)},addRetroactiveListener:function(e,t,n){return this.__getEventEmitter().addRetroactiveListener(e,t,n)},addListenerMap:function(e,t){return this.__getEventEmitter().addListenerMap(e,t)},addRetroactiveListenerMap:function(e,t){return this.__getEventEmitter().addListenerMap(e,t)},removeAllListeners:function(){this.__getEventEmitter().removeAllListeners()},removeCurrentListener:function(){this.__getEventEmitter().removeCurrentListener()},releaseHeldEventType:function(e){this.__getEventEmitter().releaseHeldEventType(e)},__getEventEmitter:function(){if(!this.__eventEmitter){var e=new s,t=new o;this.__eventEmitter=new _(e,t)}return this.__eventEmitter}};n.exports=r},342);