package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {

    //Publicaciones
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    //Nuevas
    val fotoLucho = Foto(10, 10)
    val textoLucho = Texto("Texto generico")
    val videoLucho = Video(Calidad.SD, 60)
    val video2Lucho = Video(Calidad.SD, 60)

    val fotoJulian = Foto(10, 10)
    val textoJulian = Texto("Texto generico")
    val videoJulian = Video(Calidad.SD, 60)

    val fotoFede = Foto(10, 10)
    val textoFede = Texto("Texto generico")
    val videoFede = Video(Calidad.SD, 60)

    val fotoX = Foto(10,10)

    //Usuarios
    val lucho = Usuario()
    val julian = Usuario()
    val fede = Usuario()
    val marcelo = Usuario()

    //Agrega publi a Lucho
    lucho.agregarPublicacion(videoLucho)
    lucho.agregarPublicacion(videoLucho)
    lucho.agregarPublicacion(videoLucho)
    lucho.agregarPublicacion(video2Lucho)
    julian.agregarPublicacion(fotoJulian)
    julian.agregarPublicacion(textoJulian)
    julian.agregarPublicacion(videoJulian)
    fede.agregarPublicacion(fotoFede)
    fede.agregarPublicacion(textoFede)
    fede.agregarPublicacion(videoFede)

    //REQ 1 - Saber cuánto espacio ocupa el total de las publicaciones de un usuario.
    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
        it("NO ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldNotBe(225987)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
        it("NO ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldNotBe(25)
        }
      }

      describe("de tipo video") {
        it("ocupa tantos bytes como su longitud en SD") {
          videoLucho.espacioQueOcupa().shouldBe(60)
        }
        it("ocupa tantos bytes como su longitud en HD720") {
          videoLucho.cambiarCalidad(Calidad.HD720)
          videoLucho.espacioQueOcupa().shouldBe(180)
        }
        it("ocupa tantos bytes como su longitud en HD1080") {
          videoLucho.cambiarCalidad(Calidad.HD1080)
          videoLucho.espacioQueOcupa().shouldBe(360)
        }
        it("NO ocupa tantos bytes como su longitud en SD") {
          videoLucho.espacioQueOcupa().shouldNotBe(100)
        }
        it("NO ocupa tantos bytes como su longitud en HD720") {
          videoLucho.cambiarCalidad(Calidad.HD720)
          videoLucho.espacioQueOcupa().shouldNotBe(200)
        }
        it("NO ocupa tantos bytes como su longitud en HD1080") {
          videoLucho.cambiarCalidad(Calidad.HD1080)
          videoLucho.espacioQueOcupa().shouldNotBe(300)
        }
      }
    }
    describe("Un usuario2") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
      }
    }

    //REQ 2 -
    describe("Poder darle me gusta a una publicación, y conocer cuántas veces fue votada de esta forma.") { //Poder darle me gusta a una publicación, y conocer cuántas veces fue votada de esta forma.
      it("puede darle me gusta a una publicacion") {
        lucho.meLikea(videoLucho,julian)
        videoLucho.cantidadLikes().shouldBe(1)
      }
      it("No puede darle me gusta a una publicacion mas de 1 vez"){
        lucho.meLikea(videoLucho,julian)
        lucho.meLikea(videoLucho,julian)
        videoLucho.cantidadLikes().shouldNotBe(2)
      }
    }

    //REQ 3
    describe("Saber si un usuario es más amistoso que otro: esto ocurre cuando el primero tiene más amigos") { //Poder darle me gusta a una publicación, y conocer cuántas veces fue votada de esta forma.
      it("Un usuario es mas amistoso que otro") {
        lucho.agregarAmigo(julian)
        lucho.agregarAmigo(fede)
        lucho.esMasAmistosoQue(julian).shouldBe(true)
      }
      it("Un usuario NO es mas amistoso que otro") {
        lucho.agregarAmigo(julian)
        julian.agregarAmigo(fede)
        lucho.esMasAmistosoQue(julian).shouldBe(false)
      }
    }

    //REQ 4
    describe("Saber si un usuario puede ver una cierta publicacion."){
      it("puede ver una publicacion publica"){
        videoLucho.puedeVer(julian,lucho).shouldBe(true)
      }
      //Un usuario NO puede NO ver una publicacion publica.

      it("puede ver una publicacion solo amigos"){
        lucho.agregarAmigo(julian)
        videoLucho.cambiarPermiso(SoloAmigos)
        videoLucho.puedeVer(julian,lucho).shouldBe(true)
      }
      it("NO puede ver una publicacion solo amigos"){
        videoLucho.cambiarPermiso(SoloAmigos)
        videoLucho.puedeVer(marcelo,lucho).shouldBe(false)
      }
      it("puede ver una publicacion privado con lista de permitidos"){
        videoLucho.cambiarPermiso(PrivadoConListaDePermitidos)
        lucho.agregarAmigo(julian)
        videoLucho.agregarPermitido(julian)
        videoLucho.puedeVer(julian,lucho).shouldBe(true)
      }
      it("NO puede ver una publicacion privada con lista de permitidos"){
        videoLucho.cambiarPermiso(PrivadoConListaDePermitidos)
        lucho.agregarAmigo(fede)
        videoLucho.puedeVer(fede,lucho).shouldBe(false)
      }
      it("puede ver una publicacion publica con lista de excluidos"){
        videoLucho.cambiarPermiso(PublicoConListaDeExcluidos)
        lucho.agregarAmigo(fede)
        videoLucho.puedeVer(fede,lucho).shouldBe(true)
      }
      it("NO puede ver una publicacion publica con lista de excluidos"){
        videoLucho.cambiarPermiso(PublicoConListaDeExcluidos)
        videoLucho.excluirAmigo(julian)
        videoLucho.puedeVer(julian,lucho).shouldBe(false)
      }
    }

    //REQ 5
    describe("Mejores amigos de un usuario"){
      it("el conjunto de sus amigos que pueden ver todas sus publicaciones"){ //realizar mejoresAmigos()
        textoLucho.cambiarPermiso(SoloAmigos)
        videoLucho.cambiarPermiso(PublicoConListaDeExcluidos)
        video2Lucho.cambiarPermiso(PrivadoConListaDePermitidos)
        lucho.agregarAmigo(julian)
        video2Lucho.agregarPermitido(julian)
        lucho.agregarAmigo(fede)
        video2Lucho.agregarPermitido(fede)
        lucho.agregarAmigo(marcelo)
        lucho.mejoresAmigos().shouldContainAll(julian,fede) //Una lista que contenga a julian y fede.
      }
      it("Amigos que NO pueden ver sus publicaciones"){
        textoLucho.cambiarPermiso(SoloAmigos)
        videoLucho.cambiarPermiso(PublicoConListaDeExcluidos)
        video2Lucho.cambiarPermiso(PrivadoConListaDePermitidos)
        lucho.agregarAmigo(julian)
        lucho.agregarAmigo(fede)
        video2Lucho.agregarPermitido(fede)
        video2Lucho.agregarPermitido(julian)
        lucho.agregarAmigo(marcelo)
        lucho.mejoresAmigos().shouldNotBe(marcelo)//Una lista que NO contenga a Marcelo.
      }
    }

    //REQ 6
    describe("El amigo más popular que tiene un usuario. Es decir, el amigo que tiene mas me gusta entre todas sus publicaciones.") {
      it("Es el amigo mas popular") {
        julian.agregarAmigo(fede)
        julian.agregarAmigo(lucho)
        fede.agregarAmigo(lucho)
        lucho.meLikea(videoLucho, julian)
        lucho.meLikea(videoLucho, fede)
        julian.meLikea(videoJulian, lucho)
        fede.amigoMasPopular().shouldBe(lucho)
      }

      it("NO es el amigo mas popular") {
        julian.agregarAmigo(fede)
        julian.agregarAmigo(lucho)
        fede.agregarAmigo(lucho)
        lucho.meLikea(videoLucho, julian)
        lucho.meLikea(videoLucho, fede)
        julian.meLikea(videoJulian, lucho)
        fede.amigoMasPopular().shouldNotBe(julian)
      }
    }

    //REQ 7
    describe("Un usuario stalkea a otro. Lo cual ocurre si el stalker le dio me gusta a más del 90% de sus publicaciones"){
      it("es Stalker"){
        julian.agregarAmigo(lucho)
        fede.agregarAmigo(lucho)
        lucho.meLikea(textoLucho,julian)
        lucho.meLikea(fotoLucho,julian)
        lucho.meLikea(videoLucho,julian)
        lucho.meLikea(video2Lucho,julian)
        lucho.esStalker(julian).shouldBe(true)
      }
      it("NO es Stalker"){
        julian.agregarAmigo(fede)
        fede.agregarAmigo(lucho)
        lucho.meLikea(textoLucho,fede)
        lucho.meLikea(fotoLucho,fede)
        lucho.esStalker(fede).shouldBe(false)
      }
    }
  }
})
