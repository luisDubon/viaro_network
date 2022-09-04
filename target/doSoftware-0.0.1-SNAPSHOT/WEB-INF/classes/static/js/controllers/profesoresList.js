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
        let botonEliminar = '<a href="#" onclick="eliminarProfesor(' + profesor.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
let botonEditar = '<a href="#" onclick="editarProfesor(' + profesor.id + ')" class="btn btn-primary btn-circle btn-sm"><i class="fas fa-user-circle"></i></a>';
        let profesorHtml = '<tr><td>'+profesor.id+'</td><td>' + profesor.nombres + '</td><td>' + profesor.apellidos + '</td><td>'
                        + profesor.genero+'</td><td>' + botonEliminar +'  '+botonEditar+ '</td></tr>';
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

async function eliminarProfesor(id) {

  if (!confirm('¿Desea eliminar este profesor?')) {
    return;
  }

 const request = await fetch('profesores/delete/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

async function editarProfesor(id){
    localStorage.setItem("idProfesor",id);
    window.location.href = 'editP.html';
}

