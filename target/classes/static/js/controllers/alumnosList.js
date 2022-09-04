$(document).ready(function(){
    getAlumnos();
});

async function getAlumnos(){
    const request = await fetch('alumnos/list', {
              method: 'GET',
              headers: getHeaders()
          });
    const alumnos = await request.json();
    console.log(alumnos);

      let listadoHtml = '';
      for (let alumno of alumnos) {
        let botonEliminar = '<a href="#" onclick="eliminarAlumno(' + alumno.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
let botonEditar = '<a href="#" onclick="editarAlumno(' + alumno.id + ')" class="btn btn-primary btn-circle btn-sm"><i class="fas fa-user-circle"></i></a>';
        let alumnoHtml = '<tr><td>'+alumno.id+'</td><td>' + alumno.nombres + '</td><td>' + alumno.apellidos + '</td><td>'
                        + alumno.genero+ '</td><td>'+alumno.fechaNacVar+'</td><td>' +alumno.edad+'</td><td>' + botonEliminar +'  '+botonEditar+ '</td></tr>';
        listadoHtml += alumnoHtml;
      }
      document.querySelector('#AlumnodataTable tbody').outerHTML = listadoHtml;

}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json'
   };
}

async function eliminarAlumno(id) {

  if (!confirm('¿Desea eliminar este alumno?')) {
    return;
  }

 const request = await fetch('alumnos/delete/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

async function editarAlumno(id){
    localStorage.setItem("idAlumno",id);
    window.location.href = 'editA.html';
}

