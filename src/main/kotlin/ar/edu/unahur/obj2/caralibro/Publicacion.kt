package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion(var likes: Int = 0, var permiso: String = "Publico") { //Agregue el atributo **likes**
  val likers = mutableListOf<Usuario>() //Ver
  abstract fun espacioQueOcupa(): Int
  fun cantLikes() = likes
  fun sumarLike(){ likes += 1 }
  fun sumarUsuario(usuario: Usuario) {
    if (!likers.contains(usuario)){
      likers.add(usuario)
    }
  }
  fun quienLikeo() = likers
  abstract fun tipoPublicacion() : String
  fun cambiarPermiso(nuevoPermiso : String){ permiso = nuevoPermiso }
  fun quienPuedeVer(){} //Que usuario puede ver esta publicacion(publico, solo amigos, privado con lista, publico con lista)


}

class Foto(val alto: Int, val ancho: Int, var factorDeCompresion: Double = 0.7) : Publicacion() {
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt() //ceil() => Redondea un numero desde positiva hasta infinito.
  override fun tipoPublicacion() = "foto"
  fun cambiarFactorDeCompresion(nuevoFactor : Double) { factorDeCompresion = nuevoFactor }
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
  override fun tipoPublicacion() = "texto"
}

class Video(var calidad: String, val duracion: Int) : Publicacion() {
  override fun espacioQueOcupa(): Int {
    var espacio = 0
    if(calidad == "SD"){
      espacio = duracion
    }
    else if(calidad == "HD 720p"){
      espacio = duracion * 3
    }
    else if (calidad == "Full HD 1080p"){
      espacio = duracion * 6
    }
    return espacio
  }
  fun cambiarCalidad(nuevaCalidad : String){ calidad = nuevaCalidad }
  fun duracionSeg() = duracion
  override fun tipoPublicacion() = "video"
}



