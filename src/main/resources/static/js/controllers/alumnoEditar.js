$(document).ready(function(){
    getDataProfesor();
});

async function getDataProfesor(){
    const idP = localStorage.getItem("idAlumno");
    const request = await fetch('alumnos/editar/'+idP, {
                  method: 'GET',
                  headers: getHeaders()
              });
        const alumno = await request.json();
        console.log(alumno);
        document.getElementById('nombesP').value = alumno.nombres;
        document.getElementById('apellidoP').value = alumno.apellidos;
        if(alumno.genero == 'M'){
            document.querySelector('#radioM').checked = true;
        }else{
            document.querySelector('#radioF').checked = true;
        }
        document.getElementById('dateF').value = alumno.fechaNacVar;
}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json'
   };
}

async function guardarAlumno(id) {
  let datos = {};
  try{
      datos.id = localStorage.getItem("idAlumno");
      datos.nombres = document.getElementById('nombesP').value;
      datos.apellidos = document.getElementById('apellidoP').value;
      var selected = document.querySelector('input[type=radio][name=flexRadioDefault]:checked');
      datos.genero = selected.id=='radioM'?'M':'F';
      let dataDate = document.getElementById('dateF').value;
            const dataSp = dataDate.replaceAll("-","");
      datos.fechaNacimiento = dataSp

      const request = await fetch('alumnos/editaralumno', {
          method: 'POST',
          headers: getHeaders(),
          body: JSON.stringify(datos)
        });
        const value = await request.json();
        console.log(value);
        if(value){
            alert("El alumno fue editado con exito!");
            window.location.href = 'listA.html';
        }else{
            alert("Conflicto al editar información");
        }
  }catch(e){
  }

}