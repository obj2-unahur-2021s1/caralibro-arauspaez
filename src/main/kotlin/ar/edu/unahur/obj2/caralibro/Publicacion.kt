package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion() { //Agregue el atributo **likes**
    var permiso: Permiso = Publico
    val likers = mutableListOf<Usuario>()
    val excluidos = mutableListOf<Usuario>()
    val permitido = mutableListOf<Usuario>()

    abstract fun espacioQueOcupa(): Int
    fun cantidadLikes() = likers.size
    fun sumarUsuarioConLike(usuario: Usuario){
        if (!likers.contains(usuario)){
            likers.add(usuario)
        }
    }
    fun quienLikeo() = likers
    fun cambiarPermiso(nuevoPermiso : Permiso){ permiso = nuevoPermiso }
    fun quienPuedeVer() = this.permiso //Que usuario puede ver esta publicacion (publico, solo amigos, privado con lista, publico con lista)
    fun puedeVer(usuarioChusma: Usuario,usuarioDuenio: Usuario) = this.permiso.puedeVerPublicacion(usuarioChusma,usuarioDuenio,this)
    fun excluirAmigo(nuevoUsuario: Usuario) {
        if(!excluidos.contains(nuevoUsuario))
            excluidos.add(nuevoUsuario)
    }
    fun agregarPermitido(nuevoUsuario: Usuario){
        if(!permitido.contains(nuevoUsuario))
            permitido.add(nuevoUsuario)
    }
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
    var factorDeCompresion = FactorDeCompresionGlobal
    override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion.factor).toInt() //ceil() => Redondea un numero desde positiva hasta infinito.
    fun cambiarFactorDeCompresion(nuevoFactor: Double){
        factorDeCompresion.cambiar(nuevoFactor)}
}

class Texto(val contenido: String) : Publicacion() {
    override fun espacioQueOcupa() = contenido.length
}

class Video(var calidad: Calidad, val duracion: Int) : Publicacion() {
    override fun espacioQueOcupa() = duracion * calidad.multiplicador
    fun cambiarCalidad(calidad: Calidad){ this.calidad = calidad }
}

//Para cambiar la calidad como clase
enum class Calidad(var multiplicador: Int){
    SD(1),
    HD720(3),
    HD1080(HD720.multiplicador*2)
}

//Factor para cambiar a todas las fotos
object FactorDeCompresionGlobal {
    var factor = 0.7
    fun cambiar(nuevoFactor: Double){
        factor = nuevoFactor
    }
}
//Clase abst Permiso
abstract class Permiso {
    abstract fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDuenio: Usuario,publicacionNueva: Publicacion) : Boolean
}
object Publico: Permiso() {
    override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDuenio :Usuario,publicacionNueva: Publicacion) = true
}
object SoloAmigos: Permiso() {
    override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDuenio :Usuario,publicacionNueva: Publicacion) =
        usuarioDuenio.amigos.contains(usuarioChusma) || usuarioChusma == usuarioDuenio
}
object PrivadoConListaDePermitidos: Permiso(){
    override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDuenio: Usuario,publicacionNueva: Publicacion) =
        publicacionNueva.permitido.contains(usuarioChusma) || usuarioChusma == usuarioDuenio
}
object PublicoConListaDeExcluidos: Permiso(){
    override fun puedeVerPublicacion(usuarioChusma: Usuario, usuarioDuenio: Usuario,publicacionNueva: Publicacion) =
        !publicacionNueva.excluidos.contains(usuarioChusma)
}

