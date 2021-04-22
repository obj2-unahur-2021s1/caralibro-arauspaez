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
  fun agregarAmigo(nuevoUsuario: Usuario) {
    if(!amigos.contains(nuevoUsuario))
      amigos.add(nuevoUsuario)
      nuevoUsuario.amigos.add(this)
  }
  fun puedoVerYo() = publicaciones.size > 0 //Mientras haya publicaciones puede ver
}
