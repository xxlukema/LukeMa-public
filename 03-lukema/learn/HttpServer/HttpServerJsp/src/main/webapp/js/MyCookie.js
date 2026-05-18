
var cookie1 = "IP-COOKIE";
var cookie2 = "Luke-COOKIE";
var cookie3 = "Luke-COOKIE3";

function createCookies(ip)
{
   createCookie(cookie1, ip, 1);
   createCookie(cookie2, "luke cookie 2.", 2);
   // If specify PATH, the cookie will not show up if not invoking that path.
   createCookie(cookie3, "luke cookie 3.", 3, "server");
}

// If specify PATH, the cookie will not show up if not invoking that path.
function readCookies()
{
   var value1 = readCookie(cookie1);
   if ( value1 == null )
   {
      alert("No cookies.");
   }
   else
   {
      var value2 = readCookie(cookie2);
      var value3 = readCookie(cookie3);
      alert("Cookies: " + cookie1 + " = " + value1 + "; " + cookie2 + " = " + value2 + "; " + cookie3 + " = " + value3);
   }
}

function deleteCookies()
{
   deleteCookie(cookie1);
   deleteCookie(cookie2);
   deleteCookie(cookie3);
}


