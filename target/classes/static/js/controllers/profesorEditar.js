$(document).ready(function(){
    getDataProfesor();
});

async function getDataProfesor(){
    const idP = localStorage.getItem("idProfesor");
    const request = await fetch('profesores/editar/'+idP, {
                  method: 'GET',
                  headers: getHeaders()
              });
        const profesor = await request.json();
        console.log(profesor);
        document.getElementById('nombesP').value = profesor.nombres;
        document.getElementById('apellidoP').value = profesor.apellidos;
        if(profesor.genero == 'M'){
            document.querySelector('#radioM').checked = true;
        }else{
            document.querySelector('#radioF').checked = true;
        }

}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json'
   };
}

async function guardarProfesor(id) {
  let datos = {};
  try{
      datos.id = localStorage.getItem("idProfesor");
      datos.nombres = document.getElementById('nombesP').value;
      datos.apellidos = document.getElementById('apellidoP').value;
      var selected = document.querySelector('input[type=radio][name=flexRadioDefault]:checked');
      datos.genero = selected.id=='radioM'?'M':'F';

      const request = await fetch('profesores/editarprofesor', {
          method: 'POST',
          headers: getHeaders(),
          body: JSON.stringify(datos)
        });
        const value = await request.json();
        console.log(value);
        if(value){
            alert("El profesor fue editado con exito!");
            window.location.href = 'listP.html';
        }else{
            alert("Conflicto al editar información");
        }
  }catch(e){
  }

}