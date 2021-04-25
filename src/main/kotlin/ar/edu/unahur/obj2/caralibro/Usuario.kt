package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()
  val excluidos = mutableListOf<Usuario>()
  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
  fun meLikea(publicacion: Publicacion,usuario: Usuario) {
    if (!publicacion.quienLikeo().contains(usuario)){
      publicacion.sumarUsuarioConLike(usuario)
    }
  }
  fun agregarAmigo(nuevoUsuario: Usuario) {
    if(!amigos.contains(nuevoUsuario))
      amigos.add(nuevoUsuario)
      nuevoUsuario.amigos.add(this)
  }
  fun excluirAmigo(nuevoUsuario: Usuario) {
    if(!excluidos.contains(nuevoUsuario))
      excluidos.add(nuevoUsuario)
  }
  fun puedoVerYo() = publicaciones.size > 0 //Un usuario puede ver sus propias publicaciones
  /*fun puedoVerPublicacion(usuarioChusma : Usuario,publicacion: Publicacion) = //Saber si un usuario puede ver cierta publicacion.
    publicacion.puedeVerPublicacion(usuarioChusma,this)*/

  //fun mejoresAmigos() = retorna una lista con los usuarios que pueden ver todas sus publicaciones**
  /*fun mejoresAmigos(usuario: Usuario){
    publicaciones.forEach{ it.puedeVerPublicacion(usuarioChusma = usuario,usuarioDuenio = this) }
  }*/

  fun cantidadAmigos() = amigos.size
  fun esMasAmistosoQue(usuario: Usuario) = this.cantidadAmigos() > usuario.cantidadAmigos()
  fun amigoMasPopular() = amigos.maxByOrNull { it.totalLikes() } //Devuelve el amigo con mas likes entre todas sus publicaciones.
  fun totalLikes() = publicaciones.sumBy { it.cantidadLikes() } //Devuelve el total de likes de la lista de publicaciones.
  fun totalPublicaciones() = publicaciones.size
  fun esStalker(stalker: Usuario) =
    this.cantPublicacionesLikeadasPorAmigo(stalker) > this.totalPublicaciones() * 0.9 //usuario es stalker si likeo mas del 90% de mis publicaciones.
  fun cantPublicacionesLikeadasPorAmigo(amigo: Usuario) = publicaciones.count { it.likers.contains(amigo) } //Devuelve el total de publicaciones likeadas por 'amigo'.

}
