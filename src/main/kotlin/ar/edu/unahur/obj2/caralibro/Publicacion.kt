package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion(var likes: Int = 0, var permiso: String = "Publico") { //Agregue el atributo **likes**
  val likers = mutableListOf<Usuario>()
  abstract fun espacioQueOcupa(): Int

  fun cantLikes() = likes
  fun sumarLike(){ likes += 1 }
  fun quienLikeo() = likers
  fun cambiarPermiso(nuevoPermiso : String){ permiso = nuevoPermiso}
  fun quienPuedeVer(){} //Que usuario puede ver esta publicacion(publico, solo amigos, privado con lista, publico con lista)
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt() //ceil() => Redondea un numero desde positiva hasta infinito.
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(var calidad: String, val duracion: Int) : Publicacion() {
  override fun espacioQueOcupa(): Int {
    val espacio = 0
    if(calidad == "SD"){
      espacio = this.duracionSeg()
    }
    else if(calidad == "HD 720p"){
      espacio = this.duracionSeg() * 3
    }
    else {
      espacio = this.duracionSeg() * 6
    }
    return espacio
  }
  fun cambiarCalidad(nuevaCalidad : String){
    calidad = nuevaCalidad
  }
  fun duracionSeg(){} //Convierte la duracion total del video en segundos.

}



