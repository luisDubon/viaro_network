async function guardarGrado(id) {
  let datos = {};
  try{
      datos.nombre = document.getElementById('nombesP').value;
      datos.profesor = document.getElementById('cbProfe').value;
      const request = await fetch('grados/create', {
          method: 'POST',
          headers: getHeaders(),
          body: JSON.stringify(datos)
        });
        const value = await request.json();
        if(value){
        alert("El grado fue ingresado con exito!");
        window.location.href = 'listG.html';
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

$(document).ready(function(){
    getProfesores();
});

async function getProfesores(){
    const request = await fetch('profesores/list', {
              method: 'GET',
              headers: getHeaders()
          });
    const profesores = await request.json();
    console.log(profesores);

      let listadoHtml = '';
      for (let profesor of profesores) {
        var opt = document.createElement('option');
            opt.value = profesor.id;
            opt.innerHTML = profesor.nombres+' '+profesor.apellidos;
            document.getElementById('cbProfe').appendChild( opt );
      }


}
