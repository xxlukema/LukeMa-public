
var tabIds = new Array("TabFocusA", "searchCriteriaSite", "startDateDate", "selectedBusinessDay");

function setTabFocus()
{
   for ( var i = 0; i < tabIds.length; i++ )
   {
      var id = tabIds[i];

      fElement = document.getElementById(id);

      if ( fElement != null )
      {
         fElement.focus();
         break;
      }
   }
}

