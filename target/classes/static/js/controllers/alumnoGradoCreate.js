async function guardarGrado(id) {
  let datos = {};
  try{
      datos.alumno = document.getElementById('cbAlumno').value;
      datos.grado = document.getElementById('cbGrado').value;
      datos.seccion = document.getElementById('seccionP').value;
      const request = await fetch('alumnoGrados/create', {
          method: 'POST',
          headers: getHeaders(),
          body: JSON.stringify(datos)
        });
        const value = await request.json();
        if(value){
        alert("La asignación fue ingresado con exito!");
        window.location.href = 'listAG.html';
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
    const request = await fetch('alumnos/list', {
              method: 'GET',
              headers: getHeaders()
          });
    const alumnos = await request.json();
    console.log(alumnos);

      for (let alumno of alumnos) {
        var opt = document.createElement('option');
            opt.value = alumno.id;
            opt.innerHTML = alumno.nombres+' '+alumno.apellidos;
            document.getElementById('cbAlumno').appendChild( opt );
      }

    const requestG = await fetch('grados/list', {
                  method: 'GET',
                  headers: getHeaders()
              });
        const grados = await requestG.json();
        console.log(grados);

          for (let grado of grados) {
            var opt = document.createElement('option');
                opt.value = grado.id;
                opt.innerHTML = grado.nombre+' ('+grado.profeName+')';
                document.getElementById('cbGrado').appendChild( opt );
          }

}
