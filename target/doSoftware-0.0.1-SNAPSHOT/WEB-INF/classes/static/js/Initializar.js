$(document).ready(function(){
   getElementS("sideBarInit","rawPages/sidebar.html");
   getElementS("navInit","rawPages/nav.html");
   getElementS("footerInit","rawPages/footer.html");
   getElementS("LogoutInit","rawPages/logoutModal.html");
});

async function getElementS(elemendID, elementLocation){
    await fetch(elementLocation)
        .then((response) => response.text())
        .then((html) => {
            document.getElementById(elemendID).innerHTML = html;
        })
        .catch((error) => {
            console.warn(error);
        });
}