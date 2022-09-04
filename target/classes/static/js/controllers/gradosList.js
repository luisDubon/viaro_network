$(document).ready(function(){
    getGrados();
});

async function getGrados(){
    const request = await fetch('grados/list', {
              method: 'GET',
              headers: getHeaders()
          });
    const grados = await request.json();

      let listadoHtml = '';
      for (let grado of grados) {
        let botonEliminar = '<a href="#" onclick="eliminarGrado(' + grado.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
let botonEditar = '<a href="#" onclick="editarGrado(' + grado.id + ')" class="btn btn-primary btn-circle btn-sm"><i class="fas fa-user-circle"></i></a>';
        let profesorHtml = '<tr><td>'+grado.id+'</td><td>' + grado.nombre +'</td><td>'
                        + grado.profeName+'</td><td>' + botonEliminar +'  '+botonEditar+ '</td></tr>';
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

async function eliminarGrado(id) {

  if (!confirm('¿Desea eliminar el grado seleccionado?')) {
    return;
  }

 const request = await fetch('grados/delete/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

async function editarGrado(id){
    localStorage.setItem("idGrado",id);
    window.location.href = 'editG.html';
}

