__d(function(t,e,a,n){"use strict";var r=e(22),o={createIdentityMatrix:function(){return[1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1]},createCopy:function(t){return[t[0],t[1],t[2],t[3],t[4],t[5],t[6],t[7],t[8],t[9],t[10],t[11],t[12],t[13],t[14],t[15]]},createOrthographic:function(t,e,a,n,r,o){var i=2/(e-t),u=2/(n-a),s=-2/(o-r),c=-(e+t)/(e-t),m=-(n+a)/(n-a),v=-(o+r)/(o-r);return[i,0,0,0,0,u,0,0,0,0,s,0,c,m,v,1]},createFrustum:function(t,e,a,n,r,o){var i=1/(e-t),u=1/(n-a),s=1/(r-o),c=2*(r*i),m=2*(r*u),v=(e+t)*i,l=(n+a)*u,f=(o+r)*s,d=2*(o*r*s);return[c,0,0,0,0,m,0,0,v,l,f,-1,0,0,d,0]},createPerspective:function(t,e,a,n){var r=1/Math.tan(t/2),o=1/(a-n),i=(n+a)*o,u=2*(n*a*o);return[r/e,0,0,0,0,r,0,0,0,0,i,-1,0,0,u,0]},createTranslate2d:function(t,e){var a=o.createIdentityMatrix();return o.reuseTranslate2dCommand(a,t,e),a},reuseTranslate2dCommand:function(t,e,a){t[12]=e,t[13]=a},reuseTranslate3dCommand:function(t,e,a,n){t[12]=e,t[13]=a,t[14]=n},createScale:function(t){var e=o.createIdentityMatrix();return o.reuseScaleCommand(e,t),e},reuseScaleCommand:function(t,e){t[0]=e,t[5]=e},reuseScale3dCommand:function(t,e,a,n){t[0]=e,t[5]=a,t[10]=n},reusePerspectiveCommand:function(t,e){t[11]=-1/e},reuseScaleXCommand:function(t,e){t[0]=e},reuseScaleYCommand:function(t,e){t[5]=e},reuseScaleZCommand:function(t,e){t[10]=e},reuseRotateXCommand:function(t,e){t[5]=Math.cos(e),t[6]=Math.sin(e),t[9]=-Math.sin(e),t[10]=Math.cos(e)},reuseRotateYCommand:function(t,e){t[0]=Math.cos(e),t[2]=-Math.sin(e),t[8]=Math.sin(e),t[10]=Math.cos(e)},reuseRotateZCommand:function(t,e){t[0]=Math.cos(e),t[1]=Math.sin(e),t[4]=-Math.sin(e),t[5]=Math.cos(e)},createRotateZ:function(t){var e=o.createIdentityMatrix();return o.reuseRotateZCommand(e,t),e},reuseSkewXCommand:function(t,e){t[4]=Math.tan(e)},reuseSkewYCommand:function(t,e){t[1]=Math.tan(e)},multiplyInto:function(t,e,a){var n=e[0],r=e[1],o=e[2],i=e[3],u=e[4],s=e[5],c=e[6],m=e[7],v=e[8],l=e[9],f=e[10],d=e[11],h=e[12],M=e[13],C=e[14],p=e[15],T=a[0],x=a[1],y=a[2],b=a[3];t[0]=T*n+x*u+y*v+b*h,t[1]=T*r+x*s+y*l+b*M,t[2]=T*o+x*c+y*f+b*C,t[3]=T*i+x*m+y*d+b*p,T=a[4],x=a[5],y=a[6],b=a[7],t[4]=T*n+x*u+y*v+b*h,t[5]=T*r+x*s+y*l+b*M,t[6]=T*o+x*c+y*f+b*C,t[7]=T*i+x*m+y*d+b*p,T=a[8],x=a[9],y=a[10],b=a[11],t[8]=T*n+x*u+y*v+b*h,t[9]=T*r+x*s+y*l+b*M,t[10]=T*o+x*c+y*f+b*C,t[11]=T*i+x*m+y*d+b*p,T=a[12],x=a[13],y=a[14],b=a[15],t[12]=T*n+x*u+y*v+b*h,t[13]=T*r+x*s+y*l+b*M,t[14]=T*o+x*c+y*f+b*C,t[15]=T*i+x*m+y*d+b*p},determinant:function(t){var e=babelHelpers.slicedToArray(t,16),a=e[0],n=e[1],r=e[2],o=e[3],i=e[4],u=e[5],s=e[6],c=e[7],m=e[8],v=e[9],l=e[10],f=e[11],d=e[12],h=e[13],M=e[14],C=e[15];return o*s*v*d-r*c*v*d-o*u*l*d+n*c*l*d+r*u*f*d-n*s*f*d-o*s*m*h+r*c*m*h+o*i*l*h-a*c*l*h-r*i*f*h+a*s*f*h+o*u*m*M-n*c*m*M-o*i*v*M+a*c*v*M+n*i*f*M-a*u*f*M-r*u*m*C+n*s*m*C+r*i*v*C-a*s*v*C-n*i*l*C+a*u*l*C},inverse:function(t){var e=o.determinant(t);if(!e)return t;var a=babelHelpers.slicedToArray(t,16),n=a[0],r=a[1],i=a[2],u=a[3],s=a[4],c=a[5],m=a[6],v=a[7],l=a[8],f=a[9],d=a[10],h=a[11],M=a[12],C=a[13],p=a[14],T=a[15];return[(m*h*C-v*d*C+v*f*p-c*h*p-m*f*T+c*d*T)/e,(u*d*C-i*h*C-u*f*p+r*h*p+i*f*T-r*d*T)/e,(i*v*C-u*m*C+u*c*p-r*v*p-i*c*T+r*m*T)/e,(u*m*f-i*v*f-u*c*d+r*v*d+i*c*h-r*m*h)/e,(v*d*M-m*h*M-v*l*p+s*h*p+m*l*T-s*d*T)/e,(i*h*M-u*d*M+u*l*p-n*h*p-i*l*T+n*d*T)/e,(u*m*M-i*v*M-u*s*p+n*v*p+i*s*T-n*m*T)/e,(i*v*l-u*m*l+u*s*d-n*v*d-i*s*h+n*m*h)/e,(c*h*M-v*f*M+v*l*C-s*h*C-c*l*T+s*f*T)/e,(u*f*M-r*h*M-u*l*C+n*h*C+r*l*T-n*f*T)/e,(r*v*M-u*c*M+u*s*C-n*v*C-r*s*T+n*c*T)/e,(u*c*l-r*v*l-u*s*f+n*v*f+r*s*h-n*c*h)/e,(m*f*M-c*d*M-m*l*C+s*d*C+c*l*p-s*f*p)/e,(r*d*M-i*f*M+i*l*C-n*d*C-r*l*p+n*f*p)/e,(i*c*M-r*m*M-i*s*C+n*m*C+r*s*p-n*c*p)/e,(r*m*l-i*c*l+i*s*f-n*m*f-r*s*d+n*c*d)/e]},transpose:function(t){return[t[0],t[4],t[8],t[12],t[1],t[5],t[9],t[13],t[2],t[6],t[10],t[14],t[3],t[7],t[11],t[15]]},multiplyVectorByMatrix:function(t,e){var a=babelHelpers.slicedToArray(t,4),n=a[0],r=a[1],o=a[2],i=a[3];return[n*e[0]+r*e[4]+o*e[8]+i*e[12],n*e[1]+r*e[5]+o*e[9]+i*e[13],n*e[2]+r*e[6]+o*e[10]+i*e[14],n*e[3]+r*e[7]+o*e[11]+i*e[15]]},v3Length:function(t){return Math.sqrt(t[0]*t[0]+t[1]*t[1]+t[2]*t[2])},v3Normalize:function(t,e){var a=1/(e||o.v3Length(t));return[t[0]*a,t[1]*a,t[2]*a]},v3Dot:function(t,e){return t[0]*e[0]+t[1]*e[1]+t[2]*e[2]},v3Combine:function(t,e,a,n){return[a*t[0]+n*e[0],a*t[1]+n*e[1],a*t[2]+n*e[2]]},v3Cross:function(t,e){return[t[1]*e[2]-t[2]*e[1],t[2]*e[0]-t[0]*e[2],t[0]*e[1]-t[1]*e[0]]},quaternionToDegreesXYZ:function(t,e,a){var n=babelHelpers.slicedToArray(t,4),r=n[0],i=n[1],u=n[2],s=n[3],c=s*s,m=r*r,v=i*i,l=u*u,f=r*i+u*s,d=c+m+v+l,h=180/Math.PI;return f>.49999*d?[0,2*Math.atan2(r,s)*h,90]:f<-.49999*d?[0,-2*Math.atan2(r,s)*h,-90]:[o.roundTo3Places(Math.atan2(2*r*s-2*i*u,1-2*m-2*l)*h),o.roundTo3Places(Math.atan2(2*i*s-2*r*u,1-2*v-2*l)*h),o.roundTo3Places(Math.asin(2*r*i+2*u*s)*h)]},roundTo3Places:function(t){var e=t.toString().split("e");return.001*Math.round(e[0]+"e"+(e[1]?+e[1]-3:3))},decomposeMatrix:function(t){r(16===t.length,"Matrix decomposition needs a list of 3d matrix values, received %s",t);var e=[],a=[],n=[],i=[],u=[];if(t[15]){for(var s=[],c=[],m=0;m<4;m++){s.push([]);for(var v=0;v<4;v++){var l=t[4*m+v]/t[15];s[m].push(l),c.push(3===v?0:l)}}if(c[15]=1,o.determinant(c)){if(0!==s[0][3]||0!==s[1][3]||0!==s[2][3])var f=[s[0][3],s[1][3],s[2][3],s[3][3]],d=o.inverse(c),h=o.transpose(d),e=o.multiplyVectorByMatrix(f,h);else e[0]=e[1]=e[2]=0,e[3]=1;for(var m=0;m<3;m++)u[m]=s[3][m];var M=[];for(m=0;m<3;m++)M[m]=[s[m][0],s[m][1],s[m][2]];n[0]=o.v3Length(M[0]),M[0]=o.v3Normalize(M[0],n[0]),i[0]=o.v3Dot(M[0],M[1]),M[1]=o.v3Combine(M[1],M[0],1,-i[0]),i[0]=o.v3Dot(M[0],M[1]),M[1]=o.v3Combine(M[1],M[0],1,-i[0]),n[1]=o.v3Length(M[1]),M[1]=o.v3Normalize(M[1],n[1]),i[0]/=n[1],i[1]=o.v3Dot(M[0],M[2]),M[2]=o.v3Combine(M[2],M[0],1,-i[1]),i[2]=o.v3Dot(M[1],M[2]),M[2]=o.v3Combine(M[2],M[1],1,-i[2]),n[2]=o.v3Length(M[2]),M[2]=o.v3Normalize(M[2],n[2]),i[1]/=n[2],i[2]/=n[2];var C=o.v3Cross(M[1],M[2]);if(o.v3Dot(M[0],C)<0)for(m=0;m<3;m++)n[m]*=-1,M[m][0]*=-1,M[m][1]*=-1,M[m][2]*=-1;a[0]=.5*Math.sqrt(Math.max(1+M[0][0]-M[1][1]-M[2][2],0)),a[1]=.5*Math.sqrt(Math.max(1-M[0][0]+M[1][1]-M[2][2],0)),a[2]=.5*Math.sqrt(Math.max(1-M[0][0]-M[1][1]+M[2][2],0)),a[3]=.5*Math.sqrt(Math.max(1+M[0][0]+M[1][1]+M[2][2],0)),M[2][1]>M[1][2]&&(a[0]=-a[0]),M[0][2]>M[2][0]&&(a[1]=-a[1]),M[1][0]>M[0][1]&&(a[2]=-a[2]);var p;return p=a[0]<.001&&a[0]>=0&&a[1]<.001&&a[1]>=0?[0,0,o.roundTo3Places(180*Math.atan2(M[0][1],M[0][0])/Math.PI)]:o.quaternionToDegreesXYZ(a,s,M),{rotationDegrees:p,perspective:e,quaternion:a,scale:n,skew:i,translation:u,rotate:p[2],rotateX:p[0],rotateY:p[1],scaleX:n[0],scaleY:n[1],translateX:u[0],translateY:u[1]}}}}};a.exports=o},221);