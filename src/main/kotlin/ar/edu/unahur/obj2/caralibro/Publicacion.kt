package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ThisClassReceiver

abstract class Publicacion() { //Agregue el atributo **likes**
  var permiso = publico
  val likers = mutableListOf<Usuario>() //Ver
  abstract fun espacioQueOcupa(): Int
  fun cantidadLikes() = likers.size
  fun sumarUsuarioConLike(usuario: Usuario) {
    if (!likers.contains(usuario)){
      likers.add(usuario)
    }
  }
  fun quienLikeo() = likers
  abstract fun tipoPublicacion() : String
  fun cambiarPermiso(nuevoPermiso : Permiso){ permiso = nuevoPermiso }
  fun quienPuedeVer(){} //Que usuario puede ver esta publicacion(publico, solo amigos, privado con lista, publico con lista)

  fun puedeVerPublicacion(usuarioChusma: Usuario,this: Usuario){
    permiso.puedeVerPublicacion(usuarioChusma,this)
  }
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  var factorDeCompresion = factorDeCompresionGlobal
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion.factor).toInt() //ceil() => Redondea un numero desde positiva hasta infinito.
  override fun tipoPublicacion() = "foto"
  fun cambiarFactorDeCompresion(nuevoFactor: Double){
    factorDeCompresion.cambiar(nuevoFactor)}
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
  override fun tipoPublicacion() = "texto"
}

class Video(var calidad: Calidad, val duracion: Int) : Publicacion() {
  override fun espacioQueOcupa() = duracion * calidad.multiplicador
  fun cambiarCalidad(calidad: Calidad){ this.calidad = calidad }
  override fun tipoPublicacion() = "video"
}

//Para cambiar la calidad como clase
enum class Calidad(val multiplicador: Int){
  SD(1),
  HD720(3),
  HD1080(HD720.multiplicador*2)
}

//Factor para cambiar a todas las fotos
object factorDeCompresionGlobal{
  var factor = 0.7
  fun cambiar(nuevoFactor: Double){
    factor = nuevoFactor
  }
}
//Clase abst Permiso
abstract class Permiso{
  abstract fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDueño: Usuario) : Boolean
}
object publico: Permiso() {
  override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDueño :Usuario) = true
}
object soloAmigos: Permiso() {
  override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDueño :Usuario) =  usuarioDueño.amigos.contains(usuarioChusma)
}
object privadoConListaDePermitidos: Permiso(){
  override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDueño :Usuario) =  usuarioDueño.excluidos.contains(usuarioChusma)
}
object publicoConListaDeExcluidos: Permiso(){
  override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDueño :Usuario) =  !usuarioDueño.excluidos.contains(usuarioChusma)
}
