package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
  fun likear(publicacion: Publicacion) {
    if (!publicacion.quienLikeo().contains(this)){
      publicacion.sumarLike()
      publicacion.sumarUsuario(this)
    }
  }
  //fun desLikear(publicacion: Publicacion){ publicacion.sacarLike() } //Definir sacarLike() y sacar usuario.
  //fun cambiarCalidadVideo(nuevaCalidad){}

  fun publicacionesFotos() = publicaciones.filter{ it.tipoPublicacion() == "foto" }
  fun modificarFactorCompresionFotos(nuevoFactor : Double){
    this.publicacionesFotos().forEach{it.cambiarFactorDeCompresion(nuevoFactor)}
  } //Consultar
  fun agregarAmigo(nuevoUsuario: Usuario) {
    if(!amigos.contains(nuevoUsuario))
      amigos.add(nuevoUsuario)
      nuevoUsuario.amigos.add(this)
  }
  fun eliminarAmigo(nuevoUsuario: Usuario) {
    if (amigos.contains(nuevoUsuario))
    amigos.remove(nuevoUsuario)
    nuevoUsuario.amigos.remove(this)
  }

  fun puedoVerYo(publicacion: Publicacion) = publicaciones.contains(publicacion)

}
