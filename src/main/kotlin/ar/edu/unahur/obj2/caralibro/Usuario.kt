package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()

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
  
  
  fun esMejorAmigo(amigo: Usuario) = publicaciones.all{ it.puedeVer(amigo,this) } 
  fun mejoresAmigos() = amigos.filter{ it.esMejorAmigo(it) }


  fun cantidadAmigos() = amigos.size
  fun esMasAmistosoQue(usuario: Usuario) = this.cantidadAmigos() > usuario.cantidadAmigos()
  fun amigoMasPopular() = amigos.maxByOrNull { it.totalLikes() }
  fun totalLikes() = publicaciones.sumBy { it.cantidadLikes() } 
  fun totalPublicaciones() = publicaciones.size
  fun esStalker(stalker: Usuario) =
    this.cantPublicacionesLikeadasPorAmigo(stalker) > this.totalPublicaciones() * 0.9 
  fun cantPublicacionesLikeadasPorAmigo(amigo: Usuario) = publicaciones.count { it.likers.contains(amigo) } 

}


