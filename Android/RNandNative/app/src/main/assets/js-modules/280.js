__d(function(e,n,t,r){"use strict";function a(){b||(b=L>0?setTimeout(o,0+p):setImmediate(o))}function o(){b=0;var e=w.size;T.forEach(function(e){return w.add(e)}),E.forEach(function(e){return w.delete(e)});var n=w.size;if(0!==e&&0===n?m.emit(h.Events.interactionComplete):0===e&&0!==n&&m.emit(h.Events.interactionStart),0===n)for(;k.hasTasksToProcess();)if(k.processNext(),L>0&&i.getEventLoopRunningTime()>=L){a();break}T.clear(),E.clear()}var i=n(41),c=n(55),s=n(154),d=n(281),u=n(282),l=n(22),f=n(212),m=new c,p=0,v=!1,h={Events:f({interactionStart:!0,interactionComplete:!0}),runAfterInteractions:function(e){var n=[],t=new Promise(function(t){a(),e&&n.push(e),n.push({run:t,name:"resolve "+(e&&e.name||"?")}),k.enqueueTasks(n)});return{then:t.then.bind(t),done:function(){return t.done?t.done.apply(t,arguments):void console.warn("Tried to call done when not supported by current Promise implementation.")},cancel:function(){k.cancelTasks(n)}}},createInteractionHandle:function(){v&&u("create interaction handle"),a();var e=++I;return T.add(e),e},clearInteractionHandle:function(e){v&&u("clear interaction handle"),l(!!e,"Must provide a handle to clear."),a(),T.delete(e),E.add(e)},addListener:m.addListener.bind(m),setDeadline:function(e){L=e}},w=new s,T=new s,E=new s,k=new d({onMoreTasks:a}),b=0,I=0,L=-1;t.exports=h},280);