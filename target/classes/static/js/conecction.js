$(document).ready(function(){
    obtenerConexion();
    getEstadistica();
});

async function obtenerConexion(){
    await fetch('connection', {
              method: 'GET',
              headers: getHeaders()
          });
}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json'
   };
}

async function getEstadistica(){
    const request = await fetch('estadistica',{
        method:'GET',
        headers: getHeaders()
    });
    const estadistica = await request.json();
    console.log(estadistica);
    document.getElementById("gradReg").outerHTML = estadistica.gradReg;
    document.getElementById("alumAsig").outerHTML = estadistica.alumAsig;
    document.getElementById("profReg").outerHTML = estadistica.profReg;
    document.getElementById("alumReg").outerHTML = estadistica.alumReg;
}