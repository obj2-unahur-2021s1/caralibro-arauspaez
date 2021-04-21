package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()
  val excluidos = mutableListOf<Usuario>()
  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
  fun likear(publicacion: Publicacion) {
    if (!publicacion.quienLikeo().contains(this)){
      publicacion.sumarUsuarioConLike(this)
    }
  }

  //fun cambiarCalidadVideo(nuevaCalidad){}

  fun publicacionesFotos() = publicaciones.filter{ it.tipoPublicacion() == "foto" }

  fun agregarAmigo(nuevoUsuario: Usuario) {
    if(!amigos.contains(nuevoUsuario))
      amigos.add(nuevoUsuario)
      nuevoUsuario.amigos.add(this)
  }

  //fun puedoVerYo(publicacion: Publicacion) = publicaciones.contains(publicacion)

}
