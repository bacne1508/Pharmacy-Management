// Convert numbers to words
// copyright 25th July 2006, by Stephen Chapman http://javascript.about.com
// permission to use this Javascript on your web page is granted
// provided that all of the code (including this copyright notice) is
// used exactly as shown (you can change the numbering system if you wish)

// American Numbering System
var thVI = ['','ngàn','triệu', 'tỷ','trăm tỷ'];
// uncomment this line for English Number System
// var th = ['','thousand','million', 'milliard','billion'];

var dgVI = ['không','một','hai','ba','bốn', 'năm','sáu','bảy','tám','chín']; var tnVI = ['mười','mười một','mười hai','mười ba', 'mười bốn','mười lăm','mười sáu', 'mười bảy','mười tám','mười chín']; var twVI = ['hai mươi','ba mươi','bốn mươi','năm mươi', 'sáu mươi','bảy mươi','tám mươi','chín mươi']; function toWordVIs(s){s = s.toString(); s = s.replace(/[\, ]/g,''); if (s != parseFloat(s)) return 'not a number'; var x = s.indexOf('.'); if (x == -1) x = s.length; if (x > 15) return 'too big'; var n = s.split(''); var str = ''; var sk = 0; for (var i=0; i < x; i++) {if ((x-i)%3==2) {if (n[i] == '1') {str += tnVI[Number(n[i+1])] + ' '; i++; sk=1;} else if (n[i]!=0) {str += twVI[n[i]-2] + ' ';sk=1;}} else if (n[i]!=0) {str += dgVI[n[i]] +' '; if ((x-i)%3==0) str += 'trăm ';sk=1;} if ((x-i)%3==1) {if (sk) str += thVI[(x-i-1)/3] + ' ';sk=0;}} if (x != s.length) {var y = s.length; str += 'phẩy '; for (var i=x+1; i<y; i++) str += dgVI[n[i]] +' ';} return str.replace(/\s+/g,' ').replace(/mươi năm/g, "mươi lăm").replace(/mươi một/g, "mươi mốt");}