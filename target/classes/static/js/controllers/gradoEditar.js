function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json'
   };
}

async function guardarGrado(id) {
  let datos = {};
  try{
      datos.id = localStorage.getItem("idGrado");
      datos.nombre = document.getElementById('nombesP').value;
        datos.profesor = document.getElementById('cbProfe').value;
      const request = await fetch('grados/editargrado', {
          method: 'POST',
          headers: getHeaders(),
          body: JSON.stringify(datos)
        });
        const value = await request.json();
        console.log(value);
        if(value){
            alert("El grado fue editado con exito!");
            window.location.href = 'listG.html';
        }else{
            alert("Conflicto al editar información");
        }
  }catch(e){
  }
}

$(document).ready(function(){
    getProfesores();
    getDataProfesor();
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

    const idP = await localStorage.getItem("idGrado");
    const    requestGet = await fetch('grados/editar/'+idP, {
                      method: 'GET',
                      headers: getHeaders()
                  });
            const grado = await requestGet.json();
            document.getElementById('nombesP').value = grado.nombre;
            let element = document.getElementById("cbProfe");
            element.value = grado.profesor;
}

async function getDataProfesor(){

}