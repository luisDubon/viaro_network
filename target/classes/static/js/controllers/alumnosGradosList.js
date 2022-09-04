$(document).ready(function(){
    getGrados();
});

async function getGrados(){
    const request = await fetch('alumnoGrados/list', {
              method: 'GET',
              headers: getHeaders()
          });
    const grados = await request.json();

      let listadoHtml = '';
      for (let grado of grados) {
        let botonEliminar = '<a href="#" onclick="eliminarAsignacion(' + grado.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
let botonEditar = '<a href="#" onclick="editarAsignacion(' + grado.id + ')" class="btn btn-primary btn-circle btn-sm"><i class="fas fa-user-circle"></i></a>';
        let profesorHtml = '<tr><td>'+grado.id+'</td><td>' + grado.alumnoVr +'</td><td>'
                        + grado.gradoVr+'</td><td>'+grado.seccion+'</td><td>' + botonEliminar +'  '+botonEditar+ '</td></tr>';
        listadoHtml += profesorHtml;
      }
      document.querySelector('#ProfesordataTable tbody').outerHTML = listadoHtml;

}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json'
   };
}

async function eliminarAsignacion(id) {

  if (!confirm('¿Desea eliminar la asignación seleccionado?')) {
    return;
  }

 const request = await fetch('alumnoGrados/delete/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

async function editarAsignacion(id){
    localStorage.setItem("idAlumnoGrado",id);
    window.location.href = 'editAG.html';
}

