async function guardarProfesor(id) {
  let datos = {};
  try{
      datos.nombres = document.getElementById('nombesP').value;
      datos.apellidos = document.getElementById('apellidoP').value;
      var selected = document.querySelector('input[type=radio][name=flexRadioDefault]:checked');
      datos.genero = selected.id=='radioM'?'M':'F';

      const request = await fetch('profesores/create', {
          method: 'POST',
          headers: getHeaders(),
          body: JSON.stringify(datos)
        });
        const value = await request.json();
        if(value){
        alert("El profesor fue ingresado con exito!");
        window.location.href = 'listP.html';
        }else{
            alert("Conflicto al ingresar información");
        }
  }catch(e){
  }

}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json'
   };
}