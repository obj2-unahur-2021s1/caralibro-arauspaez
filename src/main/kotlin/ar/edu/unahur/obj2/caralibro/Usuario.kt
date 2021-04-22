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
  fun puedoVerYo() = publicaciones.size > 0 //Un usuario puede ver sus propias publicaciones
  fun puedoVerPublicacion(publicacion: Publicacion, usuario: Usuario) =
    publicacion.puedeVerPublicacion(usuarioChusma = this, usuarioDueño = usuario) //Saber si un usuario puede ver cierta publicacion.

  //fun mejoresAmigos() = retorna una lista con los usuarios que pueden ver todas sus publicaciones**

  fun cantidadAmigos() = amigos.size
  fun esMasAmistosoQue(usuario: Usuario) = this.cantidadAmigos() > usuario.cantidadAmigos()
  fun amigoMasPopular() = amigos.maxBy { it.totalLikes() } //Devuelve el amigo con mas likes entre todas sus publicaciones.
  fun totalLikes() = publicaciones.sumBy { it.cantidadLikes() } //Devuelve el total de likes de la lista de publicaciones.
  fun totalPublicaciones() = publicaciones.size
  fun esStalker(stalker: Usuario) =
    this.cantPublicacionesLikeadasPorAmigo(stalker) > this.totalPublicaciones() * 0.9 //usuario es stalker si likeo mas del 90% de mis publicaciones.
  fun cantPublicacionesLikeadasPorAmigo(amigo: Usuario) = publicaciones.count { it.likers.contains(amigo) } //Devuelve el total de publicaciones likeadas por 'amigo'.
}
