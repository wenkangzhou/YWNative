__d(function(t,e,n,r){"use strict";function s(t){return"topMouseUp"===t||"topTouchEnd"===t||"topTouchCancel"===t}function i(t){return"topMouseMove"===t||"topTouchMove"===t}function o(t){return"topMouseDown"===t||"topTouchStart"===t}function c(t,e,n,r){var s=t.type||"unknown-event";t.currentTarget=I.getNodeFromInstance(r),f.invokeGuardedCallbackAndCatchFirstError(s,n,void 0,t),t.currentTarget=null}function a(t,e){var n=t._dispatchListeners,r=t._dispatchInstances;if(Array.isArray(n))for(var s=0;s<n.length&&!t.isPropagationStopped();s++)c(t,e,n[s],r[s]);else n&&c(t,e,n,r);t._dispatchListeners=null,t._dispatchInstances=null}function u(t){var e=t._dispatchListeners,n=t._dispatchInstances;if(Array.isArray(e)){for(var r=0;r<e.length&&!t.isPropagationStopped();r++)if(e[r](t,n[r]))return n[r]}else if(e&&e(t,n))return n;return null}function p(t){var e=u(t);return t._dispatchInstances=null,t._dispatchListeners=null,e}function d(t){var e=t._dispatchListeners,n=t._dispatchInstances;v(!Array.isArray(e),"executeDirectDispatch(...): Invalid `event`."),t.currentTarget=e?I.getNodeFromInstance(n):null;var r=e?e(t):null;return t.currentTarget=null,t._dispatchListeners=null,t._dispatchInstances=null,r}function l(t){return!!t._dispatchListeners}var h,f=e(89),v=e(22),g=(e(18),{injectComponentTree:function(t){h=t}}),I={isEndish:s,isMoveish:i,isStartish:o,executeDirectDispatch:d,executeDispatchesInOrder:a,executeDispatchesInOrderStopAtTrue:p,hasDispatches:l,getFiberCurrentPropsFromNode:function(t){return h.getFiberCurrentPropsFromNode(t)},getInstanceFromNode:function(t){return h.getInstanceFromNode(t)},getNodeFromInstance:function(t){return h.getNodeFromInstance(t)},injection:g};n.exports=I},106);